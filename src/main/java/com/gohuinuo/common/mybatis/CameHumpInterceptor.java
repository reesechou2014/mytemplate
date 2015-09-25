package com.gohuinuo.common.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.*;

/**
 * MyBatis Map类型大写下划线Key转小写驼峰形式
 *
 * @since 1.0.0
 */
@Intercepts(
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
)
@SuppressWarnings("rawtypes")
public class CameHumpInterceptor implements Interceptor {

    public static final String RESULT_TYPE = "-Inline";

    
	@Override
    public Object intercept(Invocation invocation) throws Throwable {
        //先执行，后处理
        Object result = invocation.proceed();
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        ResultMap resultMap = mappedStatement.getResultMaps().get(0);
        Class<?> type = resultMap.getType();
        //只有有返回值并且type是Map的时候,还不能是嵌套复杂的resultMap,才需要特殊处理
        if (((List) result).size() > 0
                && Map.class.isAssignableFrom(type)
                && !resultMap.hasNestedQueries()
                && !resultMap.hasNestedResultMaps()) {
            List resultList = (List) result;
            //1.resultType时
            if (resultMap.getId().endsWith(RESULT_TYPE)) {
                for (Object re : resultList) {
                	if(re == null) continue;
                    processMap((Map) re);
                }
            } else {//2.resultMap时
                for (Object re : resultList) {
                    processMap((Map) re, resultMap.getResultMappings());
                }
            }
        }
        return result;
    }

    /**
     * 处理简单对象
     *
     * @param map
     */
	@SuppressWarnings("unchecked")
	private void processMap(Map map) {
        Map cameHumpMap = new HashMap();
        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            String key = (String) entry.getKey();
            String cameHumpKey = underlineToCamelhump(key);
            if (!key.equals(cameHumpKey)) {
                cameHumpMap.put(cameHumpKey, entry.getValue());
                iterator.remove();
            }
        }
        map.putAll(cameHumpMap);
    }

    /**
     * 配置过的属性不做修改
     *
     * @param map
     * @param resultMappings
     */
    @SuppressWarnings("unchecked")
	private void processMap(Map map, List<ResultMapping> resultMappings) {
        Set<String> propertySet = toPropertySet(resultMappings);
        Map cameHumpMap = new HashMap();
        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            String key = (String) entry.getKey();
            if (propertySet.contains(key)) {
                continue;
            }
            
            String cameHumpKey = underlineToCamelhump(key);
            if (!key.equals(cameHumpKey)) {
                cameHumpMap.put(cameHumpKey, entry.getValue());
                iterator.remove();
            }
        }
        map.putAll(cameHumpMap);
    }

    /**
     * 列属性转Set
     *
     * @param resultMappings
     * @return
     */
    private Set<String> toPropertySet(List<ResultMapping> resultMappings) {
        Set<String> propertySet = new HashSet<String>();
        for (ResultMapping resultMapping : resultMappings) {
            propertySet.add(resultMapping.getProperty());
        }
        return propertySet;
    }
    
    /**
	 * 将下划线风格替换为驼峰风格
	 */
	public static String underlineToCamelhump(String name) {
		char[] buffer = name.toCharArray();
		int count = 0;
		boolean lastUnderscore = false;
		for (int i = 0; i < buffer.length; i++) {
			char c = buffer[i];
			if (c == '_') {
				lastUnderscore = true;
			} else {
				c = (lastUnderscore && count != 0) ? toUpperAscii(c)
						: toLowerAscii(c);
				buffer[count++] = c;
				lastUnderscore = false;
			}
		}
		if (count != buffer.length) {
			buffer = subarray(buffer, 0, count);
		}
		return new String(buffer);
	}
    
    public static char[] subarray(char[] src, int offset, int len) {
        char[] dest = new char[len];
        System.arraycopy(src, offset, dest, 0, len);
        return dest;
    }
    
    public static boolean isLowercaseAlpha(char c) {
        return (c >= 'a') && (c <= 'z');
    }
    
    public static char toUpperAscii(char c) {
        if (isLowercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }

    public static char toLowerAscii(char c) {
        if ((c >= 'A') && (c <= 'Z')) {
            c += (char) 0x20;
        }
        return c;
    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
