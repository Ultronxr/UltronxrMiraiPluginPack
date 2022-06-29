package cn.ultronxr.command;

import cn.hutool.core.util.ReUtil;
import cn.ultronxr.UltronxrMiraiPluginPack;
import cn.ultronxr.bean.GlobalData;
import cn.ultronxr.service.RandomService;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ultronxr
 * @date 2022/06/29 11:33
 *
 * 命令 —— 随机数
 */
@Component
public class RandomCmd extends JCompositeCommand {
    public static final RandomCmd INSTANCE = new RandomCmd();

    @Autowired
    private RandomService randomService;


    private RandomCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "random");
        setDescription("随机数命令");
        setPrefixOptional(false);
    }

    // >random
    public void onCommand(CommandSender sender) {
        sender.sendMessage(randomService.randomLengthNumber(1));
    }

    // >random figure <位数>
    @SubCommand("figure")
    public void onCommand(CommandSender sender, Integer figure) {
        if(figure > 0) {
            sender.sendMessage(randomService.randomLengthNumber(figure));
            return;
        }
        sender.sendMessage(randomService.randomLengthNumber(1));
    }

    // >random endpoint <左端点值> <右端点值>
    @SubCommand("endpoint")
    public void onCommand(CommandSender sender, Long left, Long right) {
        String result = String.valueOf(randomService.randomIntervalNumber(left, right));
        sender.sendMessage(result);
    }

    // >random interval <区间表达式>
    @SubCommand("interval")
    public void onCommand(CommandSender sender, String interval) {
        List<String> regexGroups = null;
        try {
            regexGroups = ReUtil.getAllGroups(Pattern.compile(GlobalData.Regex.MATH_INTERVAL), interval.replaceAll(" ", ""));
        } catch (Exception ex) {
            sender.sendMessage("区间表达式有误！");
            return;
        }
        String result = randomService.randomIntervalNumber(regexGroups).toString();
        sender.sendMessage(result);
    }

}
