package br.com.arndroid.monitormobile.dashboard;

import br.com.arndroid.monitormobile.utils.DashboardUtils;
import br.com.arndroid.monitormobile.utils.IssueUtils;

public class DashboardItem {
    public String acronymId;
    public String description;
    public int closed;
    public int[][] flagAndClockType = new int[IssueUtils.TOTAL_FLAGS][IssueUtils.TOTAL_CLOCKS];

    public int getFlagTypeCount(int flagType) {
        int result = 0;
        for (int clockType = 0; clockType < IssueUtils.TOTAL_CLOCKS; clockType++) {
            result += flagAndClockType[flagType][clockType];
        }
        return result;
    }

    public int getClockTypeCount(int clockType) {
        int result = 0;
        for (int flagType = 0; flagType < IssueUtils.TOTAL_CLOCKS; flagType++) {
            result += flagAndClockType[flagType][clockType];
        }
        return result;
    }

    public String getPlainTextForDashboardType(int dashboardType) {
        if (dashboardType == DashboardUtils.DASHBOARD_TYPE_FLAG) {
            return "K=" + getFlagTypeCount(IssueUtils.FLAG_BLACK)
                    + " R=" + getFlagTypeCount(IssueUtils.FLAG_RED)
                    + " Y=" + getFlagTypeCount(IssueUtils.FLAG_YELLOW)
                    + " B=" + getFlagTypeCount(IssueUtils.FLAG_BLUE)
                    + " C=" + closed;
        } else if (dashboardType == DashboardUtils.DASHBOARD_TYPE_CLOCK) {
            return "K=" + getClockTypeCount(IssueUtils.CLOCK_BLACK)
                    + " R=" + getClockTypeCount(IssueUtils.CLOCK_RED)
                    + " Y=" + getClockTypeCount(IssueUtils.CLOCK_YELLOW)
                    + " B=" + getClockTypeCount(IssueUtils.CLOCK_BLUE)
                    + " C=" + closed;
        } else {
            return "[array: see debug window]";
        }
    }
}