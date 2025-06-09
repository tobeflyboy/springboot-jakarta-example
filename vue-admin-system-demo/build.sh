#!/bin/bash

# 判断传入的参数
if [ "$1" = "pro" ]; then
  echo "构建【生产环境】开始"
  rm -rf dist/
  #rm -rf node_modules
  #rm -rf package-lock.json
  echo "已清理历史构建产物"
  npm install
  npm run build:pro
  echo "构建【生产环境】结束"
elif [ "$1" = "test" ]; then
  echo "构建【测试环境】开始"
  rm -rf dist/
  #rm -rf node_modules
  #rm -rf package-lock.json
  echo "已清理历史构建产物"
  npm install
  npm run build:test
  echo "构建【测试环境】结束"
else
  # 参数错误提示
  echo "Usage: $0 [pro|test]"
  exit 1
fi
