CREATE TABLE `S` (
                     `SNO` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '学号',
                     `SNAME` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '姓名'
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='学生关系';

CREATE TABLE `C` (
                     `CNO` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '课程号',
                     `CNAME` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '课程名',
                     `CTEACHER` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '老师'
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='课程关系';

CREATE TABLE `SC` (
                      `SNO` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '学号',
                      `CNO` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT '课程号',
                      `SCORE` bigint(100) NOT NULL DEFAULT '0' COMMENT '成绩'
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='选课关系';

INSERT INTO `SC` (`SNO`, `CNO`, `SCORE`)  VALUES   ( '2', '11', 120);
INSERT INTO `S` (`SNO`, `SNAME`)  VALUES  ('2', '小易');
INSERT INTO `C` (`CNO`, `CNAME`, `CTEACHER`)  VALUES  ('11', '小易', '物理老师');

-- 找出没有选择小易老师的学生姓名
-- 列出有三门课程成绩大于90的学生姓名及课程平均分

select com.SNAME from (select S.SNAME,SC.CNO from S left join SC on S.SNO = SC.SNO ) as com
                          left join C on com.CNO=C.CNO;