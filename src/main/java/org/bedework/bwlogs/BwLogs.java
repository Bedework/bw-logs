/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.bwlogs;

import org.bedework.bwlogs.DisplaySessions.DisplayMode;
import org.bedework.util.args.Args;

import static org.bedework.bwlogs.DisplaySessions.DisplayMode.full;
import static org.bedework.bwlogs.DisplaySessions.DisplayMode.list;
import static org.bedework.bwlogs.DisplaySessions.DisplayMode.summary;

/**
 * User: mike
 * Date: 5/5/15
 * Time: 4:26 PM
 */
public class BwLogs {
  /**
   * <p>Arguments<ul>
   *     <li>url: the url of the jolokia service</li>
   * </ul>
   * </p>
   *
   * @param args program arguments.
   */
  public static void main(final String[] args) {
    String requestDt = null;
    String taskId = null;
    String sessionId = null;
    String sessionUser = null;
    boolean skipAnon = false;
    boolean displayTotals = false;
    DisplayMode displayMode = full;
    boolean logShowLong = false;
    boolean logShowMissingTaskIds = false;

    try {
      final Args pargs = new Args(args);

      while (pargs.more()) {
        if (pargs.ifMatch("logshowlong")) {
          logShowLong = true;
          continue;
        }

        if (pargs.ifMatch("logshowmissingtaskids")) {
          logShowMissingTaskIds = true;
          continue;
        }

        if (pargs.ifMatch("logsummarisetests")) {
          new SummariseTests().process(pargs.next(), logShowLong,
                                       logShowMissingTaskIds);
          return;  // Always 1 shot
        }

        if (pargs.ifMatch("loganalyse")) {
          new LogAnalysis().process(pargs.next(), logShowLong,
                                    logShowMissingTaskIds);
          return;  // Always 1 shot
        }

        if (pargs.ifMatch("sessions")) {
          new DisplaySessions(taskId,
                              sessionId,
                              sessionUser,
                              requestDt,
                              skipAnon,
                              displayTotals,
                              displayMode).
                  process(pargs.next(), logShowLong,
                          logShowMissingTaskIds);
          return;  // Always 1 shot
        }

        if (pargs.ifMatch("skipAnon")) {
          skipAnon = true;
          continue;
        }

        if (pargs.ifMatch("displayTotals")) {
          displayTotals = true;
          continue;
        }

        if (pargs.ifMatch("summary")) {
          displayMode = summary;
          continue;
        }

        if (pargs.ifMatch("list")) {
          displayMode = list;
          continue;
        }

        if (pargs.ifMatch("full")) {
          displayMode = full;
          continue;
        }

        if (pargs.ifMatch("requestDt")) {
          requestDt = pargs.next();
          continue;
        }

        if (pargs.ifMatch("sessionId")) {
          sessionId = pargs.next();
          continue;
        }

        if (pargs.ifMatch("taskId")) {
          taskId = pargs.next();
          continue;
        }

        if (pargs.ifMatch("sessionUser")) {
          sessionUser = pargs.next();
          continue;
        }

        if (pargs.ifMatch("access")) {
          new AccessLogs().analyze(pargs.next());
          return;  // Always 1 shot
        }

        usage("Illegal argument: " +
                      pargs.current());
        return;
      }
    } catch (final Throwable t) {
      t.printStackTrace();
    }
  }

  private static void usage(final String msg) {
    if (msg != null) {
      System.err.println();
      System.err.println(msg);
    }

    System.err.println();
    System.err.println("Optional arguments:");
    System.err.println("   access             Analyze access log");
    System.err.println("   logshowlong        To enable display of long requests" +
                       "                      in loganalyse");
    System.err.println("   [logsummarisetests] loganalyse <path>  " +
                       "                      Calculate and display information" +
                       "                      from referenced log file. If" +
                       "                      logsummarisetests is present then " +
                       "                      display a summary to help when" +
                       "                      running the tests");
  }
}
