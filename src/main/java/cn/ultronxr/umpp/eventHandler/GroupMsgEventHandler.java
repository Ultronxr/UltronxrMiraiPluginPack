package cn.ultronxr.umpp.eventHandler;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.OnlineMessageSource;

import java.util.*;

/**
 * @author Ultronxr
 * @date 2023/07/16 15:29:21
 * @description 群聊消息事件 - 群聊消息事件Handler，包括 群成员的消息处理、统计、撤回处理 等
 */
public class GroupMsgEventHandler {

    public static final GroupMsgEventHandler INSTANCE = new GroupMsgEventHandler();

    /**
     * 用于记录每个群的最后 DEFAULT_MSG_MAX_NUMBER 条消息记录，防撤回时可以调出记录<br/>
     * HashMap<群号，该群的消息记录队列>
     */
    private static final HashMap<Long, ArrayDeque<OnlineMessageSource>> MSG_HISTORY = new HashMap<>(10);

    /** 每个群只保留最后 x 条的群聊消息记录 */
    private static final Integer DEFAULT_MSG_MAX_NUMBER = 100;

    /**
     * 用于记录每个群最后被撤回的 DEFAULT_MSG_RECALLED_MAX_NUMBER 条消息记录<br/>
     * HashMap<群号，该群的被撤回的消息记录队列>
     */
    private static final HashMap<Long, ArrayDeque<OnlineMessageSource>> MSG_RECALLED = new HashMap<>(10);

    /** 每个群只保留最后 x 条的被撤回的群聊消息记录 */
    private static final Integer DEFAULT_MSG_RECALLED_MAX_NUMBER = 50;


    /**
     * 记录群聊消息<br/>
     * ⚠注意：Bot 自身发送的消息不会被记录！！！（当然也无法从被撤回消息记录中获取！）
     *
     * @param groupMessageEvent 群聊消息事件
     */
    public void logMsg(GroupMessageEvent groupMessageEvent) {
        OnlineMessageSource msgSource = groupMessageEvent.getSource();
        // 主动调用一次 originalMessage ，使其初始化，防止后续消息被撤回之后无法获取消息内容
        msgSource.getOriginalMessage();
        UltronxrMiraiPluginPack.log.info("【消息ID" + Arrays.toString(msgSource.getIds()) + "】 " + msgSource.getOriginalMessage().contentToString());

        Long groupId = groupMessageEvent.getGroup().getId();
        // 新的群，还没有消息记录，那么创建队列
        if(!MSG_HISTORY.containsKey(groupId)) {
            ArrayDeque<OnlineMessageSource> deque = new ArrayDeque<>(DEFAULT_MSG_MAX_NUMBER);
            deque.offerLast(msgSource);
            MSG_HISTORY.put(groupId, deque);
        } else {
            ArrayDeque<OnlineMessageSource> deque = MSG_HISTORY.get(groupId);
            // 已有的群，消息记录数量满了，那么删除最早的一条，再加入记录
            if(deque.size() >= DEFAULT_MSG_MAX_NUMBER) {
                deque.pollFirst();
                deque.offerLast(msgSource);
            } else {
                // 已有的群，消息记录数量没满，直接加入记录
                deque.offerLast(msgSource);
            }
        }

        UltronxrMiraiPluginPack.log.info("【当前消息记录队列（队首<-队尾）：" + Arrays.toString(MSG_HISTORY.get(groupId).peekFirst().getIds()) +" <- "+ Arrays.toString(MSG_HISTORY.get(groupId).peekLast().getIds()) + "】");
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
        ArrayDeque<OnlineMessageSource> deque = MSG_HISTORY.get(groupId);
        if(null == deque) {
            UltronxrMiraiPluginPack.log.info("【消息记录队列为空，无法寻找被撤回的消息对应的消息记录。】");
            return;
        }

        // msgId 相等则为同一条消息
        int msgId = groupRecallEvent.getMessageIds()[0];
        deque.stream()
                .filter(msg -> msg.getIds()[0] == msgId)
                .findFirst()
                .ifPresentOrElse(msg -> {
                    ArrayDeque<OnlineMessageSource> dequeRecalled = null;
                    if(!MSG_RECALLED.containsKey(groupId)) {
                        dequeRecalled = new ArrayDeque<>(DEFAULT_MSG_RECALLED_MAX_NUMBER);
                        dequeRecalled.offerLast(msg);
                    } else {
                        dequeRecalled = MSG_RECALLED.get(groupId);
                        if(dequeRecalled.size() >= DEFAULT_MSG_RECALLED_MAX_NUMBER) {
                            dequeRecalled.pollFirst();
                            dequeRecalled.offerLast(msg);
                        } else {
                            dequeRecalled.offerLast(msg);
                        }
                    }
                    MSG_RECALLED.put(groupId, dequeRecalled);
                }, null);

        UltronxrMiraiPluginPack.log.info("【当前被撤回的消息记录队列（队首<-队尾）：" + Arrays.toString(MSG_RECALLED.get(groupId).peekFirst().getIds()) +" <- "+ Arrays.toString(MSG_RECALLED.get(groupId).peekLast().getIds()) + "】");
    }

    /**
     * 获取某个群聊的最后第 last 条被撤回的消息
     *
     * @param groupId   群ID
     * @param lastIndex 最后被撤回的消息记录下标<br/>
     *                  从 0 开始，指向最后第 lastIndex+1 条被撤回的消息
     * @return 消息记录
     */
    public OnlineMessageSource getMsgRecalled(Long groupId, Integer lastIndex) {
        if(null == groupId) {
            return null;
        }
        ArrayDeque<OnlineMessageSource> deque = MSG_RECALLED.getOrDefault(groupId, null);
        if(null == deque || lastIndex >= deque.size() || lastIndex < 0) {
            return null;
        }
        if(0 == lastIndex) {
            return deque.peekLast();
        }
        OnlineMessageSource msg = deque.stream().skip(deque.size() - lastIndex-1).findFirst().orElse(null);
        return msg;
    }

}
