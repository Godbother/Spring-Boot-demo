package com.bishe.bishe.mapper;

import com.bishe.bishe.model.UploadHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UploadHistoryMapper {

    @Select("select * from uploadhistory")
    List<UploadHistory> selectAll();

    @Insert("insert into uploadhistory values(null,#{filename},#{username},#{uploadtime},#{uploadat})")
    int insert(@Param("filename")String filename,
               @Param("username")String username,
               @Param("uploadtime")String uploadtime,
               @Param("uploadat")Long uploadat);

    @Select("select * from uploadhistory order by uploadtime desc limit 0,10")
    List<UploadHistory> selectRecent10();

    @Select("SELECT DATE_FORMAT(uploadtime,'%Y/%m/%d') as time,count(*) as total " +
            "from bishe.uploadhistory where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= uploadtime " +
            "group by DATE_FORMAT(uploadtime,'%Y %m %d')")
    List<Map> dayCount();
}
