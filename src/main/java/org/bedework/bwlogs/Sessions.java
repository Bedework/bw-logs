package org.bedework.bwlogs;

import org.bedework.util.misc.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sessions {
  // key is sessionid
  protected final Map<String, SessionInfo> sessionInfos =
      new HashMap<>();

  // key is ip
  final Map<String, Collection<SessionInfo>> ipSessionMap =
      new HashMap<>();

  public SessionInfo trySessionStart(final String ln) {
    final var si = new SessionInfo();
    final var res = si.parse(ln);

    if ((res == null) || (res < 0)) {
      return null;
    }

    final var sid = si.sessionId;
    sessionInfos.put(sid, si);

    final var sis = ipSessionMap
        .computeIfAbsent(si.ip,
                         k -> new ArrayList<>());
    sis.add(si);

    return si;
  }

  public int size() {
    return sessionInfos.size();
  }

  public void requestIn(final String sessionId,
                        final String ip) {
    final var si = sessionInfos.get(sessionId);
    if (si != null) {
      si.numRequests++;

      if (si.ip == null) {
        addToIpSessionMap(ip, si);
      }
      si.ip = ip;
    } else {
      // No session start seen
      final var newSi = new SessionInfo();
      newSi.ip = ip;
      sessionInfos.put(sessionId, newSi);
      addToIpSessionMap(ip, newSi);
    }
  }

  private void addToIpSessionMap(final String ip,
                                 final SessionInfo si) {
    final var sis = ipSessionMap
        .computeIfAbsent(ip,
                         k -> new ArrayList<>());
    sis.add(si);
  }

  public record SessionCounts(long numRequests, long avg)
  implements Comparable<SessionCounts> {
    @Override
    public int compareTo(final SessionCounts o) {
      return Long.compare(this.numRequests, o.numRequests);
    }
  }

  public SessionCounts getSessionCounts(final String ip) {
    var total = 0L;
    final var sis = ipSessionMap.get(ip);
    if (sis == null) {
      return new SessionCounts(-999, -999);
    }

    for (final var si: sis) {
      total += si.numRequests;
    }

    return new SessionCounts(total, total / sis.size());
  }

  public List<Map.Entry<String, SessionCounts>> getAllSessionCounts() {
    final var ipCounts = new HashMap<String, SessionCounts>();

    for (final var ip: ipSessionMap.keySet()) {
      ipCounts.put(ip, getSessionCounts(ip));
    }

    return Util.sortMap(ipCounts).reversed();
  }
}
