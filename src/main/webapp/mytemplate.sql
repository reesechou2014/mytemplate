/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : mytemplate

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-04-18 22:07:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for maintain_task_definition
-- ----------------------------
DROP TABLE IF EXISTS `maintain_task_definition`;
CREATE TABLE `maintain_task_definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `cron` varchar(200) DEFAULT NULL,
  `bean_class` varchar(200) DEFAULT NULL,
  `bean_name` varchar(200) DEFAULT NULL,
  `method_name` varchar(200) DEFAULT NULL,
  `is_start` tinyint(1) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintain_task_definition
-- ----------------------------
INSERT INTO `maintain_task_definition` VALUES ('1', '测试1', '0/10 * * * * ? ', 'com.gohuinuo.web.maintain.dynamictask.task.TestTask1', 'testTask1', 'run', '0', null);

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '区域名称',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记(0活null 正常 1,删除)',
  `icon` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_parent_ids` (`parent_ids`(255)),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('1', '0', '0,', '100000', '中国', '1', '1', '2013-05-27 08:00:00', '2,超级管理员', '2015-02-28 20:37:03', '', '0', 'fa fa-institution');
INSERT INTO `sys_area` VALUES ('2', '1', '0,1,', '110000', '北京市', '2', '1', '2013-05-27 08:00:00', '22', '2015-01-20 22:15:47', '', '0', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('3', '2', '0,1,2,', '110101', '东城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_area` VALUES ('4', '2', '0,1,2,', '110102', '西城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_area` VALUES ('5', '2', '0,1,2,', '110103', '朝阳区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_area` VALUES ('6', '2', '0,1,2,', '110104', '丰台区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_area` VALUES ('7', '2', '0,1,2,', '110105', '海淀区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_area` VALUES ('8', '1', '0,1,', '370000', '山东省', '2', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0', null);
INSERT INTO `sys_area` VALUES ('9', '8', '0,1,8,', '370531', '济南市', '3', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0', null);
INSERT INTO `sys_area` VALUES ('10', '9', '0,1,8,9,', '370532', '高新区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0', null);
INSERT INTO `sys_area` VALUES ('11', '9', '0,1,8,9,', '370533', '历城区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0', null);
INSERT INTO `sys_area` VALUES ('12', '9', '0,1,8,9,', '370534', '历下区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0', null);
INSERT INTO `sys_area` VALUES ('13', '9', '0,1,8,9,', '370535', '天桥区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0', null);
INSERT INTO `sys_area` VALUES ('14', '9', '0,1,8,9,', '370536', '槐荫区', '4', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0', null);
INSERT INTO `sys_area` VALUES ('15', '1', '0,1,', '110000x', '测试导入', '2', '2,超级管理员', '2015-01-31 20:49:31', '22', '2015-01-31 20:49:31', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('16', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 20:57:38', '22', '2015-01-31 20:57:38', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('17', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 20:59:10', '22', '2015-01-31 20:59:10', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('18', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 21:00:07', '22', '2015-01-31 21:00:07', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('19', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 21:01:37', '22', '2015-01-31 21:01:37', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('20', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 21:04:52', '22', '2015-01-31 21:04:52', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('21', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 21:08:07', '22', '2015-01-31 21:08:07', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('22', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 21:09:53', '22', '2015-01-31 21:09:53', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('23', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 21:11:53', '22', '2015-01-31 21:11:53', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('24', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 21:13:31', '22', '2015-01-31 21:13:31', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('25', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:15:36', '22', '2015-01-31 22:15:36', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('26', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:16:18', '22', '2015-01-31 22:16:18', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('27', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:16:53', '22', '2015-01-31 22:16:53', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('28', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:17:20', '22', '2015-01-31 22:17:20', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('29', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:18:00', '22', '2015-01-31 22:18:00', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('30', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:19:39', '22', '2015-01-31 22:19:39', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('31', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:21:10', '22', '2015-01-31 22:21:10', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('32', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:21:37', '22', '2015-01-31 22:21:37', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('33', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-01-31 22:23:28', '22', '2015-01-31 22:23:28', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('34', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-02-02 01:27:54', '22', '2015-02-02 01:27:54', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('35', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-02-02 01:28:44', '22', '2015-02-02 01:28:44', '', '1', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('36', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-02-02 01:30:40', '22', '2015-02-02 01:30:40', '', '0', 'fa fa-smile-o');
INSERT INTO `sys_area` VALUES ('37', '1', '0,1,', '110000', '测试导入', '2', '2,超级管理员', '2015-02-02 01:31:00', '22', '2015-02-02 01:31:00', '', '0', 'fa fa-smile-o');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序（升序）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '正常', '0', 'del_flag', '删除标记', '10', '1', '2013-05-27 08:00:00', '2,超级管理员', '2015-02-28 23:07:13', null, '0');
INSERT INTO `sys_dict` VALUES ('2', '删除', '1', 'del_flag', '删除标记', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('3', '显示', '1', 'show_hide', '显示/隐藏', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('4', '隐藏', '0', 'show_hide', '显示/隐藏', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('5', '是', '1', 'yes_no', '是/否', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('6', '否', '0', 'yes_no', '是/否', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('17', '国家', '1', 'sys_area_type', '区域类型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('18', '省份、直辖市', '2', 'sys_area_type', '区域类型', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('19', '地市', '3', 'sys_area_type', '区域类型', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('20', '区县', '4', 'sys_area_type', '区域类型', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('22', '部门', '2', 'sys_office_type', '机构类型', '70', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('23', '一级', '1', 'sys_office_grade', '机构等级', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('24', '二级', '2', 'sys_office_grade', '机构等级', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('25', '三级', '3', 'sys_office_grade', '机构等级', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('26', '四级', '4', 'sys_office_grade', '机构等级', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('27', '所有数据', '1', 'sys_data_scope', '数据范围', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('28', '所在公司及以下数据', '2', 'sys_data_scope', '数据范围', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('29', '所在公司数据', '3', 'sys_data_scope', '数据范围', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('30', '所在部门及以下数据', '4', 'sys_data_scope', '数据范围', '40', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('31', '所在部门数据', '5', 'sys_data_scope', '数据范围', '50', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('32', '仅本人数据', '8', 'sys_data_scope', '数据范围', '90', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('33', '按明细设置', '9', 'sys_data_scope', '数据范围', '100', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('34', '系统管理', '1', 'sys_user_type', '用户类型', '10', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('35', '部门经理', '2', 'sys_user_type', '用户类型', '20', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('36', '普通用户', '3', 'sys_user_type', '用户类型', '30', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('62', '操作日志', '1', 'sys_log_type', '日志类型', '30', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('63', '异常日志', '2', 'sys_log_type', '日志类型', '40', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('64', '单表增删改查', 'single', 'prj_template_type', '代码模板', '10', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('65', '所有entity和dao', 'entityAndDao', 'prj_template_type', '代码模板', '20', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('66', '公司', '1', 'sys_office_type', '', '1', null, '2015-01-10 22:15:43', null, '2015-01-10 22:15:43', null, '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  `description` text COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_date`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('20', '2', '2,超级管理员', '2015-03-01 14:23:57', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36', '/mytemplate/test/area/save', 'POST', '[参数1，类型:SysArea，值:{\"updateBy\":\"2,超级管理员\",\"id\":1,\"parentId\":0,\"icon\":\"fa fa-institution\",\"parentIds\":\"0,\",\"oldParentIds\":\"0,\",\"name\":\"中国\",\"remarks\":\"\",\"code\":\"100000\",\"type\":\"1\",\"updateDate\":1425191037467}]', 'java.lang.ArithmeticException: / by zero', null);
INSERT INTO `sys_log` VALUES ('21', '2', '2,超级管理员', '2015-03-01 14:24:42', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36', '/mytemplate/test/area/save', 'POST', '[参数1，类型:SysArea，值:{\"updateBy\":\"2,超级管理员\",\"id\":1,\"parentId\":0,\"icon\":\"fa fa-institution\",\"parentIds\":\"0,\",\"oldParentIds\":\"0,\",\"name\":\"中国\",\"remarks\":\"\",\"code\":\"100000\",\"type\":\"1\",\"updateDate\":1425191082730}]', 'java.lang.ArithmeticException: / by zero', null);
INSERT INTO `sys_log` VALUES ('22', '2', '2,超级管理员', '2015-03-01 14:25:53', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36', '/mytemplate/test/area/save', 'POST', '[参数1，类型:SysArea，值:{\"updateBy\":\"2,超级管理员\",\"id\":1,\"parentId\":0,\"icon\":\"fa fa-institution\",\"parentIds\":\"0,\",\"oldParentIds\":\"0,\",\"name\":\"中国\",\"remarks\":\"\",\"code\":\"100000\",\"type\":\"1\",\"updateDate\":1425191153080}]', 'java.lang.ArithmeticException: / by zero', null);
INSERT INTO `sys_log` VALUES ('23', '2', '2,超级管理员', '2015-03-01 14:30:23', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36', '/mytemplate/test/area/save', 'POST', '[参数1，类型:SysArea，值:{\"updateBy\":\"2,超级管理员\",\"id\":1,\"parentId\":0,\"icon\":\"fa fa-institution\",\"parentIds\":\"0,\",\"oldParentIds\":\"0,\",\"name\":\"中国\",\"remarks\":\"\",\"code\":\"100000\",\"type\":\"1\",\"updateDate\":1425191423732}]', 'java.lang.ArithmeticException: / by zero', null);
INSERT INTO `sys_log` VALUES ('24', '2', '2,超级管理员', '2015-03-01 14:39:04', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36', '/mytemplate/test/area/save', 'POST', '[参数1，类型:SysArea，值:{\"updateBy\":\"2,超级管理员\",\"id\":1,\"parentId\":0,\"icon\":\"fa fa-institution\",\"parentIds\":\"0,\",\"oldParentIds\":\"0,\",\"name\":\"中国\",\"remarks\":\"\",\"code\":\"100000\",\"type\":\"1\",\"updateDate\":1425191923185}]', 'java.lang.ArithmeticException: / by zero', null);
INSERT INTO `sys_log` VALUES ('25', '2', '2,超级管理员', '2015-03-29 17:18:50', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36', '/mytemplate/menu/list', 'POST', '[参数1，类型:LinkedHashMap，值:{name=, id=, pageNum=1, pageSize=10, _ORIGINAL_PARAMETER_OBJECT=(this Map), First_PageHelper=0, Second_PageHelper=10}]', 'org.springframework.jdbc.BadSqlGrammarException: \r\n### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'sr.code\' in \'field list\'\r\n### The error may exist in file [E:\\develop_software\\Tomcat\\apache-tomcat-8.0.20\\webapps\\mytemplate\\WEB-INF\\classes\\com\\template\\web\\sys\\mapper\\SysResourceMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: select sr.id,sr.code,sr.description,sr.icon,sr.name,sr.sort,sr.common,sr.pos,   sr.status,sr.type,sr.url,srp.name pname,srp.id pid   from sys_resource sr   left join sys_resource srp   ON sr.parent_id=srp.id        ORDER BY sr.update_date desc limit ?,?\r\n### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'sr.code\' in \'field list\'\n; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column \'sr.code\' in \'field list\'', null);

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `area_id` bigint(20) NOT NULL COMMENT '归属区域',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '机构名称',
  `type` char(1) DEFAULT NULL COMMENT '机构类型',
  `grade` char(1) DEFAULT NULL COMMENT '机构等级',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `icon` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_parent_ids` (`parent_ids`(255)),
  KEY `sys_office_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '0', '0,', '2', '100000', '北京市总公司', '1', '1', '', null, '', '', '', '', '1', '2013-05-27 08:00:00', '2,超级管理员', '2015-02-28 20:49:57', '', '0', 'fa fa-bicycle');
INSERT INTO `sys_office` VALUES ('2', '1', '0,1,', '2', '100001', '公司领导', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('3', '1', '0,1,', '2', '100002', '人力部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('4', '1', '0,1,', '2', '100003', '市场部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('5', '1', '0,1,', '2', '100004', '技术部', '2', '4', '', null, '', '', '', '', '1', '2013-05-27 08:00:00', '22', '2015-01-24 16:39:03', '', '0', '');
INSERT INTO `sys_office` VALUES ('6', '1', '0,1,', '2', '100005', '研发部', '2', '1', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0', null);
INSERT INTO `sys_office` VALUES ('7', '1', '0,1,', '3', '200000', '山东省分公司', '1', '2', '', '', '', '', '', '', '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', '', '0', null);
INSERT INTO `sys_office` VALUES ('8', '7', '0,1,7,', '8', '200001', '公司领导', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('9', '7', '0,1,7,', '8', '200002', '综合部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('10', '7', '0,1,7,', '8', '200003', '市场部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('11', '7', '0,1,7,', '8', '200004', '技术部', '2', '2', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('12', '7', '0,1,7,', '9', '201000', '济南市分公司', '1', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('13', '12', '0,1,7,12,', '9', '201001', '公司领导', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('14', '12', '0,1,7,12,', '9', '201002', '综合部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('15', '12', '0,1,7,12,', '9', '201003', '市场部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('16', '12', '0,1,7,12,', '9', '201004', '技术部', '2', '3', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('17', '12', '0,1,7,12,', '11', '201010', '济南市历城区分公司', '1', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('18', '17', '0,1,7,12,17,', '11', '201011', '公司领导', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('19', '17', '0,1,7,12,17,', '11', '201012', '综合部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('20', '17', '0,1,7,12,17,', '11', '201013', '市场部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('21', '17', '0,1,7,12,17,', '11', '201014', '技术部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('22', '12', '0,1,7,12,', '12', '201020', '济南市历下区分公司', '1', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('23', '22', '0,1,7,12,22,', '12', '201021', '公司领导', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('24', '22', '0,1,7,12,22,', '12', '201022', '综合部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('25', '22', '0,1,7,12,22,', '12', '201023', '市场部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('26', '22', '0,1,7,12,22,', '12', '201024', '技术部', '2', '4', null, null, null, null, null, null, '1', '2013-05-27 08:00:00', '2', '2014-11-23 22:00:25', null, '0', null);
INSERT INTO `sys_office` VALUES ('27', '5', '0,1,5,', '2', null, '技术1部门', '2', null, '', null, '', '', '', '', null, '2015-01-11 15:19:53', null, '2015-01-11 15:19:53', '', '0', null);
INSERT INTO `sys_office` VALUES ('28', '0', '0,', '1', null, '测试公司名字一定要长', '1', null, '', null, '', '', '', '', null, '2015-01-11 23:48:22', null, '2015-01-12 21:46:32', '', '1', null);
INSERT INTO `sys_office` VALUES ('29', '28', '0,28,', '1', null, '测试部门1', '2', null, '', null, '', '', '', '', null, '2015-01-11 23:48:35', null, '2015-01-11 23:48:35', '', '1', null);
INSERT INTO `sys_office` VALUES ('30', '10', '0,1,7,10,', '1', null, '市场子部门', '2', null, '', null, '', '', '', '', null, '2015-01-13 22:56:14', null, '2015-01-13 22:56:14', '', '0', null);
INSERT INTO `sys_office` VALUES ('35', '0', '0,', '1', null, 'fsdfsdf', '2', '1', '', null, '', '', '', '', null, '2015-01-14 23:13:43', null, '2015-01-14 23:13:43', '', '0', null);
INSERT INTO `sys_office` VALUES ('36', '35', '0,35,', '1', null, '2222', '2', '2', '', null, '', '', '', '', '2', '2015-01-18 20:29:53', '2', '2015-01-18 20:34:08', '', '0', null);
INSERT INTO `sys_office` VALUES ('37', '1', '0,1,', '2', null, '测测', '2', '2', '', null, '', '', '', '', '22', '2015-01-24 15:19:09', null, '2015-01-24 15:19:09', '', '0', '');
INSERT INTO `sys_office` VALUES ('38', '5', '0,1,5,', '2', null, 'sdsd', '2', '3', '', null, '', '', '', '', '22', '2015-01-24 17:08:50', null, '2015-01-24 17:08:50', '', '0', '');
INSERT INTO `sys_office` VALUES ('39', '35', '0,35,', '1', null, 'sssddd', '2', '2', '', null, '', '', '', '', '2', '2015-01-24 17:35:09', null, '2015-01-24 17:35:09', '', '0', '');
INSERT INTO `sys_office` VALUES ('40', '1', '0,1,', '2', null, '测试', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 10:23:15', null, '2015-01-25 10:23:15', '', '1', '');
INSERT INTO `sys_office` VALUES ('41', '1', '0,1,', '2', null, 'aaaa', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 21:34:43', null, '2015-01-25 21:34:43', '', '0', '');
INSERT INTO `sys_office` VALUES ('42', '1', '0,1,', '2', null, 'aaaa', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 21:37:13', null, '2015-01-25 21:37:13', '', '1', '');
INSERT INTO `sys_office` VALUES ('43', '1', '0,1,', '2', null, 'ffffddd', '2', '2', '', null, '', '', '', '', '22', '2015-01-25 21:37:48', null, '2015-01-25 21:37:48', '', '0', '');
INSERT INTO `sys_office` VALUES ('45', '1', '0,1,', '2', null, '测试自动赋权', '2', '2', '', null, '', '', '', '', '22', '2015-01-27 20:02:50', null, '2015-01-27 20:02:50', '', '0', '');
INSERT INTO `sys_office` VALUES ('46', '1', '0,1,', '2', null, 'cc22', '2', '2', '', null, '', '', '', '', '22', '2015-01-27 20:19:45', null, '2015-01-27 20:19:45', '', '0', '');
INSERT INTO `sys_office` VALUES ('47', '0', '0,', '1', null, 'sss', '1', '1', '', null, '', '', '', '', '2', '2015-01-28 21:46:00', null, '2015-01-28 21:46:00', '', '1', '');
INSERT INTO `sys_office` VALUES ('48', '1', '0,1,', '1', null, 'dd', '1', '2', '', null, '', '', '', '', '22', '2015-01-28 22:33:04', null, '2015-01-28 22:33:04', '', '0', '');
INSERT INTO `sys_office` VALUES ('49', '0', '0,', '1', null, 'xcxzcxc', '2', '1', '', null, '', '', '', '', '22', '2015-01-28 22:55:37', null, '2015-01-28 22:55:37', '', '0', '');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `common` char(1) DEFAULT '0' COMMENT '是否是公共资源(0.不是 1.是)',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `sort` int(11) DEFAULT '1' COMMENT '排序号',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `type` char(1) DEFAULT '0' COMMENT '类型(0.菜单 1.按钮)',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态(0.正常 1.禁用)',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '父级集合',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `permission_str` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', '菜单配置', '0', 'fa fa-list', '1', '188', '0', 'menu', '', '0', '0,188,', null, '2015-03-11 23:12:27', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('181', '区域管理', '0', 'fa fa-globe', '1', '189', '0', 'area', '', '0', '0,189,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('188', '系统管理', '0', 'fa fa-cogs', '1', '0', '0', '', '', '0', '0,', null, '2015-03-12 23:57:18', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('189', '机构用户', '0', 'fa fa-group', '2', '0', '0', '', '', '0', '0,', null, '2015-01-18 11:00:57', null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('190', '字典管理', '0', 'fa fa-calculator', '1', '188', '0', 'dict', '', '0', '0,188,', null, '2015-03-11 23:12:41', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('192', '机构管理', '0', 'fa fa-sitemap', '1', '189', '0', 'office', '', '0', '0,189,', null, '2015-03-11 23:08:59', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('193', '用户管理', '0', 'fa fa-user', '1', '189', '0', 'sysuser', '', '0', '0,189,', null, '2015-03-11 23:07:11', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('194', '角色管理', '0', 'fa fa-graduation-cap', '2', '189', '0', 'role', '', '0', '0,189,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('195', '日志查询', '0', 'fa fa-copy', '1', '188', '0', 'syslog', '', '0', '0,188,', null, null, null, null, '0', null);
INSERT INTO `sys_resource` VALUES ('200', '测试', '0', '', '7', '0', '0', 'test', '', '0', '0,', null, '2015-03-14 20:04:08', null, '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('203', '搜索按钮', '0', 'fa fa-angellist', '1', '181', '1', 'sys:area:find', '这是一个按钮级别的示例，页面为添加，请添加@if(auth.hasPermission(\"sys:area:find\")){}测试', '0', '0,189,181,', '2015-01-20 20:50:16', '2015-01-20 20:57:38', '22', '22', '0', null);
INSERT INTO `sys_resource` VALUES ('204', '系统监控', '0', 'fa fa-binoculars', '6', '0', '0', '', '', '0', '0,', '2015-03-03 20:11:10', '2015-03-11 23:12:56', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('205', 'Ehcache监控', '0', 'fa fa-crosshairs', '1', '204', '0', 'monitor/ehcache', '', '0', '0,204,', '2015-03-03 20:11:19', '2015-03-11 23:15:52', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('206', 'jvm监控', '0', 'fa fa-flash', '1', '204', '0', 'monitor/jvm', '', '0', '0,204,', '2015-03-08 11:17:00', '2015-03-11 23:20:19', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('207', '执行sql', '0', 'fa fa-ge', '1', '204', '0', 'monitor/db/sql', '', '0', '0,204,', '2015-03-09 21:07:49', '2015-03-11 23:18:39', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('208', '数据库监控', '0', 'fa fa-github-alt', '1', '204', '0', 'monitor/db/druid', '', '0', '0,204,', '2015-03-10 21:11:20', '2015-03-11 23:19:56', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('209', '个人中心', '0', 'fa fa-user', '1', '0', '0', '', '', '0', '0,', '2015-03-12 19:58:59', '2015-03-14 19:39:10', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('210', '我的资料', '0', 'fa fa-file-o', '1', '209', '0', 'userCenter', '', '0', '0,209,', '2015-03-12 19:59:40', '2015-03-18 22:03:58', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('211', '聊天室', '0', '', '1', '209', '0', 'userCenter/conversation', '', '0', '0,209,', '2015-03-25 13:27:57', '2015-03-25 13:34:14', '2,超级管理员', '2,超级管理员', '0', null);
INSERT INTO `sys_resource` VALUES ('212', 'CMS示例', '0', 'fa fa-copyright', '1', '0', '0', '', '', '0', '0,', '2015-03-25 16:15:31', '2015-03-25 16:15:31', '2,超级管理员', null, '0', null);
INSERT INTO `sys_resource` VALUES ('213', '文章列表', '0', '', '1', '212', '0', 'cms/article', '', '0', '0,212,', '2015-03-25 16:16:10', '2015-03-25 16:40:14', '2,超级管理员', '2,超级管理员', '0', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `office_id` bigint(20) DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('15', '1', '平民', '9', null, '2015-01-17 20:36:23', '22', '2015-01-25 10:22:54', null, '0');
INSERT INTO `sys_role` VALUES ('16', '1', '大臣', '1', null, '2015-01-18 13:01:02', '2', '2015-01-28 22:32:39', null, '0');
INSERT INTO `sys_role` VALUES ('17', '27', 'ceshi', '9', '22', '2015-01-24 20:53:58', null, '2015-01-24 20:53:58', null, '0');
INSERT INTO `sys_role` VALUES ('18', '1', '测试1', '9', '22', '2015-01-25 21:33:16', null, '2015-01-25 21:33:16', null, '0');

-- ----------------------------
-- Table structure for sys_role_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_office`;
CREATE TABLE `sys_role_office` (
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `office_id` bigint(20) NOT NULL COMMENT '机构编号',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色-机构';

-- ----------------------------
-- Records of sys_role_office
-- ----------------------------
INSERT INTO `sys_role_office` VALUES ('15', '1', '1', null, null, null, null, '0');
INSERT INTO `sys_role_office` VALUES ('15', '2', '2', null, null, null, null, '0');
INSERT INTO `sys_role_office` VALUES ('15', '3', '3', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1323 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('823', '15', '188', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('824', '15', '1', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('825', '15', '190', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('826', '15', '195', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('827', '15', '189', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('828', '15', '181', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('829', '15', '203', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('830', '15', '192', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('831', '15', '193', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('832', '15', '194', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('833', '15', '200', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('996', '18', '188', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('997', '18', '1', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('998', '18', '190', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('999', '18', '195', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1000', '18', '189', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1001', '18', '181', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1002', '18', '203', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1003', '18', '192', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1004', '18', '193', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1005', '18', '194', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1313', '16', '188', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1314', '16', '1', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1315', '16', '190', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1316', '16', '195', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1317', '16', '189', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1318', '16', '181', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1319', '16', '192', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1320', '16', '193', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1321', '16', '194', null, null, null, null, '0');
INSERT INTO `sys_role_resource` VALUES ('1322', '16', '200', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `company_id` bigint(20) NOT NULL COMMENT '归属公司',
  `office_id` bigint(20) NOT NULL COMMENT '归属部门',
  `username` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT '0' COMMENT '用户类型(0.普通 1.系统超级管理员)',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `status` char(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`),
  KEY `sys_user_login_name` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2', '1', '1', 'admin', '86f3059b228c8acf99e69734b6bb32cc', '0002', '超级管理员', '@163.com', '8675', '8675', '1', '0:0:0:0:0:0:0:1', '2015-04-16 21:32:54', '1', '2013-05-27 08:00:00', '2,超级管理员', '2015-04-16 21:32:54', '管理员', '0', '0');
INSERT INTO `sys_user` VALUES ('22', '1', '1', 'ceshi1', 'd851ea96c7f9d003938f562957be5f60', '', '测试1', '', '2333', '', '0', '0:0:0:0:0:0:0:1', '2015-03-18 22:00:31', null, '2015-01-17 19:14:05', '22,测试1', '2015-03-18 22:00:31', '', '0', '0');
INSERT INTO `sys_user` VALUES ('23', '1', '6', 'ceshi3', '053d1c300518bcefb75352d022f45d00', '', '韩流', '', '3333', '', '0', null, null, '22', '2015-01-25 12:13:04', null, '2015-01-25 12:13:04', '', '0', '0');
INSERT INTO `sys_user` VALUES ('24', '1', '27', 'ceshi4', '7f8c872d354b49473259f0900113eec5', '', '王五误', '', '444', '', '0', null, null, '22', '2015-01-25 13:30:35', null, '2015-01-25 13:30:35', '', '0', '0');
INSERT INTO `sys_user` VALUES ('25', '1', '2', 'ceshi5', 'd3c884a0dbee705ecd52ce0811fa80d7', '', '测试mapper', '', '555', '', '0', null, null, '22', '2015-01-25 21:38:37', null, '2015-01-25 21:38:37', '', '0', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('59', '15', '23', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('67', '16', '22', null, null, null, null, '0');
