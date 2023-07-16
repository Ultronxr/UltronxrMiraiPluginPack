package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.eventHandler.GroupMsgEventHandler;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
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
        setDescription("防撤回命令（重新发送最后一条被撤回的消息）");
        setPrefixOptional(false);
    }

    // >recall
    @Handler
    public void onCommand(CommandSender sender) {
        OnlineMessageSource msgSource = GroupMsgEventHandler.INSTANCE.getMsgRecalled(sender.getSubject().getId());
        if(msgSource != null) {
            sender.sendMessage(msgSource.getOriginalMessage());
            return;
        }
        sender.sendMessage("未找到消息记录！");
    }

    // >recall <xxx>
    //@Handler
    //public void onCommand(CommandSender sender, @Name("xxx") Integer line) {
    //
    //}

}
