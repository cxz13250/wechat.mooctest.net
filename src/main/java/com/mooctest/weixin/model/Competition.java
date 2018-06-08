package com.mooctest.weixin.model;

import javax.persistence.*;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 下午1:54 2018/6/8
 * @Modified By:
 */
@Entity
@Table(name = "competition")
public class Competition {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "group_id")
    private long groupId;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "is_delete")
    private boolean is_delete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
