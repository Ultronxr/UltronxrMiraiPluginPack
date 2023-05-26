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

## mirai 核心组件的版本修改方式

[MCL命令手册](https://github.com/iTXTech/mirai-console-loader/blob/master/cli.md) 中已经介绍过， `maven` 支持两个子频道： `stable` 与 `prerelease` 。

可以使用命令分别对每一个组件包进行更新，但是这样太麻烦了，这里我使用直接修改配置文件的方式完成。

修改 MCL 安装根目录下的 `config.json` 文件（如下版本仅供示例，具体版本自己选择）：

```json
{
  // ...
  // 这是 stable 频道的版本配置（注意 channel version type 字段）
  "packages": {
    "net.mamoe:mirai-console": {
      "channel": "maven-stable",
      "version": "2.14.0",
      "type": "libs",
      "versionLocked": false
    },
    "net.mamoe:mirai-console-terminal": {
      "channel": "maven-stable",
      "version": "2.14.0",
      "type": "libs",
      "versionLocked": false
    },
    "net.mamoe:mirai-core-all": {
      "channel": "maven-stable",
      "version": "2.14.0",
      "type": "libs",
      "versionLocked": false
    },
    // ...
  },
  // ...
}
```

```json
{
  // ...
  // 这是 prerelease 频道的版本配置（注意 channel version type 字段）
  "packages": {
    "net.mamoe:mirai-console": {
      "channel": "maven-prerelease",
      "version": "2.15.0-M1",
      "type": "libs",
      "versionLocked": false
    },
    "net.mamoe:mirai-console-terminal": {
      "channel": "maven-prerelease",
      "version": "2.15.0-M1",
      "type": "libs",
      "versionLocked": false
    },
    "net.mamoe:mirai-core-all": {
      "channel": "maven-prerelease",
      "version": "2.15.0-M1",
      "type": "libs",
      "versionLocked": false
    },
    // ...
  },
  // ...
}
```

# 开发心得

## 报错及解决

### 日志相关

自己添加 slf4j (log4j2) 日志框架。

1. lombok 插件无法正常使用

+ 解决

正确添加日志及 lombok 依赖：

```kotlin
dependencies {
    implementation("org.slf4j:slf4j-log4j12:1.7.36")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
}
```

并在 IDEA 中下载和启用 Lombok 插件。

2. slf4j 加载冲突

+ 日志

```log
Caused by: java.lang.LinkageError: loader constraint violation: loader 'ultronxr-mirai-plugin-pack-dev.mirai2.jar' @61cd1c71 wants to load interface org.slf4j.Logger. A different interface with the same name was previously loaded by 'app'. (org.slf4j.Logger is in unnamed module of loader 'app')
```

+ 现象

IDEA 编译、运行正常（使用 RunTerminal.kt 运行，下同）；

打包成插件置入MCL运行报错（包括使用 Mirai Console IDEA 开发插件中的 mirai - run - runConsole 运行，下同）。

+ 原因

在插件主入口类（UltronxrMiraiPluginPack.java）使用了 slf4j 的 Logger 。

包括：lombok 插件的注解 `@Slf4j` 或 代码引入 `Logger log = LoggerFactory.getLogger(UltronxrMiraiPluginPack.class);`

+ 解决

不要在插件主入口类使用 slf4j 的 Logger （其他文件中使用无所谓），而是使用自带的 MiraiLogger 。

```java
public final class UltronxrMiraiPluginPack extends JavaPlugin {

    public static final UltronxrMiraiPluginPack INSTANCE = new UltronxrMiraiPluginPack();

    // 使用自带的 MiraiLogger
    public static final MiraiLogger log = UltronxrMiraiPluginPack.INSTANCE.getLogger();
    
    // ...
}
```

3. 找不到 log4j 配置文件

+ 日志

```log
W/stderr: log4j:WARN No appenders could be found for logger (cn.ultronxr.util.LogUtils).
W/stderr: log4j:WARN Please initialize the log4j system properly.
W/stderr: log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
```

+ 现象

IDEA 编译、运行正常；

打包成插件置入MCL运行报错，且日志无法正常打印。（打包成mirai 2代插件会报错，打包成mirai 1代插件可以正常运行。）

+ 原因

虽然 log4j.properties 配置文件被打包入插件 jar 包，但是2代插件的打包方式没有把log4j依赖包打入jar包，而是使用第三方路径加载，也就是说log4j搜索不到jar包内的配置文件。

这也是打包成1代插件包可以正常运行的原因。

+ 解决

手动指定配置文件，在插件如入口类添加如下代码：

```java
public final class UltronxrMiraiPluginPack extends JavaPlugin {

    public static final UltronxrMiraiPluginPack INSTANCE = new UltronxrMiraiPluginPack();

    public static final MiraiLogger log = UltronxrMiraiPluginPack.INSTANCE.getLogger();

    static {
        // 手动指定 jar包内的 log4j 配置文件
        InputStream log4jConfigIS = UltronxrMiraiPluginPack.class.getClassLoader().getResourceAsStream("log4j.properties");
        PropertyConfigurator.configure(log4jConfigIS);
    }
    
    // ...
}
```

4. slf4j 日志可以正常输出， mirai console 自带的日志无法输出

+ 现象

IDEA 、打包成插件置入MCL 运行时，slf4j 日志都可以正常输出， mirai console 自带的日志都无法输出。

+ 解决

删除 LoggerAdapters 的代码。（我目前对其功能不甚了解。）

```java
public final class UltronxrMiraiPluginPack extends JavaPlugin {

    public static final UltronxrMiraiPluginPack INSTANCE = new UltronxrMiraiPluginPack();

    public static final MiraiLogger log = UltronxrMiraiPluginPack.INSTANCE.getLogger();

    static {
        // 删除如下两行代码
        // LoggerAdapters.useLog4j2();
        // LoggerAdapters.asMiraiLogger();

        InputStream log4jConfigIS = UltronxrMiraiPluginPack.class.getClassLoader().getResourceAsStream("log4j.properties");
        PropertyConfigurator.configure(log4jConfigIS);
    }
    
    // ...
}
```

5. 权限重复，注册失败

+ 日志

```log
W/stderr: Caused by: net.mamoe.mirai.console.permission.PermissionRegistryConflictException: Conflicting Permission registry. new: PermissionImpl(id=cn.ultronxr.ultronxr-mirai-plugin-pack:*, description='The base permission', parent=PermissionImpl(id=*:*, description='The root permission', parent=<self>)), existing: PermissionImpl(id=cn.ultronxr.ultronxr-mirai-plugin-pack:*, description='The base permission', parent=PermissionImpl(id=*:*, description='The root permission', parent=<self>))
W/stderr: 	at net.mamoe.mirai.console.internal.permission.AbstractConcurrentPermissionService.register(AbstractConcurrentPermissionService.kt:29)
W/stderr: 	at net.mamoe.mirai.console.permission.PermissionService.register$default(PermissionService.kt:101)
W/stderr: 	at net.mamoe.mirai.console.internal.plugin.JvmPluginInternal$parentPermission$2.invoke(JvmPluginInternal.kt:52)
W/stderr: 	at net.mamoe.mirai.console.internal.plugin.JvmPluginInternal$parentPermission$2.invoke(JvmPluginInternal.kt:51)
W/stderr: 	at kotlin.SynchronizedLazyImpl.getValue(LazyJVM.kt:74)
W/stderr: 	at net.mamoe.mirai.console.internal.plugin.JvmPluginInternal.getParentPermission(JvmPluginInternal.kt:51)
W/stderr: 	at net.mamoe.mirai.console.internal.plugin.JvmPluginInternal.internalOnEnable$mirai_console(JvmPluginInternal.kt:126)
W/stderr: 	at net.mamoe.mirai.console.internal.plugin.BuiltInJvmPluginLoaderImpl.enable(BuiltInJvmPluginLoaderImpl.kt:278)
W/stderr: 	... 13 more
```

+ 现象

IDEA 、打包成插件置入MCL 运行时，都无法正常运行，会报错。

+ 原因

相同id权限无法注册。

+ 解决

检查代码中是否存在相同权限id；

检查 MCL 插件目录下是否存在相同插件jar包（旧版本的插件包没有删除）；

检查项目根目录的 debug-sandbox\plugins 下是否存在相同插件jar包（使用 mirai - run - runConsole 运行后，这里会生成一个 dev 版插件jar包，会和 IDEA 内运行冲突）。
