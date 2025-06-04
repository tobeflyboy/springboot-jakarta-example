FROM nginx:alpine

# 定义Label接收的变量
ARG BUILD_DATE BUILD_BRANCH BUILD_COMMIT

# 添加标签
# maintainer 作者
# build-date 构建日期
# build-branch 应用Git分支
# build-commit 应用Git提交日志ID
# build-version 应用当前版本
LABEL maintainer="胡桃夹子" \
      build-date=$BUILD_DATE \
      build-branch=$BUILD_BRANCH \
      build-commit=$BUILD_COMMIT
# 构建镜像时，传递Label参数示例：
# docker build --no-cache \
#   --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') \
#   --build-arg BUILD_BRANCH=$(git branch --show-current) \
#   --build-arg BUILD_COMMIT=$(git rev-parse HEAD | tr -d '\n') \
#   -f Dockerfile -t shharbor.centaline.com.cn/sh-uam/deep-pay-web:0.0.10-SNAPSHOT .

COPY nginx/nginx.conf /etc/nginx/nginx.conf

COPY dist /usr/share/nginx/html/
