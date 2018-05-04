package com.bishe.bishe.mapper;

import com.bishe.bishe.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    @Select("select * from users where id = #{id}")
    User selectUser(Integer id);

    @Select("select * from users where username = #{username,jdbcType=VARCHAR} and password = #{password,jdbcType=VARCHAR}")
    User checkUser(String username,String password);
}
