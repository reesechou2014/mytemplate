package com.gohuinuo.common.mybatis.provider;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.MetaObject;

import com.gohuinuo.common.constant.Constant;
import com.gohuinuo.common.mybatis.EntityHelper;


public class CommonSqlProvider extends BaseProvider{
	
	public String beforeDeleteTreeStructureSql(Map<String, Object> params){
		final String tableNameOne = params.get("t0").toString();
		final String tableNameTwo = params.get("t1").toString();
		final String checkField = params.get("checkField").toString();
		return new SQL(){{
			SELECT("count(0)");
			FROM(tableNameOne+" t0");
			FROM(tableNameTwo+" t1");
			WHERE("t1.parent_ids like concat('%,',#{id},',%') or t1.id=#{id}");
			AND();
			WHERE("t0."+checkField+"=t1.id");
			
		}}.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String findEntityListByDataScope(final Map<String, Object> params){
		Map<String,Object> map = (Map<String, Object>)params.get("record");
		map.put(Constant.FIELD_DEL_FLAG, Constant.DEL_FLAG_NORMAL);
		String sql = "";
		if(map.containsKey("userDataScope")){
			sql = map.get("userDataScope").toString();
		}
		final String dataScope = sql;
		return new SQL(){{
			Object entity = getEntity(params);
            Class<?> entityClass = getEntityClass(params);
            com.gohuinuo.common.mybatis.EntityHelper.EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
            SELECT(com.gohuinuo.common.mybatis.EntityHelper.getAllColumns(entityClass));
            FROM(entityTable.getName());
            if (entity != null) {
                final MetaObject metaObject = forObject(entity);
                for (EntityHelper.EntityColumn column : entityTable.getEntityClassColumns()) {
                    Object value = metaObject.getValue(column.getProperty());
                    if (column.getJavaType().equals(String.class)) {
                        if (isNotEmpty((String) value)) {
                            WHERE(column.getColumn() + "=#{record." + column.getProperty() + "}");
                        }
                    } else if (value != null) {
                        WHERE(column.getColumn() + "=#{record." + column.getProperty() + "}");
                    }
                }
            }
            if(StringUtils.isNotBlank(dataScope)){
            	WHERE(dataScope);
            }
		}}.toString();
	}
	
}
