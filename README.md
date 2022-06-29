# Mirai Console 生态介绍

+ [Mirai Core](https://github.com/mamoe/mirai/tree/dev/mirai-core) ：[mirai](https://github.com/mamoe/mirai) 核心实现模块，提供机器人的核心功能支持。
+ [Mirai Console Loader](https://github.com/iTXTech/mirai-console-loader) (MCL) ：模块化、轻量级且支持完全自定义的 mirai 加载器。
+ [Mirai Console](https://github.com/mamoe/mirai/tree/dev/mirai-console) ：默认指 mirai 控制台 [后端](https://github.com/mamoe/mirai/tree/dev/mirai-console/backend/mirai-console) ，是 mirai 的控制台应用框架，提供命令、插件等支持。
+ [Mirai Console Terminal](https://github.com/mamoe/mirai/tree/dev/mirai-console/frontend/mirai-console-terminal) ：mirai 控制台前端，与用户交互。

简单总结：Core提供机器人的核心功能；Console 和 Console Terminal 提供命令及插件支持；MCL是一个启动器，负责启动、维护、更新上述各模块。

# 如何开发插件

[JVM 平台 mirai-console 插件开发](https://github.com/mamoe/mirai/blob/dev/docs/README.md#jvm-%E5%B9%B3%E5%8F%B0-mirai-console-%E6%8F%92%E4%BB%B6%E5%BC%80%E5%8F%91)

## 几个关键步骤

[安装 IDE 插件](https://github.com/mamoe/mirai/blob/dev/docs/Preparations.md#%E5%AE%89%E8%A3%85-ide-%E6%8F%92%E4%BB%B6)

[使用项目创建工具新建项目](https://github.com/mamoe/mirai/blob/dev/docs/Preparations.md#%E5%AE%89%E8%A3%85-ide-%E6%8F%92%E4%BB%B6)

[Mirai Core 开发文档](https://github.com/mamoe/mirai/blob/dev/docs/CoreAPI.md)

[Mirai Console 开发文档](https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/README.md)

# 如何使用插件

## 安装MCL

[Mirai - Console Terminal #安装](https://github.com/mamoe/mirai/blob/dev/docs/ConsoleTerminal.md#%E5%AE%89%E8%A3%85)

自动安装程序会帮你安装好MCL、Mirai Core、Mirai Console、Mirai Console Terminal。

## 安装插件

从 [社区论坛](https://mirai.mamoe.net/category/11/%E6%8F%92%E4%BB%B6%E5%8F%91%E5%B8%83) 中下载插件jar包，放入MCL的根目录 `plugins\` 文件夹，重启MCL加载插件即可。

## 用户手册

[Mirai Console 纯控制台前端使用简介（包含MCL安装）](https://github.com/mamoe/mirai/blob/dev/docs/ConsoleTerminal.md)

[MCL命令手册](https://github.com/iTXTech/mirai-console-loader/blob/master/cli.md)

[Mirai Console 内置命令手册](https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/BuiltInCommands.md)

[权限字符串表示 列表](https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/Permissions.md#%E5%AD%97%E7%AC%A6%E4%B8%B2%E8%A1%A8%E7%A4%BA)
