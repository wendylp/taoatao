package com.taotao.mapper;

import java.util.List;

import com.taotao.pojo.UserAddress;

public interface UserAddressMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserAddress userAddress);

    List<UserAddress> selectAddressByUserId(String userId);

    int updateByPrimaryKey(UserAddress userAddress);
}