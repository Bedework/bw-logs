/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.bwlogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Tracks accesses. Will initialise from an access log file or
 * directory containing such files.
 *
 * Once initialised can be updated as requests arrive to maintain
 * counts.
 *
 * User: mike Date: 2/23/20 Time: 00:39
 */
public class AccessTracker {
  // One entry per day
  private Map<String, AccessDay> dayValues = new HashMap<>();

  /** Update from the given access log entry. These should appear in
   * increasing time order.
   *
   * @param ale access log entry
   */
  public void updateFrom(final AccessLogEntry ale) {
    final AccessDay dayVal =
            dayValues.computeIfAbsent(ale.normDate, v -> new AccessDay());
    dayVal.updateFrom(ale);
  }

  public List<String> getSortedKeys() {
    final List<String> days = new ArrayList<>(dayValues.keySet());
    Collections.sort(days);

    return days;
  }

  public AccessDay getDay(final String key) {
    return dayValues.get(key);
  }
}
