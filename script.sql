-- persistent_logins: table
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- tb_book: table
CREATE TABLE `tb_book` (
  `book_id` int NOT NULL AUTO_INCREMENT COMMENT '书籍名称',
  `book_count` int DEFAULT NULL COMMENT '书籍剩余量',
  `book_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '书名',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- tb_book_user: table
CREATE TABLE `tb_book_user` (
  `username` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '学生姓名',
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '学生工号',
  `borrow_count` int DEFAULT NULL COMMENT '剩余可借阅数量',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='图书馆用户';

-- tb_borrow: table
CREATE TABLE `tb_borrow` (
  `borrow_id` int NOT NULL AUTO_INCREMENT COMMENT '借阅id',
  `book_id` tinyint DEFAULT NULL COMMENT '书籍id',
  `id` int DEFAULT NULL COMMENT '借阅人工号',
  PRIMARY KEY (`borrow_id`) USING BTREE,
  KEY `tb_borrow_tb_book_user_user_id_fk` (`id`),
  CONSTRAINT `tb_borrow_tb_book_user_user_id_fk` FOREIGN KEY (`id`) REFERENCES `tb_book_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='借阅表';

-- tb_sys: table
CREATE TABLE `tb_sys` (
  `id` int DEFAULT NULL COMMENT '序号',
  `config_name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置名',
  `config_key` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置键',
  `config_value` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置内容',
  `config_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='导入默认系统配置项';

-- tb_undo_log: table
CREATE TABLE `tb_undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE,
  KEY `ix_log_created` (`log_created`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='AT transaction mode undo table';

-- No native definition for element: ix_log_created (index)

-- tb_user: table
CREATE TABLE `tb_user` (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `user_status` smallint DEFAULT NULL COMMENT '[0:否,1:是]',
  `gender` smallint DEFAULT NULL COMMENT '[0:保密,1:男,2:女]',
  `open_id` int DEFAULT NULL COMMENT 'openId',
  `avatar` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `admire` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '赞赏',
  `subscribe` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订阅',
  `introduction` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '简介',
  `user_type` smallint DEFAULT NULL COMMENT '用户类型[0:admin,1:管理员,2:普通用户]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最终修改人',
  `deleted` smallint DEFAULT NULL COMMENT '是否启用：[0:未删除,1：已删除]',
  `phone_number` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1898180612 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';


explain
select emp.emp_id, emp.dep_id, format(mids.avg, 2) as '平均工资'
from employee emp
         right join (select avg(employee.salary) avg, employee.dep_id from employee group by employee.dep_id) mids
                    on mids.dep_id = emp.dep_id
where emp.salary < mids.avg
order by emp.dep_id;

-- 使用窗口函数进行优化操作
explain SELECT emp.emp_id,emp.name,
               emp.dep_id,
               FORMAT(avg_salary, 2) AS '平均工资'
        FROM
            (SELECT emp_id,
                    dep_id,
                    salary,
                    name,
                    AVG(salary) OVER (PARTITION BY dep_id) AS avg_salary
             FROM employee
            ) emp
        WHERE emp.salary < emp.avg_salary
        ORDER BY emp.dep_id ;

select EMP.*,AVG(emp.salary) OVER (PARTITION BY dep_id) from employee emp order by EMP.emp_id;

select emp.*, mid.avg
from employee emp
         join (select dep_id, avg(employee.salary) avg from employee group by employee.dep_id) mid
              on emp.dep_id = mid.dep_id
order by emp_id;

explain select emp.emp_id,emp.dep_id from employee emp;

-- 使用函数来获取确定精度的数字或者对数据进行转换：
-- 使用cast函数强转换，convert函数，format函数，前两个只接受字符串

explain select format(avg(employee.salary),2) avg, employee.dep_id from employee group by employee.dep_id;

-- convert/cast函数是用来转换字符变成指定数据类型的数据
select employee.dep_id from employee;

-- 拼接字符串concat，group_concat,string_add,

-- 普通查询，使用条件过滤
# select e1.*
             #  from employee e1
      #  where e1.salary < (select avg(salary) from employee e2 where e1.dep_id = e2.dep_id)
      #  order by dep_id;



