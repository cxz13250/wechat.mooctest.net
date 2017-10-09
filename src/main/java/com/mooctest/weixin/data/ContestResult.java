package com.mooctest.weixin.data;

/**
 * Created by ROGK on 2017/9/26.
 */
public class ContestResult {
    private String name;
    private double score;
    private long rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }
}
