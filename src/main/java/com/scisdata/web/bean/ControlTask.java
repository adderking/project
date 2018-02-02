package com.scisdata.web.bean;

import java.sql.Timestamp;

public class ControlTask {

    private long ID;
    private String taskName;
    private String taskTarget;
    private Timestamp startTime;
    private Timestamp endTime;
    private String enqumentID;
    private Timestamp createTime;
    private long taskStatus;
    private long taskType;
    private long controlType;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTarget() {
        return taskTarget;
    }

    public void setTaskTarget(String taskTarget) {
        this.taskTarget = taskTarget;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getEnqumentID() {
        return enqumentID;
    }

    public void setEnqumentID(String enqumentID) {
        this.enqumentID = enqumentID;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public long getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(long taskStatus) {
        this.taskStatus = taskStatus;
    }

    public long getTaskType() {
        return taskType;
    }

    public void setTaskType(long taskType) {
        this.taskType = taskType;
    }

    public long getControlType() {
        return controlType;
    }

    public void setControlType(long controlType) {
        this.controlType = controlType;
    }
}
