package com.bishe.bishe.mapper;

import com.bishe.bishe.model.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    @Select("select * from admin where adminname = #{adminname} and password = #{password}")
    Admin checkAdmin(@Param(value="adminname")String adminname,
                     @Param(value="password")String password);

}
