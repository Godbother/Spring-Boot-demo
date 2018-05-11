package com.bishe.bishe.mapper;

import com.bishe.bishe.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("select * from users where id = #{id}")
    User selectUser(Integer id);

    @Select("select * from users where username = #{username} and password = #{password}")
    User checkUser(@Param(value="username")String username,
                   @Param(value="password")String password);

    @Insert("INSERT INTO users VALUES (null,#{newUser.username},#{newUser.password},#{newUser.sex},#{newUser.uploadHistory},#{newUser.remark})")
    Integer addUser(@Param(value = "newUser") User newUser);

    @Select("select * from users")
    List<User> selectAll();

    @Delete("delete from users where id = #{id}")
    Integer delUser(@Param(value = "id")Integer id);

    @Update("update users set password = #{password} where username = #{username}")
    Integer updateUserPs(@Param(value="username")String username,
                       @Param(value="password")String password);

    @Update("update users set password = #{newUser.password},sex = #{newUser.sex},uploadHistory = #{newUser.uploadHistory},remark = #{newUser.remark} where id = #{newUser.id}")
    Integer updateUserInfo(@Param(value = "newUser") User newUser);

    @Update("update users set uploadHistory = #{uploadHistory} where id = #{id}")
    Integer updateHistory(@Param(value = "uploadHistory")String uploadHistory,
                          @Param(value = "id")Integer id);
}
