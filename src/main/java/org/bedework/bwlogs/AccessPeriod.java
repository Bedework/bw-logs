/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.bwlogs;

import org.bedework.util.misc.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mike Date: 2/22/20 Time: 23:56
 */
public class AccessPeriod {
  final Map<String, Integer> ipCounts = new HashMap<>();
  final Map<String, Integer> ip2Counts = new HashMap<>();
  final int periodSeconds;

  public AccessPeriod(final int periodSeconds) {
    this.periodSeconds = periodSeconds;
  }

  public void addIp(final String ip) {
    var i = ipCounts.getOrDefault(ip, 0);
    ipCounts.put(ip, i + 1);

    final var ip2 = getIp2(ip);
    if (ip2 == null) {
      return;
    }

    i = ip2Counts.getOrDefault(ip2, 0);
    ip2Counts.put(ip2, i + 1);
  }

  public int totalRequests() {
    return ipCounts.values().stream().mapToInt(Number::intValue).sum();
  }

  public List<Map.Entry<String, Integer>> getSortedIpCounts() {
    return Util.sortMap(ipCounts);
  }

  public List<Map.Entry<String, Integer>> getSortedIp2Counts() {
    return Util.sortMap(ip2Counts);
  }

  public float perSecond() {
    return (float)totalRequests() / periodSeconds;
  }

  public void add(final AccessPeriod ap) {
    for (final var ip: ap.ipCounts.keySet()) {
      final var ct = ap.ipCounts.get(ip);

      final var i = ipCounts.getOrDefault(ip, 0);
      ipCounts.put(ip, i + ct);
    }

    for (final var ip2: ap.ip2Counts.keySet()) {
      final var ct = ap.ip2Counts.get(ip2);

      final var i = ip2Counts.getOrDefault(ip2, 0);
      ipCounts.put(ip2, i + ct);
    }
  }

  private String getIp2(final String ip) {
    var pos = ip.indexOf(".");
    if (pos < 0) {
      return null;
    }

    pos = ip.indexOf(".", pos + 1);
    if (pos < 0) {
      return null;
    }

    return ip.substring(0, pos) + ".*";
  }
}
