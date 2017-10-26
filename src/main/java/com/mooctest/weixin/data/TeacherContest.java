package com.mooctest.weixin.data;

/**
 * Created by ROGK on 2017/10/26.
 */
public class TeacherContest {
    private String taskName;
    private double score;
    private String workerName;
    private Long rank;
    private String teacherName;

    public TeacherContest(){
        return;
    }

    public TeacherContest(WorkerContest contest,String str){
        this.setRank(contest.getRank());
        this.setScore(contest.getScore());
        this.setTaskName(contest.getTaskName());
        this.setTeacherName(str);
        this.setWorkerName(contest.getWorkerName());
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}