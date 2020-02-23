/* ********************************************************************
    Appropriate copyright notice
*/
package org.bedework.bwlogs;

/**
 * User: mike Date: 2/23/20 Time: 00:00
 */
public class AccessDay extends AccessPeriod {
  private final static int hourSecs = 60 * 60;

  private final AccessPeriod[] hours = new AccessPeriod[24];

  public AccessDay() {
    super(hourSecs * 24);

    for (int i = 0; i <= 23; i++) {
      hours[i] = new AccessPeriod(hourSecs);
    }
  }

  public void addIp(final String ip,
                    final int hour) {
    hours[hour].addIp(ip);
    addIp(ip);
  }

  public AccessPeriod getHour(final int hr) {
    return hours[hr];
  }

  public void updateFrom(final AccessLogEntry ale) {
    addIp(ale.ip, ale.hourOfDay);
  }
}
