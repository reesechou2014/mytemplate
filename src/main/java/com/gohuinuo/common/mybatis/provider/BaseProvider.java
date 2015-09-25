package com.gohuinuo.common.mybatis.provider;

import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

public class BaseProvider {

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    /**
     * 反射对象，增加对低版本Mybatis的支持
     *
     * @param object 反射对象
     * @return
     */
    public static MetaObject forObject(Object object) {
        return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
    }

    protected Class<?> getEntityClass(Map<String, Object> params) {
        if (params.containsKey("record")) {
            return params.get("record").getClass();
        } else if (params.containsKey("entityClass")) {
            return (Class<?>) params.get("entityClass");
        }
        //TODO 稍后修改为更准确的提示信息
        throw new RuntimeException("实体类型获取失败!");
    }

    protected Object getEntity(Map<String, Object> params) {
        if (params.containsKey("record")) {
            return params.get("record");
        } else if (params.containsKey("key")) {
            return params.get("key");
        }
        //TODO 稍后修改为更准确的提示信息
        throw new RuntimeException("实体类获取失败!");
    }

    protected boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    protected boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
    
    protected void throwNullKeyException(String property) {
        throw new NullPointerException("主键属性" + property + "不能为空!");
    }
    
    /**
     * 主键字段不能为空
     *
     * @param property
     * @param value
     */
    protected void notNullKeyProperty(String property, Object value) {
        if (value == null || (value instanceof String && isEmpty((String) value))) {
            throwNullKeyException(property);
        }
    }
    
}
