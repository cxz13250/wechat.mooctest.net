package com.mooctest.weixin.data;

/**
 * Created by ROGK on 2017/5/10.
 */
public class Grade {
    private int workerId;
    private String workerName;
    private double grade;

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
