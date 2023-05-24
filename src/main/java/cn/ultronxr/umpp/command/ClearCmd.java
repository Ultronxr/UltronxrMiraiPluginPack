package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
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

    private static final int MAX_LINE_NUMBER = 80;


    private ClearCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "clear");
        setDescription("清屏命令（空行数取值区间[1,80]，缺省值25）");
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
            line = Math.min(line, MAX_LINE_NUMBER);
            sender.sendMessage("\n".repeat(line));
            return;
        }
        sender.sendMessage(CLEAR_CONTENT_DEFAULT);
    }

}
