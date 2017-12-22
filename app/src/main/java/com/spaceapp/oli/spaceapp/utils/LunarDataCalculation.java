package com.spaceapp.oli.spaceapp.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class LunarDataCalculation {

    private double age, distance, la, lo;
    String phase, zodiac;
    int yy, mm, k1, k2, k3, jd;
    double ip, dp, np, rp;

    public double getAge(int y, int m, int d) {
        yy = (int) (y - Math.floor((12 - m) / 10));
        mm = m + 9;

        if (mm >= 12) {
            mm = mm - 12;
        }

        k1 = (int) Math.floor(365.25 * (yy + 4712));
        k2 = (int) Math.floor(30.6 * mm + 0.5);
        k3 = (int) ((int) Math.floor(Math.floor(yy / 100) + 49) * 0.75) - 38;

        jd = k1 + k2 + d + 59;

        if (jd > 2299160) {
            jd = jd - k3;
        }

        ip = normalize((jd - 2451550.1) / 29.530588853);
        age = ip * 29.53;

        return round2(age);
    }

    public String getPhase(int age) {
        if (age < 1.84566) return "NEW";
        else if (age < 5.53699) return "Waxing crescent";
        else if (age < 9.22831) return "First Quarter";
        else if (age < 12.91963) return "Waxing gibbous";
        else if (age < 16.61096) return "FULL";
        else if (age < 20.30228) return "Waning gibbous";
        else if (age < 23.99361) return "Last quarter";
        else if (age < 27.68493) return "Waning crescent";
        else return "NEW";
    }

    public int getPhaseNo() {
        DateTime startDate = new DateTime(1900, 1, 1, 0, 0, 0, 0);
        DateTime endDate = new DateTime();

        Days d = Days.daysBetween(startDate, endDate);
        int days = d.getDays();
        return days % 30;
    }

    public double getDistance(int y, int m, int d) {

        yy = (int) (y - Math.floor((12 - m) / 10));
        mm = m + 9;

        if (mm >= 12) {
            mm = mm - 12;
        }

        k1 = (int) Math.floor(365.25 * (yy + 4712));
        k2 = (int) Math.floor(30.6 * mm + 0.5);
        k3 = (int) ((int) Math.floor(Math.floor(yy / 100) + 49) * 0.75) - 38;

        jd = k1 + k2 + d + 59;

        if (jd > 2299160) {
            jd = jd - k3;
        }

        ip = normalize((jd - 2451550.1) / 29.530588853);

        ip = ip * 2 * Math.PI;

        dp = 2 * Math.PI * normalize((jd - 2451562.2) / 27.55454988);
        distance = 60.4 - 3.3 * Math.cos(dp) - 0.6 * Math.cos(2 * Math.PI - dp) - 0.5 * Math.cos(2 * Math.PI);
        distance = (int) round2(distance) * 6440;
        return distance;
    }

    public double moonLatitude(int y, int m, int d) {

        yy = (int) (y - Math.floor((12 - m) / 10));
        mm = m + 9;

        if (mm >= 12) {
            mm = mm - 12;
        }

        k1 = (int) Math.floor(365.25 * (yy + 4712));
        k2 = (int) Math.floor(30.6 * mm + 0.5);
        k3 = (int) ((int) Math.floor(Math.floor(yy / 100) + 49) * 0.75) - 38;

        jd = k1 + k2 + d + 59;

        if (jd > 2299160) {
            jd = jd - k3;
        }

        ip = normalize((jd - 2451550.1) / 29.530588853);

        np = 2 * Math.PI * normalize((jd - 2451565.2) / 27.212220817);
        la = 5.1 * Math.sin(np);
        return round2(la);
    }

    public double moonLongitude(int y, int m, int d) {

        yy = (int) (y - Math.floor((12 - m) / 10));
        mm = m + 9;

        if (mm >= 12) {
            mm = mm - 12;
        }

        k1 = (int) Math.floor(365.25 * (yy + 4712));
        k2 = (int) Math.floor(30.6 * mm + 0.5);
        k3 = (int) ((int) Math.floor(Math.floor(yy / 100) + 49) * 0.75) - 38;

        jd = k1 + k2 + d + 59;

        if (jd > 2299160) {
            jd = jd - k3;
        }

        ip = normalize((jd - 2451550.1) / 29.530588853);

        rp = normalize((jd - 2451555.8) / 27.321582241);
        lo = 360 * rp + 6.3 * Math.sin(dp) + 1.3 * Math.sin(2 * ip - dp) + 0.7 * Math.sin(2 * ip);
        return round2(lo);
    }

    public String moonConstellation(double lo) {
        if (lo < 33.18) return "Pisces";
        else if (lo < 51.16) return "Aries";
        else if (lo < 93.44) return "Taurus";
        else if (lo < 119.48) return "Gemini";
        else if (lo < 135.30) return "Cancer";
        else if (lo < 173.34) return "Leo";
        else if (lo < 224.17) return "Virgo";
        else if (lo < 242.57) return "Libra";
        else if (lo < 271.26) return "Scorpio";
        else if (lo < 302.49) return "Sagittarius";
        else if (lo < 311.72) return "Capricorn";
        else if (lo < 348.58) return "Aquarius";
        else return "Pisces";

    }

    public boolean isDayOfMonth(int year, int month, int day) {
        int daysOfMonth;

        if (month < 1 || 12 < month) {
            return false;
        }

        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;

            case 2:
                daysOfMonth = 28;
                if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
                    daysOfMonth = 29;
                }
                break;

            default:
                daysOfMonth = 31;
        }
        return (0 < day) && (day <= daysOfMonth);
    }


    public double round2(double x) {
        return (Math.round(100 * x) / 100.0);
    }

    public double normalize(double v) {
        v = v - Math.floor(v);
        if (v < 0) {
            v = v + 1;
        }
        return v;
    }
}
