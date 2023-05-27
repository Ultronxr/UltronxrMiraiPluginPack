package cn.ultronxr.umpp.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * @author Ultronxr
 * @date 2022/06/29 11:05
 */
@ValueDescription("UltronxrMiraiPluginPack插件配置")
public class UMPPConfig : AutoSavePluginConfig("UMPPConfig") {

    //@ValueDescription("插件是否启用：设置 false 时禁用插件")
    //val enabled: Boolean by value(true)
    //
    //@ValueDescription("插件日志等级：ALL,TRACE,DEBUG,INFO,WARN,ERROR,FATAL,OFF")
    //val logPriority: String by value("INFO")
    //
    //@ValueDescription("本插件中已启用的命令（主指令名）")
    //val commandEnabled: Array<String> by value(arrayOf())

    @Serializable
    public data class ElectricityBill(
        val qq: Boolean,
        val sms: Boolean,
    ) {

    }

    public val bill: UMPPConfig.ElectricityBill by value(
        UMPPConfig.ElectricityBill (
            qq = true,
            sms = false
        )
    )

}
