package cn.ultronxr;

import cn.ultronxr.bean.CommandInstance;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.LoggerAdapters;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2022/06/29 10:37:32
 *
 * 插件主类（入口）
 */
@Slf4j
public final class UltronxrMiraiPluginPack extends JavaPlugin {

    public static final UltronxrMiraiPluginPack INSTANCE = new UltronxrMiraiPluginPack();


    /**
     * 插件描述
     * {@see "https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/plugin/JVMPlugin.md#%E6%8F%8F%E8%BF%B0"}
     */
    private UltronxrMiraiPluginPack() {
        super(new JvmPluginDescriptionBuilder("cn.ultronxr.ultronxr-mirai-plugin-pack", "1.0-SNAPSHOT")
                .name("UltronxrMiraiPluginPack(UMPP)")
                .info("Java 开发的应用于 Mirai Console 的QQ聊天机器人插件包。")
                .author("Ultronxr")
                //.dependsOn()
                .build());
        // 尝试把Mirai的默认日志替换成slf4j
        LoggerAdapters.asMiraiLogger(log);
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        super.onLoad($this$onLoad);
        log.info("插件 UltronxrMiraiPluginPack(UMPP) 加载...");
    }

    @Override
    public void onEnable() {
        log.info("插件 UltronxrMiraiPluginPack(UMPP) 已启用！");

        CommandInstance.registerAllCmd();

        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        eventChannel.subscribeAlways(GroupMessageEvent.class, group -> {
            //监听群消息
            String msg = group.getMessage().contentToString();
            log.info(msg);
        });
        //eventChannel.subscribeAlways(FriendMessageEvent.class, friend -> {
        //    //监听好友消息
        //    getLogger().info(friend.getMessage().contentToString());
        //});
    }

    @Override
    public void onDisable() {
        log.info("插件 UltronxrMiraiPluginPack(UMPP) 已禁用！");
        CommandInstance.unregisterAllCmd();
    }

}
