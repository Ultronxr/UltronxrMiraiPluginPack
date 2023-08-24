package cn.ultronxr.umpp.bot;

import net.mamoe.mirai.Bot;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/05/28 11:13:24
 * @description 集中管理机器人
 */
public class BotManager {

    /**
     * 获取第一个在线的 bot <br/>
     * 如果没有在线的 bot ，那么返回 {@code null}
     */
    public static Bot getFirstOnlineBot() {
        List<Bot> botList = Bot.getInstances();
        for (Bot bot : botList) {
            if(bot.isOnline()) {
                return bot;
            }
        }
        return null;
    }

}
