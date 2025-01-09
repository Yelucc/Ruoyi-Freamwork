-- 创建新用户
CREATE USER 'common'@'172.21.0.1' IDENTIFIED BY 'common-password';

-- 创建使用 utf8mb4 字符集的数据库
CREATE DATABASE common CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 授予新用户对新数据库的全部权限
GRANT ALL PRIVILEGES ON common.* TO 'common'@'172.21.0.1';