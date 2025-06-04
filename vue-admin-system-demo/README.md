# VUE入门学习
## 1 vue概述

### 1.1 什么是vue

Vue (发音为 /vjuː/，类似 **view**) 是一款用于构建用户界面的 JavaScript 框架。

它基于标准 HTML、CSS 和 JavaScript 构建，并提供了一套声明式的、组件化的编程模型，帮助你高效地开发用户界面。

无论是简单还是复杂的界面，Vue 都可以胜任。

### 1.2 渐进式框架

Vue 是一个框架，也是一个生态。其功能覆盖了大部分前端开发常见的需求。但 Web 世界是十分多样化的，不同的开发者在 Web 上构建的东西可能在形式和规模上会有很大的不同。考虑到这一点，Vue 的设计非常注重灵活性和“可以被逐步集成”这个特点。根据你的需求场景，你可以用不同的方式使用 Vue：

- 无需构建步骤，渐进式增强静态的 HTML

- 在任何页面中作为 Web Components 嵌入

- 单页应用 (SPA)

- 全栈 / 服务端渲染 (SSR)

- Jamstack / 静态站点生成 (SSG)

- 开发桌面端、移动端、WebGL，甚至是命令行终端中的界面

***vue是渐进式JavaScript 框架，易学易用，性能出色，适用场景丰富的 Web 前端框架。***

### 1.3 MVVM思想

Vue.js 使用了前端中常用的分层思想 MVVM。
MVVM 分为M、V、VM

- M(Model) 也就是页面中单独数据；
- V(View)是页面中HTML 结构；
- VM(View-Model) 当V需要调用M中数据时，由VM做中间处理；

## 2 vue版本

目前开发中，Vue有两个大版本可以选择 `Vue2` 和 `Vue3`，老项目一般都是 `Vue2` 的，而新项目一般都选择`Vue3` 开发
这里以 `Vue3`版本介绍为主，因为 `Vue3` 涵盖了 `Vue2` 的知识体系，当然 `Vue3` 也增加了很多新特性。

### 1.2 名词解释

#### node.js
对 chrome V8 引擎进行了封装，使得 JavaScript 能够脱离浏览器环境，独立运行。同时能通过 Node.js 直接访问数据库的能力。
目前前端市场都是使用基于 Node.js 的框架。而不是直接使用 Node.js。
前后端分离之所以前端项目能够独立运行就是借助 Node.js.

#### npm
npm 是 Node.js 中的一个工具。通过 npm 可以实现一些组件的安装。效果和 Linux中的yum有类似。

使用 Node.js 时不是一下所有东西都能下载下来，有一些是第三方提供的，有些是插件，当需要使用这些东西的时候，通过 npm instal 进行安装即可。

#### webpack
是前端开发中的项目管理工具。和我们在开发Java 时 Maven 的作用类似。

#### vue-cli
Vue-cli 是 Vue.js 的客户端工具。通过 Vue-cli 可以实现 Vue 项目脚手架功能等，进行快速搭建。

#### cnpm
淘宝镜像提供的工具。解决了 npm 使用国外地址下载资源慢的问题。需要安装淘宝镜像后就可以使用了。
偶尔会出现工具无法连接淘宝服务器的情况。每次在使用时都是先通过 cnpm 命令查看是否能够连接淘宝镜像。才去使用。

## 2 环境安装

### 2.1 安装node.js

这里以vue3.x为例，先安装node环境，可以借助nvm插件来安装node，方便后期切换node版本。

nodejs必须 18.3 以上版本，这是vue官网的要求。

[nvm安装与学习](https://blog.csdn.net/wangxin_wangxin/article/details/132355048)

**nvm 安装 20.19.2 版本 nodesjs:**

```shell
# 下载安装20.19.2版本node
nvm install 20.19.2
# 切换至20.19.2版本node
nvm use 20.19.2
# 查看版本
node -v && npm -v
```

依次执行完上面的命令，最后能正确的出来版本号，则代表使用 nvm 安装 nodejs 成功。

### 2.2 安装npm淘宝镜像

国内使用 npm 官方镜像站下载资源会比较慢，可使用国内镜像提升下载速度。

推荐阿里 npm 镜像站

- Web 站点：[https://npmmirror.com](https://link.zhihu.com/?target=https%3A//npmmirror.com)
- Registry Endpoint：[https://registry.npmmirror.com](https://link.zhihu.com/?target=https%3A//registry.npmmirror.com)

随着阿里新的域名正式启用，老 [http://npm.taobao.org](https://link.zhihu.com/?target=http%3A//npm.taobao.org) 和 [http://registry.npm.taobao.org](https://link.zhihu.com/?target=http%3A//registry.npm.taobao.org) 域名于 2022 年 05 月 31 日停止服务了。

安装阿里镜像：

```shell
npm install cnpm -g --registry=https://registry.npmmirror.com
```

运行完成后通过 cnpm 命令查看是否安装成功，成功会提示下面信息。如果没有成功会提示没有 cnpm命令。

`目前cnpm支持的最小node版本为14.18.0，在node版本选择上要注意。`

> 以后所有官方文档中使用 `npm install` 的命令都换成 `cnpm install`
> 安装完成后，如果 IDEA 正在启动，需要关闭 IDEA，让 IDEA 加载 Node.js 相关信息。

### 2.3 安装webpack

其中 `-g` 表示全局安装，以后不需要在安装了。

如果没有 `-g` 表示只能在当前目录(运行命令时所在目录)中使用，如果重新建立项目，换了文件夹还需要重新安装。

**安装命令：**

```shell
cnpm install -g webpack webpack-cli
```

### 2.4 安装vue-cli

**安全命令：**

```shell
cnpm install -g @vue/cli
```

### 2.5 安装vue.js插件

在IDEA中
菜单 settings -> plugins -> 搜索 vue -> Vue.js 点击install-> 安装后重启idea

## 3 搭建vue项目

### 3.1 创建项目工程

在代码工作空间目录下，打开终端命令窗口，执行下面创建项目工程命令

```shell
npm create vue@latest
```

这一指令将会安装并执行 [create-vue](https://github.com/vuejs/create-vue)，它是 Vue 官方的项目脚手架工具。

首先会提示你输入工程名称，再往下将会看到一些诸如 TypeScript 和测试支持之类的可选功能提示：

> $ npm create vue@latest
>
> > npx
> > create-vue
>
> ┌  Vue.js - The Progressive JavaScript Framework
> │
> ◇  请输入项目名称：
> │  vue-admin-system
> │
> ◆  请选择要包含的功能： (↑/↓ 切换，空格选择，a 全选，回车确认)
> │  ◻ TypeScript
> │  ◻ JSX 支持
> │  ◼ Router（单页面应用开发）
> │  ◻ Pinia（状态管理）
> │  ◻ Vitest（单元测试）
> │  ◻ 端到端测试
> │  ◻ ESLint（错误预防）
> │  ◻ Prettier（代码格式化）

如果不确定是否要开启某个功能，你可以直接按下回车键选择 `No`。

这里只选择了`Router`

工程创建完之后，结构如下：

> **vue-admin-system**
>
> |-- README.md
>
> |-- index.html
>
> |-- jsconfig.json
>
> |-- node_modules
>
> |  |-- \*\*\*\*\*\*
>
> |-- package-lock.json
>
> |-- package.json
>
> |-- public
>
> |  -- favicon.ico
>
> |-- src
>
> |  |-- App.vue
>
> |  |-- assets
>
> |  |  |-- base.css
>
> |  |  |-- logo.svg
>
> |  |  -- main.css
>
> |  |-- components
>
> |  |  |-- HelloWorld.vue
>
> |  |  |-- TheWelcome.vue
>
> |  |  |-- WelcomeItem.vue
>
> |  |  -- icons
>
> |  |-- main.js
>
> |  |-- router
>
> |  |  -- index.js
>
> |  -- views
>
> |    |-- AboutView.vue
>
> |    -- HomeView.vue
>
> -- vite.config.js

在项目被创建后，通过以下步骤安装依赖并启动开发服务器：

```shell
cd vue-admin-system
npm install
npm run dev
```

上面的命令中，npm install 是安装插件，可以理解java中的下载jar一样。

npm run dev 则是运行工程，如果是以idea编辑器打开当前vue代码工具，也可以找到package.json，找到 "vue": "vite" 这一行，然后右键 Run "dev" 或 Debug "dev"

### 3.2 设置编码格式

这里是以idea编辑器来开发vue工程，主要是为了方便前后端代码工程一起开发。

当工程创建好之后，在idea中，给当前vue工程所在的工作空间，统一设置下文本编码格式为UTF-8。

> idea > File > Settings... > Editor > File Encodings



### 3.3 整理工程结构

默认创建出来的vue工程，当中一些文件用不上，这里进行删除掉

```shell
rm -f src/assets/base.css
rm -f src/assets/main.css
rm -rf src/components/*
rm -rf src/views/*
```

### 3.4 创建页面

在 src/views/ 下面，创建 Home.vue

```vue
<template>
  <div>
    this is home
  </div>
</template>
<script>
</script>
```

然后将 src/App.vue 更新下，只保留下面一段即可

```vue
<template>
  <RouterView />
</template>
```

修改路由 src/router/index.js

```typescript
import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/', name: 'home', component: Home,
        },
        {
            path: '/about', name: 'about', component: () => import('../views/About.vue'),
        },
    ],
})

export default router
```

在 `src/main.js` 或 `src/main.ts` 中引入 Element Plus 样式

```typescript
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(router)

app.mount('#app')
```





## 4 vue项目结构

结构如下：

> **vue-admin-system**
>
> |-- README.md
>
> |-- index.html
>
> |-- jsconfig.json
>
> |-- node_modules
>
> |  |-- \*\*\*\*\*\*
>
> |-- package-lock.json
>
> |-- package.json
>
> |-- public
>
> |  -- favicon.ico
>
> |-- src
>
> |  |-- App.vue
>
> |  |-- assets
>
> |  |  |-- base.css
>
> |  |  |-- logo.svg
>
> |  |  -- main.css
>
> |  |-- components
>
> |  |  |-- HelloWorld.vue
>
> |  |  |-- TheWelcome.vue
>
> |  |  |-- WelcomeItem.vue
>
> |  |  -- icons
>
> |  |-- main.js
>
> |  |-- router
>
> |  |  -- index.js
>
> |  -- views
>
> |    |-- AboutView.vue
>
> |    -- HomeView.vue
>
> -- vite.config.js

### 4.1 项目工程目录

1. node_modules 这个目录存放的是项目的所有依赖，即 npm install 命令下载下来的文件；
2. dist 这个目录是存放编译后的文件；
3. src 这个目录下存放项目的源码，即开发者写的代码放在这里；
4. index.html 则是项目的首页，入口页，也是整个项目唯一的HTML页面；
5. package.json 中定义了项目的所有依赖，包括开发时依赖和发布时依赖；

### 4.2 src目录

*（99.99% 的开发工作都是在 src 中）*

1. assets 目录用来存放资产文件；
2. components 目录用来存放组件（一些可复用，非独立的页面），当然开发者也可以在 components 中直接创建完整页面；
3. 推荐在 components 中存放组件，另外单独新建一个 page 文件夹，专门用来放完整页面；
4. router 目录中，存放了路由的js文件；
5. App.vue 是一个Vue组件，也是项目的第一个Vue组件；
6. main.js相当于Java中的main方法，是整个项目的入口js；

### 4.3 main.js

1. 在main.js 中，首先导入 Vue 对象；
2. 导入 App.vue ，并且命名为 App；
3. 导入router，注意，由于router目录下路由默认文件名为 index.js ，因此可以省略；
4. 所有东西都导入成功后，创建一个Vue对象，设置要被Vue处理的节点是 '#app'，'#app' 指提前在index.html 中定义的某个div；
5. 将 router 设置到 vue 对象中，这里是一个简化的写法，完整的写法是 router:router，如果 key/value 一模一样，则可以简写；
6. 声明一个组件 App，App 这个组件在一开始已经导入到项目中了，但是直接导入的组件无法直接使用，必须要声明；
7. template 中定义了页面模板，即将 App 组件中的内容渲染到 '#app' 这个div 中；



## 5 安装组件

这里介绍一下安装组件中的命令参数：

| **选项** |   **全称**   |   **写入位置**    |          **用途**          |
| :------: | :----------: | :---------------: | :------------------------: |
|   `-D`   | `--save-dev` | `devDependencies` | 开发环境专用（如构建工具） |
|   `-S`   |   `--save`   |  `dependencies`   |  生产环境必需（如核心库）  |

### Vue3 开发中 npm 简化命令对照表

| **命令功能**       | **完整命令**             | **简化命令**        | **作用说明**                                                 | **使用示例**        | **适用场景**                             |
| :----------------- | :----------------------- | :------------------ | :----------------------------------------------------------- | :------------------ | :--------------------------------------- |
| **安装开发依赖**   | `npm install --save-dev` | `npm i -D`          | 将包添加到 `devDependencies`（仅开发阶段需要，如 ESLint、Webpack） | `npm i -D eslint`   | 开发工具、测试库、构建工具               |
| **安装生产依赖**   | `npm install --save`     | `npm i -S`          | 将包添加到 `dependencies`（生产环境必需，如 Vue、Axios）     | `npm i -S vue`      | 核心库、运行时必需组件                   |
| **全局安装**       | `npm install --global`   | `npm i -g`          | 全局安装包（跨项目使用，如 Vue CLI）                         | `npm i -g @vue/cli` | 脚手架、命令行工具                       |
| **安装命令缩写**   | `npm install`            | `npm i`             | 安装当前项目所有依赖                                         | `npm i`             | 初始化项目或安装 `package.json` 中的依赖 |
| **运行开发服务器** | `npm run serve`          | `npm serve`¹        | 启动本地开发服务器（热更新支持）                             | `npm serve`         | 本地调试 Vue 项目                        |
| **生产环境构建**   | `npm run build`          | `npm build`¹        | 编译并压缩代码，生成 `dist/` 生产包                          | `npm build`         | 项目部署前优化                           |
| **初始化项目**     | `npm init`               | `npm init -y`       | 快速生成 `package.json`（跳过问答环节）                      | `npm init -y`       | 创建新项目时快速配置                     |
| **卸载依赖**       | `npm uninstall`          | `npm un` 或 `npm r` | 移除指定依赖包                                               | `npm un vuex`       | 清理无用依赖                             |
| **更新依赖**       | `npm update`             | `npm up`            | 更新包至最新兼容版本                                         | `npm up vue`        | 依赖版本升级                             |

> 注：`npm serve` 和 `npm build` 是 `npm run serve/build` 的简写形式，需在 `package.json` 的 `scripts` 中预定义对应命令

**依赖类型区分**

- **`-D` (开发依赖)**：如 `npm i -D typescript`，用于代码编译、格式化等开发阶段工具
- **`-S` (生产依赖)**：如 `npm i -S pinia`，项目运行时必需的核心库

| **选项** |   **全称**   |   **写入位置**    |          **用途**          |
| :------: | :----------: | :---------------: | :------------------------: |
|   `-D`   | `--save-dev` | `devDependencies` | 开发环境专用（如构建工具） |
|   `-S`   |   `--save`   |  `dependencies`   |  生产环境必需（如核心库）  |

**全局 vs 本地安装**

- 脚手架工具（如 `@vue/cli`）需全局安装（`-g`），而项目特定依赖（如 `vue-router`）应本地安装（不加 `-g`）

### 5.1 核心组件

#### 5.1.1 Element Plus 组件库

安装命令：

```shell
npm install element-plus -S
```

作用：提供丰富的 UI 组件（如表格、表单、弹窗等），是后台系统的视觉基础。

关键配置：需全局引入样式文件 element-plus/dist/index.css，并在入口文件（main.ts）中注册。

#### 5.1.2 Element Plus 图标库

安装命令：

```shell
npm install @element-plus/icons-vue
```

作用：独立图标组件（如 ElIcon），需单独安装并在入口文件全局注册。

### 5.2 自动按需加载工具

#### 5.2.1 unplugin-vue-components

安装命令：

```shell
npm install -D unplugin-vue-components
```

作用：自动识别并注册模板中使用的 Element Plus 组件，无需手动 import。

#### 5.2.2 unplugin-auto-import

安装命令：

```shell
npm install -D unplugin-auto-import
```

作用：自动导入 Vue 和 Element Plus 的 API（如 ref、ElMessage）

### 5.3 样式处理工具

#### 5.3.1 Sass 预处理器

安装命令：

```shell
npm install -D sass
```

作用：支持 Element Plus 的 Sass 变量覆盖，实现主题定制。

### 5.4 安装axios

```shell
npm install axios -S
```

Axios 是一个基于 **Promise** 的 HTTP 客户端，支持浏览器和 Node.js 环境。



### 5.5 所有组件

```sh
npm i element-plus -S
npm i @element-plus/icons-vue
npm i -D unplugin-vue-components unplugin-auto-import sass
npm i axios -S
```

1. **`element-plus`**提供丰富的 UI 组件（如表格、表单、弹窗等），是后台系统的视觉基础；
2. **`@element-plus/icons-vue`**独立图标组件（如 ElIcon），需单独安装并在入口文件全局注册；
3. **`unplugin-vue-components`** 自动识别并注册模板中使用的 Element Plus 组件，无需手动 import；
4. **`unplugin-auto-import`** 自动导入 Vue 和 Element Plus 的 API（如 ref、ElMessage）；
5. **`sass`** 处理样式预处理；
6. **`axios`**是基于Promise的 HTTP 客户端，支持浏览器和 Node.js 环境。
