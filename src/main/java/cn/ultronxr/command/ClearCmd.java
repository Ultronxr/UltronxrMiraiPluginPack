package cn.ultronxr.command;

import cn.ultronxr.UltronxrMiraiPluginPack;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

/**
 * @author Ultronxr
 * @date 2022/06/29 10:43
 *
 * 命令 —— 清屏
 */
public class ClearCmd extends JSimpleCommand {
    public static final ClearCmd INSTANCE = new ClearCmd();

    private static final String CLEAR_CONTENT_DEFAULT = "\n".repeat(25);


    private ClearCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "clear");
        setDescription("清屏命令（发送若干换行符把消息刷过去）");
        setPrefixOptional(false);
    }

    // >clear
    @Handler
    public void onCommand(CommandSender sender) {
        sender.sendMessage(CLEAR_CONTENT_DEFAULT);
    }

    // >clear <空行数>
    @Handler
    public void onCommand(CommandSender sender, @Name("空行数") Integer line) {
        if(line > 0) {
            sender.sendMessage("\n".repeat(line));
            return;
        }
        sender.sendMessage(CLEAR_CONTENT_DEFAULT);
    }

}
