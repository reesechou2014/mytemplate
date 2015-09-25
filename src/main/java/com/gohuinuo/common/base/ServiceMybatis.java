package com.gohuinuo.common.base;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gohuinuo.common.constant.Constant;
import com.gohuinuo.common.mybatis.mapper.BaseMapper;
import com.gohuinuo.common.spring.utils.SpringContextHolder;
import com.gohuinuo.common.utils.Collections3;
import com.gohuinuo.common.utils.StringConvert;
import com.gohuinuo.web.sys.model.SysUser;
import com.gohuinuo.web.sys.utils.SysUserUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

public abstract class ServiceMybatis<T extends BaseEntity> implements BaseService<T>{

	@Autowired
	protected Mapper<T> mapper;
	
	@Resource
	protected BaseMapper baseMapper;
	
	
	/**
	 * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
	 * @param <T extend T>
	 */
	public List<T> select(T record) {
		record.set("delFlag", Constant.DEL_FLAG_NORMAL);
		return mapper.select(record);
	}
	
	public List<T> select(T record,String orderSqlStr){
		Example example = new Example(record.getClass(),false);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("delFlag", Constant.DEL_FLAG_NORMAL);
		for(Map.Entry<String, Object> entry : record.entrySet()){
			if("".equals(entry.getValue())) continue;
			criteria.andEqualTo(entry.getKey(), entry.getValue());
		}
		example.setOrderByClause(orderSqlStr);
		return mapper.selectByExample(example);
	}
	
	/**
	 * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
	 * @param <T extend T>
	 */
	public int selectCount(T record) {
		record.set("delFlag", Constant.DEL_FLAG_NORMAL);
		return mapper.selectCount(record);
	}

	/**
	 * 根据主键进行查询,必须保证结果唯一 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
	 * 
	 * @param <T extend T>
	 */
	public T selectByPrimaryKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	/**
	 * 插入一条数据 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
	 * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * 
	 * @param <T extend T>
	 */
	public int insert(T record) {
		record.set("createDate",new Date() );
		record.set("updateDate", new Date());
		record.set("delFlag", Constant.DEL_FLAG_NORMAL);
		record.set("createBy", SysUserUtils.getCacheLoginUser().getId()+","+
				SysUserUtils.getCacheLoginUser().getName());
		return mapper.insert(record);
	}

	/**
	 * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
	 * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
	 * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
	 * 
	 * @param <T extend T>
	 */
	public int insertSelective(T record) {
		record.set("createDate",new Date() );
		record.set("updateDate", new Date());
		record.set("delFlag", Constant.DEL_FLAG_NORMAL);
		record.set("createBy", SysUserUtils.getCacheLoginUser().getId()+","+
				SysUserUtils.getCacheLoginUser().getName());
		return mapper.insertSelective(record);
	}

	/**
	 * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
	 * 
	 * @param <T extend T>
	 */
	public int delete(T key) {
		return mapper.delete(key);
	}

	/**
	 * 通过主键进行删除,这里最多只会删除一条数据 单个字段做主键时,可以直接写主键的值 联合主键时,key可以是实体类,也可以是Map
	 * 
	 * @param <T extend T>
	 */
	public int deleteByPrimaryKey(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	/**
	 * 根据主键进行更新,这里最多只会更新一条数据 参数为实体类
	 * 
	 * @param <T extend T>
	 */
	public int updateByPrimaryKey(T record) {
		SysUser sysUser = SysUserUtils.getCacheLoginUser();
		if(sysUser != null){
			record.set("updateBy",sysUser.getId()+","+
					SysUserUtils.getCacheLoginUser().getName());
		}
		record.set("updateDate", new Date());
		return mapper.updateByPrimaryKey(record);
	}

	/**
	 * 根据主键进行更新 只会更新不是null的数据
	 * 
	 * @param <T extend T>
	 */
	public int updateByPrimaryKeySelective(T record) {
		record.set("updateBy",SysUserUtils.getCacheLoginUser().getId()+","+
				SysUserUtils.getCacheLoginUser().getName());
		record.set("updateDate", new Date());
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 单表逻辑删除(需要有delFlag)
	* @param bean 删除的实体类型
	* @return 影响行数
	 */
	public <M extends BaseEntity> int updateDelFlagToDelStatusById(Class<M> bean,Long id){
		String mapperName = StringUtils.uncapitalize(bean.getSimpleName())+"Mapper"; 
		Mapper<M> mapper = SpringContextHolder.getBean(mapperName);
		M m = null;
		try {
			m = bean.newInstance();
			m.setId(id);
			m.set("delFlag", Constant.DEL_FLAG_DELETE);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return mapper.updateByPrimaryKeySelective(m);
	}

	/**
	 * 保存或者更新，根据传入id主键是不是null来确认
	 * 
	 * @param record
	 * @return 影响行数
	 */
	public int save(T record) {
		int count = 0;
		if (record.get("id") == null) {
			count = this.insertSelective(record);
		} else {
			count = this.updateByPrimaryKeySelective(record);
		}
		return count;
	}

	/**
	 * 单表分页
	 * @param pageNum 页码
	 * @param pageSize 条数
	 * @param record 条件实体
	 * @return
	 */
	public PageInfo<T> selectPage(int pageNum, int pageSize, T record) {
		record.set("delFlag", Constant.DEL_FLAG_NORMAL);
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<T>(mapper.select(record));
	}
	
	/**
	 * @Description:(单表分页可排序) 
	 * @param:@param pageNum
	 * @param:@param pageSize
	 * @param:@param record
	 * @param:@param orderSqlStr (如:id desc)
	 * @return:PageInfo<T>
	 */
	public PageInfo<T> selectPage(int pageNum, int pageSize, T record,String orderSqlStr) {
		Example example = new Example(record.getClass(),false);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("delFlag", Constant.DEL_FLAG_NORMAL);
		for(Map.Entry<String, Object> entry : record.entrySet()){
			if("".equals(entry.getValue())) continue;
			criteria.andEqualTo(entry.getKey(), entry.getValue());
		}
		example.setOrderByClause(orderSqlStr);
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = mapper.selectByExample(example);
		return new PageInfo<T>(list);
	}

	/**
	 * 删除前验证是否有关联(仅限于单表)
	* @param bean 实体class
	* @param fields 检查的实体属性
	* @param values 属性值
	* @return -1有关联
	 */
	public <M extends BaseEntity> int beforeDelete(Class<M> bean,Map<String, Object> params){
		String mapperName = StringUtils.uncapitalize(bean.getSimpleName())+"Mapper"; 
		Mapper<M> mapper = SpringContextHolder.getBean(mapperName);
		M m = null;
		try {
			m = bean.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		m.setAll(params);
		int count = mapper.selectCount(m);
		return count>0 ? -1:count;
	}
	
	/**
	 * 有树结构的删除前验证(适应于两表)
	* @param id 删除的id
	* @param Field 验证的属性名称
	* @param beans class 第一个是要验证的class 第二个为删除的class
	* @return 未通过返回-1
	 */
	public int beforeDeleteTreeStructure(Object id,String Field,Class<?>... beans){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("checkField", StringConvert.camelhumpToUnderline(Field));
		for(int i=0;i<beans.length;i++ ){
			Class<?> cl = beans[i];
			String tableName = StringConvert.camelhumpToUnderline(cl.getSimpleName());
			map.put("t"+i, tableName);
		}
		
		int count = baseMapper.beforeDeleteTreeStructure(map);
		return  count>0?-1:count;
	}
	
	/**
	 * 根据数据范围查找(单表操作)    
	* @param record 如果自定义别名等请set key:"userDataScope"
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> findEntityListByDataScope(E record){
		List<Map<String, Object>> list = baseMapper.findEntityListByDataScope(record);
		List<E> beanList = (List<E>) Collections3.maplist2BeanList(list, record.getClass());
		return beanList;
	}

}
