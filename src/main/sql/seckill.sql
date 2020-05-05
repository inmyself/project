-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
    `seckill_id` BIGINT NOT NUll AUTO_INCREMENT COMMENT '商品库存ID',
    `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
    `number` int NOT NULL COMMENT '库存数量',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `start_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
    `end_time`   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
    PRIMARY KEY (seckill_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
INSERT INTO seckill(name, number, start_time, end_time)
VALUES
('1000元秒杀iPhone X', 100, '2020-04-13 00:00:00', '2020-05-13 00:00:00'),
('800元秒杀小米6', 100, '2020-04-13 00:00:00', '2020-12-13 12:00:00'),
('1000元秒杀华为 mate 30', 100, '2020-06-13 00:00:00', '2020-07-13 12:00:00'),
('600元秒杀荣耀 30', 100, '2020-02-13 00:00:00', '2020-03-13 12:00:00'),
('1200元秒杀iPad Air3', 100, '2019-12-13 00:00:00', '2019-12-13 12:00:00');

-- 秒杀成功明细表
-- 用户登录认真相关的信息(简化为手机号
CREATE TABLE success_killed(
                               `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品ID',
                               `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
                               `state` TINYINT NOT NULL DEFAULT 0 COMMENT '状态标识:-1:无效 0:成功 1:已付款 2:已发货',
                               `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               PRIMARY KEY(seckill_id,user_phone),/*联合主键*/
                               KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 用户表
CREATE TABLE user(
                               `user_id` BIGINT NOT NUll AUTO_INCREMENT COMMENT '用户id',
                               `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
                               `user_psw` VARCHAR(20) NOT NULL COMMENT '用户密码',
                               `user_ads` VARCHAR(250) NOT NULL COMMENT '用户地址',
                               PRIMARY KEY(user_id,user_phone),/*联合主键*/
                               KEY idx_user_phone(user_phone)
)ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户信息表';