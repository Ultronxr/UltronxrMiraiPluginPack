package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.service.EpicGameService;
import cn.ultronxr.umpp.service.impl.EpicGameServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;

/**
 * @author Ultronxr
 * @date 2023/05/25 11:28:35
 * @description 命令 —— Epic 商城游戏信息
 */
public class EpicCmd extends JCompositeCommand {

    public static final EpicCmd INSTANCE = new EpicCmd();

    private final EpicGameService epicGameService = new EpicGameServiceImpl();

    private EpicCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "epic");
        setDescription("获取Epic商城游戏信息");
        setPrefixOptional(false);
    }

    // >epic free
    @Description("获取Epic免费游戏信息")
    @SubCommand
    public void free(CommandSender sender) {
        String msg = epicGameService.freeGamesPromotionsWeekly();
        sender.sendMessage(msg);
    }

}
