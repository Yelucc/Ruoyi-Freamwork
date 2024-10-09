# 基础镜像
FROM nginx:latest

# 挂载目录
VOLUME /home/app/projects/app-ui
# 创建目录
RUN mkdir -p /home/app/projects/app-ui
# 指定路径
WORKDIR /home/app/projects/app-ui
# 复制conf文件到路径
COPY ./conf/nginx.conf /etc/nginx/nginx.conf
# 复制html文件到路径
COPY ./html/dist /home/app/projects/app-ui
