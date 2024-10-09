# 使用 MySQL 5.7 基础镜像
FROM mysql:5.7

# 设置 MySQL 环境变量
ENV MYSQL_DATABASE=common \
    MYSQL_ROOT_PASSWORD=app@2024

# MySQL 启动时的自定义命令
CMD ["mysqld", \
    "--innodb-buffer-pool-size=80M", \
    "--character-set-server=utf8mb4", \
    "--collation-server=utf8mb4_unicode_ci", \
    "--default-time-zone=+8:00", \
    "--lower-case-table-names=1"]
