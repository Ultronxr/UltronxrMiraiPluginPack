package cn.ultronxr.umpp.eventHandler;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.OnlineMessageSource;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Ultronxr
 * @date 2023/07/16 15:29:21
 * @description 群聊消息事件 - 群聊消息事件Handler，包括 群成员的消息处理、统计、撤回处理 等
 */
public class GroupMsgEventHandler {

    public static final GroupMsgEventHandler INSTANCE = new GroupMsgEventHandler();

    /** 用于记录每一个群的最后50条消息记录，防撤回时可以调出记录 */
    private static final HashMap<Long, Queue<OnlineMessageSource>> MSG_HISTORY = new HashMap<>(10);

    /** 每个群只保留最后 x 条的群聊消息记录 */
    private static final Integer DEFAULT_MSG_MAX_NUMBER = 50;

    /** 用于记录每个群被撤回的最后一条消息 */
    private static final HashMap<Long, OnlineMessageSource> MSG_RECALLED = new HashMap<>(10);


    /**
     * 记录群聊消息
     *
     * @param groupMessageEvent 群聊消息事件
     */
    public void logMsg(GroupMessageEvent groupMessageEvent) {
        OnlineMessageSource msgSource = groupMessageEvent.getSource();
        UltronxrMiraiPluginPack.log.info(msgSource.getOriginalMessage().contentToString());

        Long groupId = groupMessageEvent.getGroup().getId();
        // 新的群，还没有消息记录，那么创建队列
        if(!MSG_HISTORY.containsKey(groupId)) {
            Queue<OnlineMessageSource> queue = new ArrayBlockingQueue<>(DEFAULT_MSG_MAX_NUMBER);
            queue.offer(msgSource);
            MSG_HISTORY.put(groupId, queue);
            return;
        }
        Queue<OnlineMessageSource> queue = MSG_HISTORY.get(groupId);
        // 已有的群，消息记录数量满了，那么删除最早的一条，再加入记录
        if(queue.size() >= DEFAULT_MSG_MAX_NUMBER) {
            queue.poll();
            queue.offer(msgSource);
            return;
        }
        // 已有的群，消息记录数量没满，直接加入记录
        queue.offer(msgSource);
    }

    /**
     * 群聊消息被撤回事件<br/>
     * 如果某条消息被撤回了，那么就在消息记录中寻找相同ID的消息记录，标记出来并保存到撤回记录中
     *
     * @param groupRecallEvent 群聊消息撤回事件
     */
    public void recallMsg(MessageRecallEvent.GroupRecall groupRecallEvent) {
        // 从消息历史记录中，找出这个群的，对应的撤回消息
        long groupId = groupRecallEvent.getGroup().getId();
        Queue<OnlineMessageSource> queue = MSG_HISTORY.get(groupId);

        // msgId 相等则为同一条消息
        int msgId = groupRecallEvent.getMessageIds()[0];
        queue.stream()
                .filter(m -> m.getIds()[0] == msgId)
                .findFirst()
                .ifPresentOrElse(m -> MSG_RECALLED.put(groupId, m), null);
    }

    /**
     * 获取某个群聊的最后一条被撤回的消息
     *
     * @param groupId 群ID
     * @return 消息记录
     */
    public OnlineMessageSource getMsgRecalled(Long groupId) {
        if(null == groupId) {
            return null;
        }
        return MSG_RECALLED.getOrDefault(groupId, null);
    }

}
