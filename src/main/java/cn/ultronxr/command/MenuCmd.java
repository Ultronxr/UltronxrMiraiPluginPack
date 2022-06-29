package cn.ultronxr.command;

import cn.ultronxr.UltronxrMiraiPluginPack;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

/**
 * @author Ultronxr
 * @date 2022/06/29 11:21
 *
 * 命令 —— 获取所有命令的帮助信息
 */
public class MenuCmd extends JSimpleCommand {
    public static final MenuCmd INSTANCE = new MenuCmd();

    private MenuCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "menu");
        setDescription("获取UltronxrMiraiPluginPack所有命令的帮助提示信息");
        setPrefixOptional(false);
    }

    private static final String MENU =
            "+—UMPP命令一览——+\n" +
            ">clear 清屏\n" +
            "\n" +
            "\n" +
            "\n" +
            "+—————————————+";

    @Handler
    public void onCommand(CommandSender sender) {
        sender.sendMessage(MENU);
    }

}
