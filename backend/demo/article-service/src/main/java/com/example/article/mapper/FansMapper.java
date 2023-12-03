package com.example.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.article.pojo.Fans;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longlong
* @description 针对表【fans】的数据库操作Mapper
* @createDate 2023-07-03 13:54:55
* @Entity com.example.demo.pojo.user.Fans
*/
@Mapper
public interface FansMapper extends BaseMapper<Fans> {


}




