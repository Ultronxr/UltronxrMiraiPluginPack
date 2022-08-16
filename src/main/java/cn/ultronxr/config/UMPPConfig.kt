package cn.ultronxr.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * @author Ultronxr
 * @date 2022/06/29 11:05
 */
object UMPPConfig : AutoSavePluginConfig("UMPPConfig") {

    @ValueDescription("插件是否启用：设置 false 时禁用插件")
    val enabled: Boolean by value(true)

    @ValueDescription("插件日志等级：ALL,TRACE,DEBUG,INFO,WARN,ERROR,FATAL,OFF")
    val logPriority: String by value("INFO")

    @ValueDescription("本插件中已启用的命令（主指令名）")
    val commandEnabled: Array<String> by value(arrayOf())

}
