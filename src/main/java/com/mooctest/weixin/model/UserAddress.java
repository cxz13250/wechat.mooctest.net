package com.mooctest.weixin.model;

import javax.persistence.*;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 下午9:33 2018/6/10
 * @Modified By:
 */
@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "address")
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
