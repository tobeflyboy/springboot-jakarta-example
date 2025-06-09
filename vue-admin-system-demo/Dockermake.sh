#!/bin/bash
build_date=`date +'%Y-%m-%dT%H:%M:%S%z' | tr -d '\n'`
build_branch=`git branch | grep "^\*" | sed -e "s/^\*\ //"`
build_commit=`git rev-parse HEAD | tr -d '\n'`
build_version=`node -p "require('./package.json').version"`

docker build --no-cache \
  --build-arg BUILD_DATE=${build_date} \
  --build-arg BUILD_BRANCH=${build_branch} \
  --build-arg BUILD_COMMIT=${build_commit} \
  --build-arg BUILD_VERSION=${build_version} \
  -f Dockerfile -t your-company.harbor.com/admin-system/vue-admin-system-demo:${build_version} .
#docker push your-company.harbor.com/admin-system/vue-admin-system-demo:${build_version}
#docker rmi your-company.harbor.com/admin-system/vue-admin-system-demo:${build_version}
