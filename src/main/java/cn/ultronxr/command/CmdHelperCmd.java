package cn.ultronxr.command;

import cn.ultronxr.UltronxrMiraiPluginPack;
import cn.ultronxr.bean.CommandInstance;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

/**
 * @author Ultronxr
 * @date 2022/07/05 15:37
 *
 * 命令 —— 列出 UMPP 所有命令的详细信息（仅限DEBUG，非用户使用）
 */
public class CmdHelperCmd extends JSimpleCommand {

    public static final CmdHelperCmd INSTANCE = new CmdHelperCmd();

    private CmdHelperCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "cmdhelper");
        setDescription("列出 UMPP 所有命令的详细信息（仅限DEBUG，非用户使用）");
        setPrefixOptional(false);
    }

    // >cmdhelper
    @Handler
    public void onCommand(CommandSender sender) {
        sender.sendMessage(CommandInstance.listAllCmd());
    }

}
