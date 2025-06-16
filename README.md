# springboot3.x + jdk17 + vue3 示例 #

## 1.工程介绍

> springboot-jakarta-example
> |-- springboot-jakarta-common
> |-- springboot-jakarta-example-demo
> |-- springboot-jakarta-jwt-demo
> |-- vue-admin-system-demo
> |-- pom.xml

**`springboot-jakarta-example`**  为project，主要用于jar版本定义等；

**`springboot-jakarta-common`** 为module，主要提供一些工具类等；

**`springboot-jakarta-example-demo`**为module，采用传统 `springmvc` + `thymeleaf` 整合的示例工程，包含登录、权限管理等。

- 权限管理包含：
  - 资源管理，菜单的新增、编辑、删除功能；
  - 角色管理，角色的新增、编辑、删除，以及角色授权菜单权限等功能；
  - 用户管理，用户的新增、编辑、删除，以及重置密码等功能；

**`springboot-jakarta-jwt-demo`**为module，采用传统 `springmvc`  定义的接口服务，包含登录、权限管理等相关的接口示例；

**`vue-admin-system-demo`**为 `vue3` 前端，包含登录、权限管理等示例；

- 权限管理包含：
  - 资源管理，菜单的新增、编辑、删除功能；
  - 角色管理，角色的新增、编辑、删除，以及角色授权菜单权限等功能；
  - 用户管理，用户的新增、编辑、删除，以及重置密码、Excel导入，Excel导出等功能；



