/*
 Navicat Premium Data Transfer

 Source Server         : yun-test
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : 175.24.112.136:3306
 Source Schema         : imooc-oa

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 21/12/2021 11:29:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for adm_department
-- ----------------------------
DROP TABLE IF EXISTS `adm_department`;
CREATE TABLE `adm_department`  (
  `department_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `department_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名',
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of adm_department
-- ----------------------------
INSERT INTO `adm_department` VALUES (1, '总裁办');
INSERT INTO `adm_department` VALUES (2, '研发部');
INSERT INTO `adm_department` VALUES (3, '市场部');

-- ----------------------------
-- Table structure for adm_employee
-- ----------------------------
DROP TABLE IF EXISTS `adm_employee`;
CREATE TABLE `adm_employee`  (
  `employee_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工名',
  `department_id` bigint(20) NULL DEFAULT NULL COMMENT '部门id',
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工信息',
  `level` int(11) NULL DEFAULT NULL COMMENT '员工等级',
  PRIMARY KEY (`employee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of adm_employee
-- ----------------------------
INSERT INTO `adm_employee` VALUES (1, '张子接', 1, '总经理', 8);
INSERT INTO `adm_employee` VALUES (2, '卡萨丁', 2, '部门经理', 7);
INSERT INTO `adm_employee` VALUES (3, '埃里克', 2, '高级研发工程师', 6);
INSERT INTO `adm_employee` VALUES (4, '龙口东', 2, '研发工程师', 5);
INSERT INTO `adm_employee` VALUES (5, '衡都', 2, '初级研发工程师', 4);
INSERT INTO `adm_employee` VALUES (6, '赵世龙', 3, '部门经理', 7);
INSERT INTO `adm_employee` VALUES (7, '阿科快', 3, '大客户经理', 6);
INSERT INTO `adm_employee` VALUES (8, '合架', 3, '客户经理', 5);
INSERT INTO `adm_employee` VALUES (9, '安康码', 3, '小客户经理', 4);
INSERT INTO `adm_employee` VALUES (10, '葛二恩', 3, '见习客户经理', 3);

-- ----------------------------
-- Table structure for adm_leave_form
-- ----------------------------
DROP TABLE IF EXISTS `adm_leave_form`;
CREATE TABLE `adm_leave_form`  (
  `form_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '请假单编号',
  `employee_id` bigint(20) NOT NULL COMMENT '员工编号',
  `form_type` int(11) NOT NULL COMMENT '请假类型 1-事假 2-病假 3-工伤假 4-婚假 5-产假 6-丧假',
  `start_time` datetime NOT NULL COMMENT '请假起始时间',
  `end_time` datetime NOT NULL COMMENT '请假结束时间',
  `reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请假事由',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'processing-正在审批 approved-审批已通过 -refused-审批被驳回',
  PRIMARY KEY (`form_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请假申请' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of adm_leave_form
-- ----------------------------
INSERT INTO `adm_leave_form` VALUES (31, 4, 3, '2020-03-29 00:00:00', '2020-04-04 00:00:00', '事故,小腿骨折,明天做手术', '2020-03-28 11:49:09', 'processing');
INSERT INTO `adm_leave_form` VALUES (32, 3, 1, '2020-03-29 00:00:00', '2020-04-04 00:00:00', '没啥原因,单纯想休息几天', '2020-03-28 11:50:35', 'processing');
INSERT INTO `adm_leave_form` VALUES (33, 6, 1, '2020-03-30 00:00:00', '2020-03-31 00:00:00', '带孩子去看牙', '2020-03-28 11:51:49', 'processing');
INSERT INTO `adm_leave_form` VALUES (39, 10, 1, '2021-11-30 00:00:00', '2021-12-30 00:00:00', '骨折', '2021-11-30 15:54:47', 'processing');
INSERT INTO `adm_leave_form` VALUES (40, 2, 1, '2021-12-15 12:00:00', '2021-12-17 12:00:00', '不开', '2021-12-17 12:35:22', 'approved');
INSERT INTO `adm_leave_form` VALUES (41, 1, 1, '2021-12-15 12:00:00', '2021-12-17 12:00:00', '我休息不好', '2021-12-19 18:13:59', 'approve');
INSERT INTO `adm_leave_form` VALUES (42, 1, 1, '2021-12-15 12:00:00', '2021-12-17 12:00:00', '我休息不好', '2021-12-19 20:59:36', 'approve');

-- ----------------------------
-- Table structure for adm_process_flow
-- ----------------------------
DROP TABLE IF EXISTS `adm_process_flow`;
CREATE TABLE `adm_process_flow`  (
  `process_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '处理任务编号',
  `form_id` bigint(20) NOT NULL COMMENT '表单编号',
  `operator_id` bigint(20) NOT NULL COMMENT '经办人编号',
  `action` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'apply-申请 audit-审批',
  `result` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'approved-同意 refused-驳回',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `order_no` int(11) NOT NULL COMMENT '任务序号',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审批时间',
  `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ready-准备 process-正在处理 complete-处理完成 cancel-取消',
  `is_last` int(11) NOT NULL COMMENT '是否最后节点 0-否 1-是 ',
  PRIMARY KEY (`process_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of adm_process_flow
-- ----------------------------
INSERT INTO `adm_process_flow` VALUES (74, 31, 4, 'apply', NULL, NULL, '2020-03-28 11:49:09', 1, NULL, 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (75, 31, 2, 'audit', 'approved', '同意', '2020-03-28 11:49:09', 2, '2021-11-30 15:51:59', 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (76, 31, 1, 'audit', NULL, NULL, '2020-03-28 11:49:09', 3, NULL, 'process', 1);
INSERT INTO `adm_process_flow` VALUES (77, 32, 3, 'apply', NULL, NULL, '2020-03-28 11:50:35', 1, NULL, 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (78, 32, 2, 'audit', 'approved', '同意', '2020-03-28 11:50:35', 2, '2021-12-01 09:49:12', 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (79, 32, 1, 'audit', NULL, NULL, '2020-03-28 11:50:36', 3, NULL, 'process', 1);
INSERT INTO `adm_process_flow` VALUES (80, 33, 6, 'apply', NULL, NULL, '2020-03-28 11:51:49', 1, NULL, 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (81, 33, 1, 'audit', NULL, NULL, '2020-03-28 11:51:49', 2, NULL, 'process', 1);
INSERT INTO `adm_process_flow` VALUES (93, 39, 10, 'apply', NULL, NULL, '2021-11-30 15:54:48', 1, NULL, 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (94, 39, 6, 'audit', NULL, NULL, '2021-11-30 15:54:48', 2, NULL, 'process', 0);
INSERT INTO `adm_process_flow` VALUES (95, 39, 1, 'audit', NULL, NULL, '2021-11-30 15:54:48', 3, NULL, 'ready', 1);
INSERT INTO `adm_process_flow` VALUES (96, 40, 2, 'apply', NULL, NULL, '2021-12-17 12:35:28', 1, NULL, 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (97, 40, 1, 'audit', 'approved', 'ok', '2021-12-17 12:35:28', 2, '2021-12-17 12:42:15', 'complete', 1);
INSERT INTO `adm_process_flow` VALUES (98, 41, 1, 'apply', NULL, NULL, '2021-12-19 18:13:59', 1, NULL, 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (99, 41, 1, 'audit', 'approved', '自动通过', '2021-12-19 18:13:59', 2, '2021-12-19 18:13:59', 'complete', 1);
INSERT INTO `adm_process_flow` VALUES (100, 42, 1, 'apply', NULL, NULL, '2021-12-19 20:59:36', 1, NULL, 'complete', 0);
INSERT INTO `adm_process_flow` VALUES (101, 42, 1, 'audit', 'approved', '自动通过', '2021-12-19 20:59:36', 2, '2021-12-19 20:59:36', 'complete', 1);

-- ----------------------------
-- Table structure for sys_node
-- ----------------------------
DROP TABLE IF EXISTS `sys_node`;
CREATE TABLE `sys_node`  (
  `node_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '节点编号',
  `node_type` int(11) NOT NULL COMMENT '节点类型 1模块  2功能\r\n\r\n节点类型 1模块  2功能\r\n\r\n节点类型 1模块  2功能\r\n\r\n节点类型 1模块  2功能\r\n\r\n节点功能 1 -模块 2-功能',
  `node_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能地址',
  `node_code` int(11) NOT NULL COMMENT '节点编码，用于排序',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父节点',
  PRIMARY KEY (`node_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_node
-- ----------------------------
INSERT INTO `sys_node` VALUES (1, 1, '行政审批', ' ', 1000000, NULL);
INSERT INTO `sys_node` VALUES (2, 2, '通知广告', '/forward/notice', 1000001, 1);
INSERT INTO `sys_node` VALUES (3, 2, '请假申请', '/forward/form', 1000002, 1);
INSERT INTO `sys_node` VALUES (4, 2, '请假审批', '/forward/audit', 1000003, 1);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知编号',
  `receiver_id` bigint(20) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (2, 4, '您的请假申请[2021-09-04-00时-2021-09-05-00时]已提交，请等待上级审批', '2021-09-04 16:37:18');
INSERT INTO `sys_notice` VALUES (3, 2, '研发工程师-龙口东提起请假申请[2021-09-04-00时-2021-09-05-00时]，请尽快审批', '2021-09-04 16:37:18');
INSERT INTO `sys_notice` VALUES (4, 4, '您的请假申请[2021-09-04-00时-2021-09-05-00时]部门经理卡萨丁已null,审批意见：同意,审批流程结束', '2021-09-04 16:37:59');
INSERT INTO `sys_notice` VALUES (5, 2, '研发工程师龙口东提起请假申请[2021-09-04-00时-2021-09-05-00时]您已null,审批意见：同意,审批流程结束', '2021-09-04 16:37:59');
INSERT INTO `sys_notice` VALUES (6, 4, '您的请假申请[2020-03-29-00时-2020-04-04-00时]部门经理卡萨丁已批准,审批意见：提,请等待总经理审批。', '2021-09-04 16:38:39');
INSERT INTO `sys_notice` VALUES (7, 2, '研发工程师龙口东提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,审批意见：提,以提交总经理审批。', '2021-09-04 16:38:39');
INSERT INTO `sys_notice` VALUES (8, 1, '研发工程师龙口东提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,请尽快审批。', '2021-09-04 16:38:39');
INSERT INTO `sys_notice` VALUES (9, 3, '您的请假申请[2020-03-29-00时-2020-04-04-00时]部门经理卡萨丁已批准,审批意见：安慰人,请等待总经理审批。', '2021-09-04 16:38:47');
INSERT INTO `sys_notice` VALUES (10, 2, '高级研发工程师埃里克提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,审批意见：安慰人,以提交总经理审批。', '2021-09-04 16:38:47');
INSERT INTO `sys_notice` VALUES (11, 1, '高级研发工程师埃里克提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,请尽快审批。', '2021-09-04 16:38:47');
INSERT INTO `sys_notice` VALUES (12, 3, '您的请假申请[2021-09-04-00时-2021-09-05-00时]已提交，请等待上级审批', '2021-09-04 16:39:51');
INSERT INTO `sys_notice` VALUES (13, 2, '高级研发工程师-埃里克提起请假申请[2021-09-04-00时-2021-09-05-00时]，请尽快审批', '2021-09-04 16:39:51');
INSERT INTO `sys_notice` VALUES (14, 3, '您的请假申请[2021-09-04-00时-2021-09-05-00时]部门经理卡萨丁已null,审批意见：ty,审批流程结束', '2021-09-04 16:40:32');
INSERT INTO `sys_notice` VALUES (15, 2, '高级研发工程师埃里克提起请假申请[2021-09-04-00时-2021-09-05-00时]您已null,审批意见：ty,审批流程结束', '2021-09-04 16:40:32');
INSERT INTO `sys_notice` VALUES (16, 4, '您的请假申请[2020-03-29-00时-2020-04-04-00时]总经理张子接已null,审批意见：,审批流程结束', '2021-09-04 16:40:57');
INSERT INTO `sys_notice` VALUES (17, 1, '研发工程师龙口东提起请假申请[2020-03-29-00时-2020-04-04-00时]您已null,审批意见：,审批流程结束', '2021-09-04 16:40:57');
INSERT INTO `sys_notice` VALUES (18, 3, '您的请假申请[2020-03-29-00时-2020-04-04-00时]总经理张子接已null,审批意见：rf,审批流程结束', '2021-09-04 16:41:06');
INSERT INTO `sys_notice` VALUES (19, 1, '高级研发工程师埃里克提起请假申请[2020-03-29-00时-2020-04-04-00时]您已null,审批意见：rf,审批流程结束', '2021-09-04 16:41:06');
INSERT INTO `sys_notice` VALUES (20, 6, '您的请假申请[2020-03-30-00时-2020-03-31-00时]总经理张子接已null,审批意见：f,审批流程结束', '2021-09-04 16:41:11');
INSERT INTO `sys_notice` VALUES (21, 1, '部门经理赵世龙提起请假申请[2020-03-30-00时-2020-03-31-00时]您已null,审批意见：f,审批流程结束', '2021-09-04 16:41:11');
INSERT INTO `sys_notice` VALUES (22, 4, '您的请假申请[2021-09-04-00时-2021-09-05-00时]已提交，请等待上级审批', '2021-09-04 16:46:36');
INSERT INTO `sys_notice` VALUES (23, 2, '研发工程师-龙口东提起请假申请[2021-09-04-00时-2021-09-05-00时]，请尽快审批', '2021-09-04 16:46:36');
INSERT INTO `sys_notice` VALUES (24, 4, '您的请假申请[2021-09-04-00时-2021-09-05-00时]部门经理卡萨丁已批准,审批意见：e,审批流程结束', '2021-09-04 16:46:57');
INSERT INTO `sys_notice` VALUES (25, 2, '研发工程师龙口东提起请假申请[2021-09-04-00时-2021-09-05-00时]您已批准,审批意见：e,审批流程结束', '2021-09-04 16:46:57');
INSERT INTO `sys_notice` VALUES (26, 3, '您的请假申请[2021-09-04-00时-2021-10-05-00时]已提交，请等待上级审批', '2021-09-04 16:48:21');
INSERT INTO `sys_notice` VALUES (27, 2, '高级研发工程师-埃里克提起请假申请[2021-09-04-00时-2021-10-05-00时]，请尽快审批', '2021-09-04 16:48:21');
INSERT INTO `sys_notice` VALUES (28, 3, '您的请假申请[2021-09-04-00时-2021-10-05-00时]部门经理卡萨丁已批准,审批意见：同意,请等待总经理审批。', '2021-09-04 16:48:40');
INSERT INTO `sys_notice` VALUES (29, 2, '高级研发工程师埃里克提起的请假申请[2021-09-04-00时-2021-10-05-00时]您已批准,审批意见：同意,以提交总经理审批。', '2021-09-04 16:48:40');
INSERT INTO `sys_notice` VALUES (30, 1, '高级研发工程师埃里克提起的请假申请[2021-09-04-00时-2021-10-05-00时]您已批准,请尽快审批。', '2021-09-04 16:48:40');
INSERT INTO `sys_notice` VALUES (31, 3, '您的请假申请[2021-09-04-00时-2021-10-05-00时]总经理张子接已批准,审批意见：tttt,审批流程结束', '2021-09-04 16:48:58');
INSERT INTO `sys_notice` VALUES (32, 1, '高级研发工程师埃里克提起请假申请[2021-09-04-00时-2021-10-05-00时]您已批准,审批意见：tttt,审批流程结束', '2021-09-04 16:48:58');
INSERT INTO `sys_notice` VALUES (33, 4, '您的请假申请[2020-03-29-00时-2020-04-04-00时]部门经理卡萨丁已批准,审批意见：同意,请等待总经理审批。', '2021-11-30 15:51:59');
INSERT INTO `sys_notice` VALUES (34, 2, '研发工程师龙口东提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,审批意见：同意,以提交总经理审批。', '2021-11-30 15:51:59');
INSERT INTO `sys_notice` VALUES (35, 1, '研发工程师龙口东提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,请尽快审批。', '2021-11-30 15:51:59');
INSERT INTO `sys_notice` VALUES (36, 10, '您的请假申请[2021-11-30-00时-2021-12-30-00时]已提交，请等待上级审批', '2021-11-30 15:54:48');
INSERT INTO `sys_notice` VALUES (37, 6, '见习客户经理-葛二恩提起请假申请[2021-11-30-00时-2021-12-30-00时]，请尽快审批', '2021-11-30 15:54:48');
INSERT INTO `sys_notice` VALUES (38, 3, '您的请假申请[2020-03-29-00时-2020-04-04-00时]部门经理卡萨丁已批准,审批意见：同意,请等待总经理审批。', '2021-12-01 09:49:12');
INSERT INTO `sys_notice` VALUES (39, 2, '高级研发工程师埃里克提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,审批意见：同意,以提交总经理审批。', '2021-12-01 09:49:12');
INSERT INTO `sys_notice` VALUES (40, 1, '高级研发工程师埃里克提起的请假申请[2020-03-29-00时-2020-04-04-00时]您已批准,请尽快审批。', '2021-12-01 09:49:12');
INSERT INTO `sys_notice` VALUES (41, 2, '您的请假申请[2021-12-15-12时-2021-12-17-12时]已提交，请等待上级审批', '2021-12-17 12:35:28');
INSERT INTO `sys_notice` VALUES (42, 1, '部门经理-卡萨丁提起请假申请[2021-12-15-12时-2021-12-17-12时]，请尽快审批', '2021-12-17 12:35:28');
INSERT INTO `sys_notice` VALUES (43, 2, '您的请假申请[2021-12-15-12时-2021-12-17-12时]总经理张子接已批准,审批意见：ok,审批流程结束', '2021-12-17 12:42:15');
INSERT INTO `sys_notice` VALUES (44, 1, '部门经理卡萨丁提起请假申请[2021-12-15-12时-2021-12-17-12时]您已批准,审批意见：ok,审批流程结束', '2021-12-17 12:42:15');
INSERT INTO `sys_notice` VALUES (45, 1, '您的请假申请[2021-12-15-12时-2021-12-17-12时]系统已自动通过.', '2021-12-19 18:13:59');
INSERT INTO `sys_notice` VALUES (46, 1, '您的请假申请[2021-12-15-12时-2021-12-17-12时]系统已自动通过.', '2021-12-19 20:59:36');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_description` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '业务岗角色');
INSERT INTO `sys_role` VALUES (2, '管理岗角色');

-- ----------------------------
-- Table structure for sys_role_node
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_node`;
CREATE TABLE `sys_role_node`  (
  `rn_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '节点-角色关系id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `node_id` bigint(20) NOT NULL COMMENT '节点id',
  PRIMARY KEY (`rn_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_node
-- ----------------------------
INSERT INTO `sys_role_node` VALUES (1, 1, 1);
INSERT INTO `sys_role_node` VALUES (2, 1, 2);
INSERT INTO `sys_role_node` VALUES (3, 1, 3);
INSERT INTO `sys_role_node` VALUES (4, 2, 1);
INSERT INTO `sys_role_node` VALUES (5, 2, 2);
INSERT INTO `sys_role_node` VALUES (6, 2, 3);
INSERT INTO `sys_role_node` VALUES (7, 2, 4);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `ru_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色用户关系id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`ru_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 2, 1);
INSERT INTO `sys_role_user` VALUES (2, 2, 2);
INSERT INTO `sys_role_user` VALUES (3, 1, 3);
INSERT INTO `sys_role_user` VALUES (4, 1, 4);
INSERT INTO `sys_role_user` VALUES (5, 1, 5);
INSERT INTO `sys_role_user` VALUES (6, 2, 6);
INSERT INTO `sys_role_user` VALUES (7, 1, 7);
INSERT INTO `sys_role_user` VALUES (8, 1, 8);
INSERT INTO `sys_role_user` VALUES (9, 1, 9);
INSERT INTO `sys_role_user` VALUES (10, 1, 10);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `employee_id` bigint(20) NOT NULL COMMENT '员工id',
  `salt` int(11) NOT NULL COMMENT '盐值MD5',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'm8', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 1, 188);
INSERT INTO `sys_user` VALUES (2, 't7', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 2, 188);
INSERT INTO `sys_user` VALUES (3, 't6', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 3, 188);
INSERT INTO `sys_user` VALUES (4, 't5', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 4, 188);
INSERT INTO `sys_user` VALUES (5, 't4', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 5, 188);
INSERT INTO `sys_user` VALUES (6, 's7', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 6, 188);
INSERT INTO `sys_user` VALUES (7, 's6', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 7, 188);
INSERT INTO `sys_user` VALUES (8, 's5', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 8, 188);
INSERT INTO `sys_user` VALUES (9, 's4', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 9, 188);
INSERT INTO `sys_user` VALUES (10, 's3', 'f57e762e3fb7e1e3ec8ec4db6a1248e1', 10, 188);

SET FOREIGN_KEY_CHECKS = 1;
