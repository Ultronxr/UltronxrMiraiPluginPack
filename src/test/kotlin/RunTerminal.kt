package cn.ultronxr

import java.io.File
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader

fun setupWorkingDir() {
    // see: net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal
    System.setProperty("user.dir", File("debug-sandbox").absolutePath)
}

/**
 * @author Ultronxr
 * @date 2022/06/29 10:37:32
 *
 * 插件测试方法
 */
suspend fun main() {
    setupWorkingDir()

    // 启动MCL
    MiraiConsoleTerminalLoader.startAsDaemon()

    val pluginInstance = UltronxrMiraiPluginPack.INSTANCE

    // 主动加载插件, Console 会调用 MiraiConsolePluginPackage.onLoad
    pluginInstance.load()
    // 主动启用插件, Console 会调用 MiraiConsolePluginPackage.onEnable
    pluginInstance.enable()

    // 登录机器人QQ账号
    val bot = MiraiConsole.addBot(123, "123").alsoLogin()

    MiraiConsole.job.join()
}
