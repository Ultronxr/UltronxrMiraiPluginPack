package cn.ultronxr.command;

import cn.hutool.core.util.ReUtil;
import cn.ultronxr.UltronxrMiraiPluginPack;
import cn.ultronxr.bean.GlobalData;
import cn.ultronxr.service.RandomService;
import cn.ultronxr.service.impl.RandomServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ultronxr
 * @date 2022/06/29 11:33
 *
 * 命令 —— 随机数
 */
public class RandomCmd {

    public static final RandomCmd INSTANCE = new RandomCmd();
    //public final SimpleCommand SIMPLE = new SimpleCommand();
    public final CompositeCommand COMPOSITE = new CompositeCommand();

    private final RandomService randomService = new RandomServiceImpl();


    //protected class SimpleCommand extends JSimpleCommand {
    //
    //    private SimpleCommand() {
    //        super(UltronxrMiraiPluginPack.INSTANCE, "random");
    //        setDescription("随机整数命令");
    //        setPrefixOptional(false);
    //    }
    //
    //    @Handler
    //    public void onCommand(CommandSender sender) {
    //        sender.sendMessage(randomService.randomLengthNumber(1));
    //    }
    //}

    protected class CompositeCommand extends JCompositeCommand {

        private CompositeCommand() {
            super(UltronxrMiraiPluginPack.INSTANCE, "random");
            setDescription("随机整数命令");
            setPrefixOptional(false);
        }

        // >random
        //@SubCommand(" ")
        //@Description("生成一位长度的随机正整数")
        //public void onCommand(CommandSender sender) {
        //    sender.sendMessage(randomService.randomLengthNumber(1));
        //}

        // >random figure <位数>
        @Description("生成指定位数长度的随机正整数")
        @SubCommand({"f", "figure"})
        public void onCommand(CommandSender sender, @Name("位数") @NotNull Integer figure) {
            if(figure > 0) {
                sender.sendMessage(randomService.randomLengthNumber(figure));
                return;
            }
            sender.sendMessage("指定位数有误！");
        }

        // >random endpoint <左端点值> <右端点值>
        @Description("生成指定两端点值（包含）内的随机整数")
        @SubCommand({"e", "endpoint"})
        public void onCommand(CommandSender sender, @Name("左端点值") @NotNull Long left, @Name("右端点值") @NotNull Long right) {
            String result = String.valueOf(randomService.randomIntervalNumber(left, right));
            sender.sendMessage(result);
        }

        // >random interval <区间表达式>
        @Description("生成指定区间内的随机整数")
        @SubCommand({"i", "interval"})
        public void onCommand(CommandSender sender, @Name("区间表达式") @NotNull String interval) {
            List<String> regexGroups = null;
            String result = null;
            try {
                regexGroups = ReUtil.getAllGroups(Pattern.compile(GlobalData.Regex.MATH_INTERVAL), interval.replaceAll(" ", ""));
                result = randomService.randomIntervalNumber(regexGroups).toString();
            } catch (Exception ex) {
                sender.sendMessage("区间表达式有误！");
                return;
            }
            sender.sendMessage(result);
        }
    }
}
