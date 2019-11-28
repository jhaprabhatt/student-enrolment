package com.jha.project.model;

public enum Status {
    ADDED("ADDED"),
    UPDATED("UPDATED"),
    REMOVED("REMOVED");

    private final String status;

    private Status(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
