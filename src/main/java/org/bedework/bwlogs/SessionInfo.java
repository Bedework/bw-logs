package org.bedework.bwlogs;

public class SessionInfo extends LogEntry {
  String sessionId;
  int numActive;
  long sessionCt;
  int numRequests;

  public String ip;

  /*
  SESSION-START:YBc2oVwhs2GxocLDlqKx9Ui4EnMgSrLt-Uj-BPYU:?:347:14051:1052M:2000M
   */
  public Integer parse(final String req) {
    if (!req.contains(" SESSION-START:") ||
        (super.parse(req, "SESSION-START", "INFO") == null)) {
      return null;
    }

    sessionId = field();
    if (sessionId == null) {
      error("No session end found for %s", req);
      return null;
    }

    final var skip = field();

    final var na = intField();
    if (na == null) {
      return null;
    }

    numActive = na;

    final var sc = longField();
    if (sc == null) {
      return null;
    }

    sessionCt = sc;

    return curPos;
  }
}
