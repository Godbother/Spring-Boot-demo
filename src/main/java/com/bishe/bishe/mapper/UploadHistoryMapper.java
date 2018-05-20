package com.bishe.bishe.mapper;

import com.bishe.bishe.model.UploadHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadHistoryMapper {

    @Select("select * from uploadhistory")
    List<UploadHistory> selectAll();

    @Insert("insert into uploadhistory values(null,#{filename},#{username},#{uploadtime},#{uploadat})")
    int insert(@Param("filename")String filename,
               @Param("username")String username,
               @Param("uploadtime")String uploadtime,
               @Param("uploadat")Long uploadat);
}
