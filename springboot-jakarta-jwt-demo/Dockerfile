# 镜像模板
FROM m.daocloud.io/docker.io/library/eclipse-temurin:17-jre-alpine

# 环境变量设置
ENV TZ=Asia/Shanghai \
    JAR=springboot-jakarta-jwt-demo.jar

# 定义Label接收的变量
ARG BUILD_DATE BUILD_BRANCH BUILD_COMMIT BUILD_VERSION

# 添加标签
# maintainer 作者
# build-date 构建日期
# build-branch 应用Git分支
# build-commit 应用Git提交日志ID
# build-version 应用当前版本
LABEL maintainer="胡桃夹子" \
      build-date=$BUILD_DATE \
      build-branch=$BUILD_BRANCH \
      build-commit=$BUILD_COMMIT \
      build-version=$BUILD_VERSION
# 构建镜像时，传递Label参数示例：
# docker build --no-cache \
#   --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') \
#   --build-arg BUILD_BRANCH=$(git branch --show-current) \
#   --build-arg BUILD_COMMIT=$(git rev-parse HEAD | tr -d '\n') \
#   --build-arg BUILD_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout) \
#   -f Dockerfile -t your-company.harbor.com/admin-system/springboot-jakarta-jwt-demo:0.0.1-SNAPSHOT .

# 指定默认目录
WORKDIR /opt/app

# 传包
COPY target/$JAR .
COPY src/main/resources/db/ ./db

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -DLANG=en_US.UTF-8 -Djava.security.egd=file:/dev/./urandom -jar $JAR"]
