package com.company.UI;

public enum Status {

    ENDED("Ended"), INPROGRESS("In progress"), PLANNED("Planned");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
