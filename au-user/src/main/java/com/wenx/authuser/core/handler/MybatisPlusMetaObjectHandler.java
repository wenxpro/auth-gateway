package com.wenx.authuser.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;

import java.util.Date;

/**
 * 公共字段填充
 * @author wenx
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";
    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_TIME = "modifyTime";
    private static final String UPDATE_BY = "modifyBy";

    @Override
    public void insertFill (MetaObject metaObject){
        Long userId = this.getUserId();
        try {
            Long id=(Long) getFieldValByName(CREATE_BY,metaObject);
            if(id==null){
                setFieldValByName(CREATE_BY, userId, metaObject);
                setFieldValByName(UPDATE_BY, userId, metaObject);
            }
            setFieldValByName(CREATE_TIME, new Date(), metaObject);
            setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        } catch (ReflectionException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void updateFill (MetaObject metaObject){
        try {
            Long id=(Long) getFieldValByName(UPDATE_BY,metaObject);
            if(id==null){
                setFieldValByName(UPDATE_BY, this.getUserId(), metaObject);
                setFieldValByName(UPDATE_TIME, new Date(), metaObject);
            }

        } catch (ReflectionException e) {
            log.warn(e.getMessage());
        }
    }

    private Long getUserId () {
        return -1L;
    }
}