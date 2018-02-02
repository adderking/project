package com.scisdata.web.bean;

public class ControlResult {

    private long id;
    private String traceId;
    private long taskId;

    public ControlResult(String traceId, long taskId) {
        this.traceId = traceId;
        this.taskId = taskId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
