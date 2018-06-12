package com.mooctest.weixin.model;

import javax.persistence.*;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 上午1:19 2018/6/11
 * @Modified By:
 */
@Entity
@Table(name = "user_2_competition")
public class User2Competition {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "competition_id")
    private long competitionId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(long competitionId) {
        this.competitionId = competitionId;
    }
}
