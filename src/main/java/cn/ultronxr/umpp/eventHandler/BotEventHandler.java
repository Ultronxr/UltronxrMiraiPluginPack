package cn.ultronxr.umpp.eventHandler;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;

/**
 * @author Ultronxr
 * @date 2023/05/25 10:39:33
 * @description BOT事件 - 有关机器人BOT的事件处理器
 */
@Slf4j
public class BotEventHandler {

    public static final BotEventHandler INSTANCE = new BotEventHandler();

    /**
     * BOT离线事件处理器
     * @param botOfflineEvent 机器人离线事件
     */
    public void botOfflineHandler(BotOfflineEvent botOfflineEvent) {
        String reason = "其他未分类或测试原因";

        if(botOfflineEvent instanceof BotOfflineEvent.Active){
            reason = "主动离线（BotOfflineEvent.Active）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.Force){
            reason = "被挤下线（BotOfflineEvent.Force）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.Dropped){
            reason = "被服务器断开或因网络问题而掉线（BotOfflineEvent.Dropped）";
        } else if(botOfflineEvent instanceof BotOfflineEvent.RequireReconnect){
            reason = "服务器主动要求更换另一个服务器（BotOfflineEvent.RequireReconnect）";
        }

        log.warn("[system] qqrobot机器人离线事件，原因：" + reason);
    }

    /**
     * BOT（重新）登录事件处理器
     * @param botReloginEvent 机器人（重新）登录事件
     */
    public void botReloginHandler(BotReloginEvent botReloginEvent) {
        log.info("[system] qqrobot机器人（重新）登录事件");
    }

    /**
     * BOT 收到好友添加请求 事件处理器
     * @param newFriendRequestEvent 好友添加请求事件
     */
    public void newFriendRequestHandler(NewFriendRequestEvent newFriendRequestEvent) {
        long requestQQ = newFriendRequestEvent.getFromId();
        String requestMsg = newFriendRequestEvent.getMessage().trim();
        log.info("[system] qqrobot机器人收到好友添加请求：qq={} , message={}", requestQQ, requestMsg);

        if(requestMsg.equals("InitFriendRequestKey123000")) { // TODO 修改这个密钥
            log.info("[system] qqrobot机器人好友添加请求处理结果：qq={} , 处理结果=accept", requestQQ);
            newFriendRequestEvent.accept();
            return;
        }
        newFriendRequestEvent.reject(false);
        log.info("[system] qqrobot机器人好友添加请求处理结果：qq={} , 处理结果=reject", requestQQ);
    }

}
