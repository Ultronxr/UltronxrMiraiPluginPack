package cn.ultronxr.umpp;

import cn.ultronxr.umpp.bean.CommandInstance;
import cn.ultronxr.umpp.eventHandler.BotEventHandler;
import cn.ultronxr.umpp.eventHandler.GroupEventHandler;
import cn.ultronxr.umpp.eventHandler.GroupMsgEventHandler;
import cn.ultronxr.umpp.timer.TimerManager;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.utils.MiraiLogger;
import org.apache.log4j.PropertyConfigurator;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

/**
 * @author Ultronxr
 * @date 2022/06/29 10:37:32
 *
 * 插件主类（入口）
 */
public final class UltronxrMiraiPluginPack extends JavaPlugin {

    public static final UltronxrMiraiPluginPack INSTANCE = new UltronxrMiraiPluginPack();

    public static final MiraiLogger log = UltronxrMiraiPluginPack.INSTANCE.getLogger();

    static {
        // 手动指定 jar包内的 log4j 配置文件
        InputStream log4jConfigIS = UltronxrMiraiPluginPack.class.getClassLoader().getResourceAsStream("log4j.properties");
        PropertyConfigurator.configure(log4jConfigIS);
    }

    /**
     * 插件描述
     * {@see "https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/plugin/JVMPlugin.md#%E6%8F%8F%E8%BF%B0"}
     */
    public UltronxrMiraiPluginPack() {
        super(new JvmPluginDescriptionBuilder("cn.ultronxr.ultronxr-mirai-plugin-pack", "0.1.6")
                .name("UltronxrMiraiPluginPack(UMPP)")
                .info("Java 开发的应用于 Mirai Console 的QQ聊天机器人插件包。")
                .author("Ultronxr")
                //.dependsOn(PluginDependency.parseFromString("net.mamoe.chat-command:0.5.1"))
                .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        super.onLoad($this$onLoad);
        log.info("插件 UltronxrMiraiPluginPack(UMPP) 加载...");
    }

    @Override
    public void onEnable() {
        log.info("插件 UltronxrMiraiPluginPack(UMPP) 已启用！");

        // 注册命令
        CommandInstance.registerAllCmd();
        // 启动定时器
        TimerManager.runTimerTasks();

        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);

        // BOT事件
        eventChannel.subscribeAlways(BotOfflineEvent.class, BotEventHandler.INSTANCE::botOfflineHandler);
        eventChannel.subscribeAlways(BotReloginEvent.class, BotEventHandler.INSTANCE::botReloginHandler);

        // 群事件
        eventChannel.subscribeAlways(MemberJoinEvent.class, GroupEventHandler.INSTANCE::onMemberJoin);
        eventChannel.subscribeAlways(MemberLeaveEvent.class, GroupEventHandler.INSTANCE::onMemberLeave);

        // 群消息事件
        eventChannel.subscribeAlways(GroupMessageEvent.class, GroupMsgEventHandler.INSTANCE::logMsg);
        eventChannel.subscribeAlways(MessageRecallEvent.GroupRecall.class, GroupMsgEventHandler.INSTANCE::recallMsg);

        //eventChannel.subscribeAlways(GroupMessageEvent.class, group -> {
        //    //监听群消息
        //    String msg = group.getMessage().contentToString();
        //    log.info(msg);
        //});
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
