package org.bloomdex.datamcbaseface.model;

public class TimeFrame {
    private String startDate;
    private String endDate;

    public TimeFrame() {
        startDate = "1970-01-01 00:00:00";
        endDate = "1970-01-01 00:00:00";
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
