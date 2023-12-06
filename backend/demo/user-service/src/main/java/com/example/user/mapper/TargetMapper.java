package com.example.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.user.domain.Target;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author lenovo
* @description 针对表【target】的数据库操作Mapper
* @createDate 2023-09-07 12:15:08
* @Entity com.example.demo.pojo.Target
*/
@Mapper
public interface TargetMapper extends BaseMapper<Target> {

    @Select("select * from target")
    public List<Target> shofffw();
}




