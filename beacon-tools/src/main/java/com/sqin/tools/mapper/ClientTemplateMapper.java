package com.sqin.tools.mapper;

import com.sqin.tools.entity.ClientTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Qin
 * @Date 2025/5/6 21:20
 * @Description
 **/
public interface ClientTemplateMapper {

    @Select("select * from client_template where sign_id = #{signId}")
    List<ClientTemplate> findBySignId(@Param("signId") Long signId);

}
