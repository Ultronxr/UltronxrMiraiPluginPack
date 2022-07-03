package cn.ultronxr;

import cn.ultronxr.command.ClearCmd;
import cn.ultronxr.command.RandomCmd;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2022/06/29 10:37:32
 *
 * 插件主类（入口）
 */
public final class UltronxrMiraiPluginPack extends JavaPlugin {
    public static final UltronxrMiraiPluginPack INSTANCE = new UltronxrMiraiPluginPack();

    public static final MiraiLogger logger = INSTANCE.getLogger();

    /**
     * 插件描述
     * {@see "https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/plugin/JVMPlugin.md#%E6%8F%8F%E8%BF%B0"}
     */
    private UltronxrMiraiPluginPack() {
        super(new JvmPluginDescriptionBuilder("cn.ultronxr.ultronxr-mirai-plugin-pack", "1.0-SNAPSHOT")
                .name("UltronxrMiraiPluginPack")
                .info("UltronxrMiraiPluginPack")
                .author("Ultronxr")
                //.dependsOn()
                .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        super.onLoad($this$onLoad);
        logger.info("插件 UltronxrMiraiPluginPack 加载...");
    }

    @Override
    public void onEnable() {

        CommandManager.INSTANCE.registerCommand(ClearCmd.INSTANCE, true);
        //CommandManager.INSTANCE.registerCommand(RandomCmd.INSTANCE.SIMPLE, true);
        CommandManager.INSTANCE.registerCommand(RandomCmd.INSTANCE.COMPOSITE, true);

        logger.info("插件 UltronxrMiraiPluginPack 已启用！");
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        eventChannel.subscribeAlways(GroupMessageEvent.class, group -> {
            //监听群消息
            String msg = group.getMessage().contentToString();
            logger.info(msg);
            //group.getSubject().sendMessage("收到消息："+msg);
        });
        //eventChannel.subscribeAlways(FriendMessageEvent.class, friend -> {
        //    //监听好友消息
        //    getLogger().info(friend.getMessage().contentToString());
        //});
    }

    @Override
    public void onDisable() {
        logger.info("插件 UltronxrMiraiPluginPack 已禁用！");
        CommandManager.INSTANCE.unregisterCommand(ClearCmd.INSTANCE);
        //CommandManager.INSTANCE.unregisterCommand(RandomCmd.INSTANCE.SIMPLE);
        CommandManager.INSTANCE.unregisterCommand(RandomCmd.INSTANCE.COMPOSITE);
    }

}
