package cn.ultronxr;

import cn.ultronxr.command.ClearCmd;
import cn.ultronxr.command.RandomCmd;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/06/29 10:37:32
 *
 * 插件主类（入口）
 */
@Component
public final class UltronxrMiraiPluginPack extends JavaPlugin {
    public static final UltronxrMiraiPluginPack INSTANCE = new UltronxrMiraiPluginPack();

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
    public void onEnable() {

        CommandManager.INSTANCE.registerCommand(ClearCmd.INSTANCE, false);
        CommandManager.INSTANCE.registerCommand(RandomCmd.INSTANCE, false);

        getLogger().info("插件 UltronxrMiraiPluginPack 已加载！");
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        eventChannel.subscribeAlways(GroupMessageEvent.class, group -> {
            //监听群消息
            String msg = group.getMessage().contentToString();
            getLogger().info(msg);
            //group.getSubject().sendMessage("收到消息："+msg);
        });
        //eventChannel.subscribeAlways(FriendMessageEvent.class, friend -> {
        //    //监听好友消息
        //    getLogger().info(friend.getMessage().contentToString());
        //});
    }

    @Override
    public void onDisable() {
        CommandManager.INSTANCE.unregisterCommand(ClearCmd.INSTANCE);
        CommandManager.INSTANCE.unregisterCommand(RandomCmd.INSTANCE);
    }

}
