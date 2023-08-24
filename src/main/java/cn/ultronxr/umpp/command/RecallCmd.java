package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.bot.BotManager;
import cn.ultronxr.umpp.eventHandler.GroupMsgEventHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.message.data.MessageSourceBuilder;
import net.mamoe.mirai.message.data.MessageSourceKind;
import net.mamoe.mirai.message.data.OnlineMessageSource;

/**
 * @author Ultronxr
 * @date 2022/06/29 10:43
 *
 * 命令 —— 重新发送被撤回的消息
 */
public class RecallCmd extends JSimpleCommand {

    public static final RecallCmd INSTANCE = new RecallCmd();


    private RecallCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "recall");
        setDescription("防撤回命令（重新发送被撤回的消息）");
        setPrefixOptional(false);
    }

    // >recall
    @Handler
    public void onCommand(CommandSender sender) {
        OnlineMessageSource msgSource = GroupMsgEventHandler.INSTANCE.getMsgRecalled(sender.getSubject().getId(), 0);
        if(msgSource != null) {
            sender.sendMessage(
                    //new MessageSourceBuilder()
                    //        .metadata(msgSource)
                    //        .time((int) System.currentTimeMillis())
                    //        .messagesFrom(msgSource)
                    //        .setSenderAndTarget(BotManager.getFirstOnlineBot(), sender.getSubject())
                    //        .build(BotManager.getFirstOnlineBot().getId(), MessageSourceKind.GROUP)
                    msgSource.getOriginalMessage()
            );
            return;
        }
        sender.sendMessage("未找到消息记录！");
    }

    // >recall <lastIndex>
    @Handler
    public void onCommand(CommandSender sender, @Name("最后被撤回的消息记录下标") Integer lastIndex) {
        OnlineMessageSource msgSource = GroupMsgEventHandler.INSTANCE.getMsgRecalled(sender.getSubject().getId(), lastIndex);
        if(msgSource != null) {
            sender.sendMessage(msgSource.getOriginalMessage());
            return;
        }
        sender.sendMessage("未找到消息记录！");
    }

}
