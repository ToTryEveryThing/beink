package com.example.article.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.article.pojo.Follow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author longlong
* @description 针对表【follow】的数据库操作Mapper
* @createDate 2023-07-03 16:29:35
* @Entity com.example.demo.pojo.Follow
*/
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

    List<Follow> selectAllByFollowingId(Integer id);

    List<Follow> selectAllByFollowerId(Integer id);

}




