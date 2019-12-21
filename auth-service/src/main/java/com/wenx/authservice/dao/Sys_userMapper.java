package com.wenx.authservice.dao;

import com.wenx.authservice.model.Sys_user;
import com.wenx.authservice.model.Sys_userWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Sys_userMapper {
    int deleteByPrimaryKey(String id);

    int insert(Sys_userWithBLOBs record);

    int insertSelective(Sys_userWithBLOBs record);

    Sys_userWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sys_userWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(Sys_userWithBLOBs record);

    int updateByPrimaryKey(Sys_user record);

    @Select("select id,username,password from sys_user where username = #{name} limit 1")
    Sys_user queryUserByName(@Param("name")String name);
}