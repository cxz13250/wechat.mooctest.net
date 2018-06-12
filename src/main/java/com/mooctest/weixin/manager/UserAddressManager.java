package com.mooctest.weixin.manager;

import com.mooctest.weixin.dao.UserAddressDao;
import com.mooctest.weixin.model.UserAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ROKG
 * @Description
 * @Date: Created in 下午9:36 2018/6/10
 * @Modified By:
 */
@Service
public class UserAddressManager {

    @Autowired
    UserAddressDao addressDao;

    public UserAddress getAddress(long userId){
        List<UserAddress> addresses=addressDao.getCompetitionByColValue("userId",userId);
        return addresses.size()==0?null:addresses.get(0);
    }

    public void save(UserAddress address){
        addressDao.updateAddress(address);
    }
}
