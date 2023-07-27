/*
 Navicat MySQL Data Transfer

 Source Server         : 个人-本机-127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : tiny-job

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 27/07/2023 18:20:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('clusteredScheduler', 'Trigger_4', 'DEFALUT', '0/9 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('clusteredScheduler', 'Trigger_5', 'DEFALUT', '0/12 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', 'Job_4', 'DEFALUT', NULL, 'org.tinycloud.tinyjob.utils.quartz.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000E4A4F425F50524F504552544945537372002A6F72672E74696E79636C6F75642E74696E796A6F622E6265616E2E656E746974792E544A6F62496E666F000000000000000102001749000764656C466C61674C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C00096372656174656441747400104C6A6176612F7574696C2F446174653B4C000963726561746564427971007E00094C000E63726F6E45787072657373696F6E71007E00094C0006686F737449647400104C6A6176612F6C616E672F4C6F6E673B4C0002696471007E000B4C000F696E74657276616C5365636F6E64737400134C6A6176612F6C616E672F496E74656765723B4C00086A6F6247726F757071007E00094C00096A6F6248656164657271007E00094C00076A6F624E616D6571007E00094C00086A6F62506172616D71007E00094C000A6A6F625472696767657271007E00094C00076A6F625479706571007E00094C00066A6F6255726C71007E00094C000D6D697366697265506F6C69637971007E00094C000F6E6578744578656375746554696D6571007E000A4C000970726F6A656374496471007E000B4C000672656D61726B71007E00094C000673746174757371007E00094C0008737472617465677971007E00094C000975706461746564417471007E000A4C000975706461746564427971007E0009787000000000740001317372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000188D24890987874000561646D696E74000D302F39202A202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000017371007E0013000000000000000470740007444546414C555474000074000CE6B58BE8AF95E4BBBBE58AA17400497B227265736F757263655F6964223A2236303036222C22666F726D6174223A226A736F6E222C227175657279223A223130312E34332E392E323531222C226F65223A2275746638227D74000443524F4E7400034745547400082F6170692E706870740001337371007E000F770800000189870AA4A87871007E0015740016E4B8A5E6A0BCEFBC9AE6AF8F39E7A792E4B880E6ACA174000131740005524F554E447371007E000F770800000189871A63E87874000561646D696E7800);
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', 'Job_5', 'DEFALUT', NULL, 'org.tinycloud.tinyjob.utils.quartz.QuartzJobExecution', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000E4A4F425F50524F504552544945537372002A6F72672E74696E79636C6F75642E74696E796A6F622E6265616E2E656E746974792E544A6F62496E666F000000000000000102001549000764656C466C61674C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C00096372656174656441747400104C6A6176612F7574696C2F446174653B4C000963726561746564427971007E00094C000E63726F6E45787072657373696F6E71007E00094C0006686F737449647400104C6A6176612F6C616E672F4C6F6E673B4C0002696471007E000B4C00086A6F6247726F757071007E00094C00096A6F6248656164657271007E00094C00076A6F624E616D6571007E00094C00086A6F62506172616D71007E00094C00076A6F625479706571007E00094C00066A6F6255726C71007E00094C000D6D697366697265506F6C69637971007E00094C000F6E6578744578656375746554696D6571007E000A4C000970726F6A656374496471007E000B4C000672656D61726B71007E00094C000673746174757371007E00094C0008737472617465677971007E00094C000975706461746564417471007E000A4C000975706461746564427971007E0009787000000000740001307372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000188DCF007107874000561646D696E74000E302F3132202A202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000027371007E00120000000000000005740007444546414C5554740000740015E6B58BE8AF95504F53545F4A534F4EE4BBBBE58AA1740000740009504F53545F4A534F4E74000E2F746573742F706F73746A736F6E740001337371007E000E770800000188DCF231C0787371007E00120000000000000001740004585858587400013174000652414E444F4D7371007E000E770800000188DCF202E07874000561646D696E7800);
INSERT INTO `qrtz_job_details` VALUES ('clusteredScheduler', 'Job_6', 'COMMON', NULL, 'org.tinycloud.tinyjob.utils.quartz.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000E4A4F425F50524F504552544945537372002A6F72672E74696E79636C6F75642E74696E796A6F622E6265616E2E656E746974792E544A6F62496E666F000000000000000102001749000764656C466C61674C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C00096372656174656441747400104C6A6176612F7574696C2F446174653B4C000963726561746564427971007E00094C000E63726F6E45787072657373696F6E71007E00094C0006686F737449647400104C6A6176612F6C616E672F4C6F6E673B4C0002696471007E000B4C000F696E74657276616C5365636F6E64737400134C6A6176612F6C616E672F496E74656765723B4C00086A6F6247726F757071007E00094C00096A6F6248656164657271007E00094C00076A6F624E616D6571007E00094C00086A6F62506172616D71007E00094C000A6A6F625472696767657271007E00094C00076A6F625479706571007E00094C00066A6F6255726C71007E00094C000D6D697366697265506F6C69637971007E00094C000F6E6578744578656375746554696D6571007E000A4C000970726F6A656374496471007E000B4C000672656D61726B71007E00094C000673746174757371007E00094C0008737472617465677971007E00094C000975706461746564417471007E000A4C000975706461746564427971007E0009787000000000740001317372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000189775E8B007874000561646D696E707372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000037371007E00120000000000000006737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000B740006434F4D4D4F4E740000740018E6898BE69CBAE58FB7E5BD92E5B19EE59CB0E69FA5E8AFA27400187B226D6F62696C65223A223137383632373139353932227D74000653494D504C457400034745547400152F6170692F636F6D6D6F6E2F74656C616472657373740001337371007E000F77080000018977994240787371007E001200000000000000027400007400013174000546495253547371007E000F770800000189871A83287874000561646D696E7800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('clusteredScheduler', 'liuxingyu011690448952529', 1690449044212, 10000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
INSERT INTO `qrtz_simple_triggers` VALUES ('clusteredScheduler', 'Trigger_6', 'COMMON', -1, 11000, 305);

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', 'Trigger_4', 'DEFALUT', 'Job_4', 'DEFALUT', NULL, 1690192167000, 1690192158000, 5, 'PAUSED', 'CRON', 1690189368000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', 'Trigger_5', 'DEFALUT', 'Job_5', 'DEFALUT', NULL, 1690192164000, 1690192152000, 5, 'PAUSED', 'CRON', 1689922268000, 0, NULL, 2, '');
INSERT INTO `qrtz_triggers` VALUES ('clusteredScheduler', 'Trigger_6', 'COMMON', 'Job_6', 'COMMON', NULL, 1690192164947, 1690192153947, 5, 'PAUSED', 'SIMPLE', 1690188809947, 0, NULL, 0, '');

-- ----------------------------
-- Table structure for t_auth_token
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_token`;
CREATE TABLE `t_auth_token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `token_str` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token',
  `login_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `token_expire_time` char(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `s_auth_token_unique_token_str`(`token_str`) USING BTREE COMMENT 'token_str不可重复'
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_auth_token
-- ----------------------------
INSERT INTO `t_auth_token` VALUES (34, '2023-07-27 11:24:37', '2023-07-27 11:36:41', 'rKTN2qCorha_l2qGvdzH3baPoxTdaUNi1JQ20QvXDG0a7GGnbea4XE13UmzzpQUwS4458G0__wsrpW5JviisHZJ6EVTZEHJkaEfJORWamkgJFdHToRvhPrldhr0i7Fd6', 'admin', '20230727120641');
INSERT INTO `t_auth_token` VALUES (35, '2023-07-27 16:07:34', '2023-07-27 16:19:48', 'eBVi5CVPa8cHvK3e743qLnvNvYIAwhYjfsxJ9Qj7h6ujFHPaVrfcrFtFYgtLRqHmRwWNXUK1p26JTxPaRHtYYzu8KuMEmPkNyTBTtzWNS13chOnl2hqwXGFN01y3yx8u', 'admin', '20230727164948');
INSERT INTO `t_auth_token` VALUES (36, '2023-07-27 17:08:14', '2023-07-27 17:10:24', 'lPCJgHmVo_EruCg9rgPh_ng5hjt5wmmys6YpwvHXG_9HXpM1__mg6z8Y4NhYFmnUKGBgNSQ90P3fHZfl99bcab7h4JszM1KhaPFHhkQJXVOcrt5Eg2pTNWHpxKuym3y5', 'admin', '20230727174024');

-- ----------------------------
-- Table structure for t_hosts_info
-- ----------------------------
DROP TABLE IF EXISTS `t_hosts_info`;
CREATE TABLE `t_hosts_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主机ID（主键ID）',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `host_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主机名称',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0--未删除1--已删除）',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'admin' COMMENT '创建者，对应t_user.username',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者，对应t_user.username',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '主机表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_hosts_info
-- ----------------------------
INSERT INTO `t_hosts_info` VALUES (1, 1, 'IP物理地址查询', 0, 'admin', 'admin', '2023-06-16 10:55:25', '2023-07-21 15:02:45', '');
INSERT INTO `t_hosts_info` VALUES (2, 1, 'fastmock', 0, 'admin', NULL, '2023-06-21 15:48:03', '2023-07-02 16:08:20', '');
INSERT INTO `t_hosts_info` VALUES (3, 2, '教书先生Api', 0, 'admin', 'admin', '2023-07-02 21:51:13', '2023-07-21 15:31:46', '');

-- ----------------------------
-- Table structure for t_hosts_item
-- ----------------------------
DROP TABLE IF EXISTS `t_hosts_item`;
CREATE TABLE `t_hosts_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主机列表ID（主键ID）',
  `host_id` bigint(20) NOT NULL COMMENT '主机id',
  `host_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '主机地址',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0--未删除1--已删除）',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'admin' COMMENT '创建者，对应t_user.username',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者，对应t_user.username',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '主机子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_hosts_item
-- ----------------------------
INSERT INTO `t_hosts_item` VALUES (3, 2, 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0', 0, 'admin', NULL, '2023-06-21 15:48:15', '2023-06-21 15:48:15', '');
INSERT INTO `t_hosts_item` VALUES (10, 1, 'https://opendata.baidu.com', 0, 'admin', NULL, '2023-07-21 15:02:45', '2023-07-21 15:02:45', NULL);
INSERT INTO `t_hosts_item` VALUES (11, 1, 'https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv', 0, 'admin', NULL, '2023-07-21 15:02:45', '2023-07-21 15:02:45', NULL);
INSERT INTO `t_hosts_item` VALUES (14, 3, 'https://api.oioweb.cn', 0, 'admin', NULL, '2023-07-27 11:36:39', '2023-07-27 11:36:39', NULL);

-- ----------------------------
-- Table structure for t_job_info
-- ----------------------------
DROP TABLE IF EXISTS `t_job_info`;
CREATE TABLE `t_job_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID（主键ID）',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `host_id` bigint(20) NOT NULL COMMENT '主机id',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `job_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式，GET,POST,POST_JSON',
  `job_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求地址',
  `job_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数，以json形式保存',
  `job_header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求头信息，以json形式保存',
  `strategy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由策略（FIRST、LAST、ROUND、RANDOM）',
  `job_trigger` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器类型（CRON、SIMPLE）',
  `cron_expression` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `interval_seconds` int(11) NULL DEFAULT NULL COMMENT '简单任务的重复间隔时间（以秒为单位）',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '调度过期策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `next_execute_time` datetime(0) NULL DEFAULT NULL COMMENT '下次执行时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0启动 1暂停）',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0--未删除1--已删除）',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'admin' COMMENT '创建者，对应t_user.username',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者，对应t_user.username',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_job_info
-- ----------------------------
INSERT INTO `t_job_info` VALUES (4, 1, 1, '测试任务', 'DEFALUT', 'GET', '/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', 'ROUND', 'CRON', '0/9 * * * * ?', NULL, '3', '1', '2023-07-24 17:49:27', '1', 0, 'admin', 'admin', '2023-06-19 14:12:31', '2023-07-24 17:49:22', '严格：每9秒一次');
INSERT INTO `t_job_info` VALUES (5, 1, 2, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', '/test/postjson', '', '', 'RANDOM', 'CRON', '0/12 * * * * ?', NULL, '3', '0', '2023-07-21 14:58:00', '1', 0, 'admin', 'admin', '2023-06-21 15:51:38', '2023-07-24 17:49:22', 'XXXX');
INSERT INTO `t_job_info` VALUES (6, 2, 3, '手机号归属地查询', 'COMMON', 'GET', '/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', 'FIRST', 'SIMPLE', NULL, 11, '3', '1', '2023-07-27 11:35:40', '1', 0, 'admin', 'admin', '2023-07-21 15:33:52', '2023-07-27 11:35:29', '');

-- ----------------------------
-- Table structure for t_job_log
-- ----------------------------
DROP TABLE IF EXISTS `t_job_log`;
CREATE TABLE `t_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID（主键ID）',
  `job_id` bigint(20) NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `job_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务类型',
  `job_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务url',
  `job_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务参数',
  `job_header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务请求头',
  `return_info` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0成功， 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `execute_at` datetime(3) NULL DEFAULT NULL COMMENT '执行时间',
  `end_at` datetime(3) NULL DEFAULT NULL COMMENT '结束时间',
  `consuming` int(11) NULL DEFAULT NULL COMMENT '任务耗时(单位毫秒)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 831 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_job_log
-- ----------------------------
INSERT INTO `t_job_log` VALUES (803, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:47:57', '2023-07-24 17:47:56.970', '2023-07-24 17:47:57.123', 153);
INSERT INTO `t_job_log` VALUES (804, 4, '测试任务', 'DEFALUT', 'GET', 'https://opendata.baidu.com/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:48:00', '2023-07-24 17:48:00.029', '2023-07-24 17:48:00.179', 150);
INSERT INTO `t_job_log` VALUES (805, 5, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0/test/postjson', '', '', '{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}', '0', NULL, '2023-07-24 17:48:00', '2023-07-24 17:48:00.041', '2023-07-24 17:48:00.238', 197);
INSERT INTO `t_job_log` VALUES (806, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:48:08', '2023-07-24 17:48:07.971', '2023-07-24 17:48:08.089', 118);
INSERT INTO `t_job_log` VALUES (807, 4, '测试任务', 'DEFALUT', 'GET', 'https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:48:09', '2023-07-24 17:48:09.027', '2023-07-24 17:48:09.161', 134);
INSERT INTO `t_job_log` VALUES (808, 5, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0/test/postjson', '', '', '{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}', '0', NULL, '2023-07-24 17:48:12', '2023-07-24 17:48:12.014', '2023-07-24 17:48:12.215', 201);
INSERT INTO `t_job_log` VALUES (809, 4, '测试任务', 'DEFALUT', 'GET', 'https://opendata.baidu.com/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:48:18', '2023-07-24 17:48:18.028', '2023-07-24 17:48:18.171', 143);
INSERT INTO `t_job_log` VALUES (810, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:48:19', '2023-07-24 17:48:18.972', '2023-07-24 17:48:19.101', 129);
INSERT INTO `t_job_log` VALUES (811, 5, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0/test/postjson', '', '', '{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}', '0', NULL, '2023-07-24 17:48:24', '2023-07-24 17:48:24.023', '2023-07-24 17:48:24.213', 190);
INSERT INTO `t_job_log` VALUES (812, 4, '测试任务', 'DEFALUT', 'GET', 'https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:48:27', '2023-07-24 17:48:27.031', '2023-07-24 17:48:27.177', 146);
INSERT INTO `t_job_log` VALUES (813, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:48:30', '2023-07-24 17:48:29.987', '2023-07-24 17:48:30.100', 113);
INSERT INTO `t_job_log` VALUES (814, 4, '测试任务', 'DEFALUT', 'GET', 'https://opendata.baidu.com/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:48:36', '2023-07-24 17:48:36.059', '2023-07-24 17:48:36.203', 144);
INSERT INTO `t_job_log` VALUES (815, 5, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0/test/postjson', '', '', '{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}', '0', NULL, '2023-07-24 17:48:36', '2023-07-24 17:48:36.073', '2023-07-24 17:48:36.283', 210);
INSERT INTO `t_job_log` VALUES (816, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:48:41', '2023-07-24 17:48:40.971', '2023-07-24 17:48:41.094', 123);
INSERT INTO `t_job_log` VALUES (817, 4, '测试任务', 'DEFALUT', 'GET', 'https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:48:45', '2023-07-24 17:48:45.032', '2023-07-24 17:48:45.167', 135);
INSERT INTO `t_job_log` VALUES (818, 5, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0/test/postjson', '', '', '{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}', '0', NULL, '2023-07-24 17:48:48', '2023-07-24 17:48:48.038', '2023-07-24 17:48:48.240', 202);
INSERT INTO `t_job_log` VALUES (819, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:48:52', '2023-07-24 17:48:51.978', '2023-07-24 17:48:52.123', 145);
INSERT INTO `t_job_log` VALUES (820, 4, '测试任务', 'DEFALUT', 'GET', 'https://opendata.baidu.com/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:48:54', '2023-07-24 17:48:54.033', '2023-07-24 17:48:54.180', 147);
INSERT INTO `t_job_log` VALUES (821, 4, '测试任务', 'DEFALUT', 'GET', 'https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:49:00', '2023-07-24 17:49:00.035', '2023-07-24 17:49:00.171', 136);
INSERT INTO `t_job_log` VALUES (822, 5, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0/test/postjson', '', '', '{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}', '0', NULL, '2023-07-24 17:49:00', '2023-07-24 17:49:00.047', '2023-07-24 17:49:00.245', 198);
INSERT INTO `t_job_log` VALUES (823, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:49:03', '2023-07-24 17:49:02.978', '2023-07-24 17:49:03.082', 104);
INSERT INTO `t_job_log` VALUES (824, 4, '测试任务', 'DEFALUT', 'GET', 'https://opendata.baidu.com/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:49:09', '2023-07-24 17:49:09.024', '2023-07-24 17:49:09.174', 150);
INSERT INTO `t_job_log` VALUES (825, 5, '测试POST_JSON任务', 'DEFALUT', 'POST_JSON', 'https://www.fastmock.site/mock/7b75b4d8f2befc6f6c698280fb791dd0/test/postjson', '', '', '{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"c.runoob.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}', '0', NULL, '2023-07-24 17:49:12', '2023-07-24 17:49:12.035', '2023-07-24 17:49:12.230', 195);
INSERT INTO `t_job_log` VALUES (826, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-24 17:49:14', '2023-07-24 17:49:13.984', '2023-07-24 17:49:14.137', 153);
INSERT INTO `t_job_log` VALUES (827, 4, '测试任务', 'DEFALUT', 'GET', 'https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php', '{\"resource_id\":\"6006\",\"format\":\"json\",\"query\":\"101.43.9.251\",\"oe\":\"utf8\"}', '', '{\"status\":\"0\",\"t\":\"\",\"set_cache_time\":\"\",\"data\":[{\"ExtendedLocation\":\"\",\"OriginQuery\":\"101.43.9.251\",\"appinfo\":\"\",\"disp_type\":0,\"fetchkey\":\"101.43.9.251\",\"location\":\"北京市海淀区\",\"origip\":\"101.43.9.251\",\"origipquery\":\"101.43.9.251\",\"resourceid\":\"6006\",\"role_id\":0,\"shareImage\":1,\"showLikeShare\":1,\"showlamp\":\"1\",\"titlecont\":\"IP地址查询\",\"tplt\":\"ip\"}]}', '0', NULL, '2023-07-24 17:49:18', '2023-07-24 17:49:18.026', '2023-07-24 17:49:18.162', 136);
INSERT INTO `t_job_log` VALUES (828, 6, '手机号归属地查询', 'COMMON', 'GET', 'https://api.oioweb.cn/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', '{\"code\":200,\"result\":{\"name\":\"移动178卡\",\"postCode\":\"264200\",\"prov\":\"山东\",\"num\":1786271,\"cityCode\":\"371000\",\"city\":\"威海市\",\"provCode\":\"370000\",\"areaCode\":\"0631\",\"type\":1},\"msg\":\"success\"}', '0', NULL, '2023-07-27 11:25:10', '2023-07-27 11:25:10.247', '2023-07-27 11:25:10.820', 573);
INSERT INTO `t_job_log` VALUES (829, 6, '手机号归属地查询', 'COMMON', 'GET', '/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', NULL, '1', 'java.net.UnknownHostException: api.oio333web.cn\r\n	at java.net.Inet6AddressImpl.lookupAllHostAddr(Native Method)\r\n	at java.net.InetAddress$2.lookupAllHostAddr(InetAddress.java:867)\r\n	at java.net.InetAddress.getAddressesFromNameService(InetAddress.java:1302)\r\n	at java.net.InetAddress$NameServiceAddresses.get(InetAddress.java:815)\r\n	at java.net.InetAddress.getAllByName0(InetAddress.java:1291)\r\n	at java.net.InetAddress.getAllByName(InetAddress.java:1144)\r\n	at java.net.InetAddress.getAllByName(InetAddress.java:1065)\r\n	at org.apache.http.impl.conn.SystemDefaultDnsResolver.resolve(SystemDefaultDnsResolver.java:45)\r\n	at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:112)\r\n	at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)\r\n	at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393)\r\n	at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)\r\n	at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)\r\n	at org.apache.http.impl.execchain.RetryExec.execute(RetryExec.java:89)\r\n	at org.apache.http.impl.execchain.RedirectExec.execute(RedirectExec.java:110)\r\n	at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)\r\n	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)\r\n	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:108)\r\n	at org.tinycloud.tinyjob.utils.http.HttpClientUtils.get(HttpClientUtils.java:100)\r\n	at org.tinycloud.tinyjob.utils.http.HttpStrategy.lambda$static$0(HttpStrategy.java:27)\r\n	at org.tinycloud.tinyjob.utils.http.HttpStrategy.getResult(HttpStrategy.java:42)\r\n	at org.tinycloud.tinyjob.utils.quartz.JobInvokeUtil.invoke(JobInvokeUtil.java:70)\r\n	at org.tinycloud.tinyjob.utils.quartz.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:22', '2023-07-27 11:32:15', '2023-07-27 11:32:15.722', '2023-07-27 11:32:15.791', 69);
INSERT INTO `t_job_log` VALUES (830, 6, '手机号归属地查询', 'COMMON', 'GET', '/api/common/teladress', '{\"mobile\":\"17862719592\"}', '', NULL, '1', 'java.net.UnknownHostException: api.oio333web.cn\r\n	at java.net.Inet6AddressImpl.lookupAllHostAddr(Native Method)\r\n	at java.net.InetAddress$2.lookupAllHostAddr(InetAddress.java:867)\r\n	at java.net.InetAddress.getAddressesFromNameService(InetAddress.java:1302)\r\n	at java.net.InetAddress$NameServiceAddresses.get(InetAddress.java:815)\r\n	at java.net.InetAddress.getAllByName0(InetAddress.java:1291)\r\n	at java.net.InetAddress.getAllByName(InetAddress.java:1144)\r\n	at java.net.InetAddress.getAllByName(InetAddress.java:1065)\r\n	at org.apache.http.impl.conn.SystemDefaultDnsResolver.resolve(SystemDefaultDnsResolver.java:45)\r\n	at org.apache.http.impl.conn.DefaultHttpClientConnectionOperator.connect(DefaultHttpClientConnectionOperator.java:112)\r\n	at org.apache.http.impl.conn.PoolingHttpClientConnectionManager.connect(PoolingHttpClientConnectionManager.java:376)\r\n	at org.apache.http.impl.execchain.MainClientExec.establishRoute(MainClientExec.java:393)\r\n	at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)\r\n	at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)\r\n	at org.apache.http.impl.execchain.RetryExec.execute(RetryExec.java:89)\r\n	at org.apache.http.impl.execchain.RedirectExec.execute(RedirectExec.java:110)\r\n	at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)\r\n	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)\r\n	at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:108)\r\n	at org.tinycloud.tinyjob.utils.http.HttpClientUtils.get(HttpClientUtils.java:100)\r\n	at org.tinycloud.tinyjob.utils.http.HttpStrategy.lambda$static$0(HttpStrategy.java:27)\r\n	at org.tinycloud.tinyjob.utils.http.HttpStrategy.getResult(HttpStrategy.java:42)\r\n	at org.tinycloud.tinyjob.utils.quartz.JobInvokeUtil.invoke(JobInvokeUtil.java:70)\r\n	at org.tinycloud.tinyjob.utils.quartz.QuartzDisallowConcurrentExecution.doExecute(QuartzDisallowConcurrentExecution.java:22', '2023-07-27 11:35:27', '2023-07-27 11:35:27.037', '2023-07-27 11:35:27.196', 159);

-- ----------------------------
-- Table structure for t_mail_config
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_config`;
CREATE TABLE `t_mail_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `smtp_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'smtp服务器地址',
  `smtp_port` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'smtp端口',
  `email_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱账号',
  `email_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱密码',
  `receive_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收件邮箱地址(多个用逗号隔开)',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0--未删除1--已删除）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注描述信息',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人-对应t_user.id',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人-对应t_user.id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统备份配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_mail_config
-- ----------------------------
INSERT INTO `t_mail_config` VALUES (2, 'smtp.111.com', '25', 'leisure@111.com', 'MuysavcjZwHXAShR', '184974699@qq.com', 0, NULL, '2023-07-27 17:10:24', '2023-07-27 17:10:24', 'admin', NULL);

-- ----------------------------
-- Table structure for t_project_info
-- ----------------------------
DROP TABLE IF EXISTS `t_project_info`;
CREATE TABLE `t_project_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目ID（主键ID）',
  `project_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '项目名称',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标志（0--未删除1--已删除）',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'admin' COMMENT '创建者，对应t_user.username',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者，对应t_user.username',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_project_info
-- ----------------------------
INSERT INTO `t_project_info` VALUES (1, '测试项目', 0, 'admin', 'admin', '2023-06-16 17:38:21', '2023-07-01 22:29:04', '');
INSERT INTO `t_project_info` VALUES (2, '正式项目', 0, 'admin', 'admin', '2023-07-01 22:29:32', '2023-07-01 22:33:16', '正式项目，非常正式');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键，内码',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称（中文姓名）',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态（0--正常 1--冻结）',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0--未删除1--已删除）',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$HGAry2cQdwZ9ABl1xJznket.mhcAQTzvkgEWajHdYYHhrKf1zbLBS', '系统管理员', NULL, NULL, '0', NULL, '0', '2023-06-13 11:21:11', '2023-06-13 11:24:14');

SET FOREIGN_KEY_CHECKS = 1;
