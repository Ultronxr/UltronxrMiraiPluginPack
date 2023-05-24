package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.service.SayService;
import cn.ultronxr.umpp.service.impl.SayServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;

/**
 * @author Ultronxr
 * @date 2022/07/04 15:31
 *
 * 命令 —— 发言（彩虹屁、朋友圈语录、毒鸡汤、口吐芬芳（废弃）、火力全开（废弃））
 */
public class SayCmd extends JCompositeCommand {

    public static final SayCmd INSTANCE = new SayCmd();

    private final SayService sayService = new SayServiceImpl();


    private SayCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "say");
        setDescription("发言命令（彩虹屁、朋友圈语录、毒鸡汤、口吐芬芳、火力全开）");
        setPrefixOptional(false);
    }

    // >say chp
    @Description("彩虹屁")
    @SubCommand
    public void chp(CommandSender sender) {
        sender.sendMessage(sayService.sayCHP());
    }

    // >say pyq
    @Description("朋友圈语录")
    @SubCommand
    public void pyq(CommandSender sender) {
        sender.sendMessage(sayService.sayPYQ());
    }

    // >say djt
    @Description("毒鸡汤")
    @SubCommand
    public void djt(CommandSender sender) {
        sender.sendMessage(sayService.sayDJT());
    }

    // >say ktff
    //@Description("口吐芬芳")
    //@SubCommand
    //public void ktff(CommandSender sender) {
    //    sender.sendMessage(sayService.sayKTFF());
    //}

    // >say hlqk
    //@Description("火力全开")
    //@SubCommand
    //public void hlqk(CommandSender sender) {
    //    sender.sendMessage(sayService.sayHLQK());
    //}

}
