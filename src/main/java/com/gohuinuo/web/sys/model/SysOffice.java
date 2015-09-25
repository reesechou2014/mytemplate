//Powered By if, Since 2014 - 2020

package com.gohuinuo.web.sys.model;

import java.util.Date;
import java.util.List;

import com.gohuinuo.common.base.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author 
 */

@SuppressWarnings({ "unused"})
@Table(name="sys_office")
public class SysOffice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	
    private String address; //address <联系地址>

	
    private Long areaId; //area_id <归属区域>

	
    private String code; //code <区域编码>

	
    private String email; //email <邮箱>

	
    private String fax; //fax <传真>

	
    private String grade; //grade <机构等级>

	
    private String master; //master <负责人>

	
    private String name; //name <机构名称>

	
    private Long parentId; //parent_id <父级编号>

	
    private String parentIds; //parent_ids <所有父级编号>

	
    private String phone; //phone <电话>

	
    private String remarks; //remarks <备注信息>

	
    private String type; //type <机构类型>

	
    private String zipCode; //zip_code <邮政编码>
    
    private String icon; //icon <图标>
    
    private String updateBy; //update_by <更新者>
	private Date updateDate; //update_date <更新时间>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>
	private String delFlag; //del_flag <删除标记(0.正常  1.删除)>
    
    @Transient
    private String oldParentIds; //旧的pids,非表中字段，用作更新用
    
	public String getAddress() {
		return this.getString("address");
    }
   
    public void setAddress(String address) {
		this.set("address", address);
    }

	public Long getAreaId() {
		return this.getLong("areaId");
    }
   
    public void setAreaId(Long areaId) {
		this.set("areaId", areaId);
    }

	public String getCode() {
		return this.getString("code");
    }
   
    public void setCode(String code) {
		this.set("code", code);
    }

	public String getEmail() {
		return this.getString("email");
    }
   
    public void setEmail(String email) {
		this.set("email", email);
    }

	public String getFax() {
		return this.getString("fax");
    }
   
    public void setFax(String fax) {
		this.set("fax", fax);
    }

	public String getGrade() {
		return this.getString("grade");
    }
   
    public void setGrade(String grade) {
		this.set("grade", grade);
    }

	public String getMaster() {
		return this.getString("master");
    }
   
    public void setMaster(String master) {
		this.set("master", master);
    }

	public String getName() {
		return this.getString("name");
    }
   
    public void setName(String name) {
		this.set("name", name);
    }

	public Long getParentId() {
		return this.getLong("parentId");
    }
   
    public void setParentId(Long parentId) {
		this.set("parentId", parentId);
    }

	public String getParentIds() {
		return this.getString("parentIds");
    }
   
    public void setParentIds(String parentIds) {
		this.set("parentIds", parentIds);
    }

	public String getPhone() {
		return this.getString("phone");
    }
   
    public void setPhone(String phone) {
		this.set("phone", phone);
    }

	public String getRemarks() {
		return this.getString("remarks");
    }
   
    public void setRemarks(String remarks) {
		this.set("remarks", remarks);
    }

	public String getType() {
		return this.getString("type");
    }
   
    public void setType(String type) {
		this.set("type", type);
    }

	public String getZipCode() {
		return this.getString("zipCode");
    }
   
    public void setZipCode(String zipCode) {
		this.set("zipCode", zipCode);
    }
    
    public String getOldParentIds() {
		return this.getString("oldParentIds");
    }
   
    public void setOldParentIds(String oldParentIds) {
		this.set("oldParentIds", oldParentIds);
    }
    
    public String getIcon() {
		return this.getString("icon");
    }
   
    public void setIcon(String icon) {
		this.set("icon", icon);
    }
    
    public String getCreateBy() {
		return this.getString("createBy");
    }
   
    public void setCreateBy(String createBy) {
		this.set("createBy", createBy);
    }

	public Date getCreateDate() {
		return this.getDate("createDate");
    }
   
    public void setCreateDate(Date createDate) {
		this.set("createDate", createDate);
    }
    public String getUpdateBy() {
		return this.getString("updateBy");
    }
   
    public void setUpdateBy(String updateBy) {
		this.set("updateBy", updateBy);
    }

	public Date getUpdateDate() {
		return this.getDate("updateDate");
    }
   
    public void setUpdateDate(Date updateDate) {
		this.set("updateDate", updateDate);
    }

    public String getDelFlag() {
		return this.getString("delFlag");
    }
   
    public void setDelFlag(String delFlag) {
		this.set("delFlag", delFlag);
    }

}
