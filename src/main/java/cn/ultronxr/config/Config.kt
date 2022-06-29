package cn.ultronxr.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * @author Ultronxr
 * @date 2022/06/29 11:05
 */
object Config : AutoSavePluginConfig("config") {
    @ValueDescription("")
    val apiKey: String by value()
    @ValueDescription("")
    val extendApiKey: List<String> by value()
    @ValueDescription("")
    val platform: String by value("PC")
}
