package cn.ultronxr.command;

import cn.ultronxr.UltronxrMiraiPluginPack;
import cn.ultronxr.service.WeatherService;
import cn.ultronxr.service.impl.WeatherServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;

/**
 * @author Ultronxr
 * @date 2022/07/06 10:42
 *
 * 命令 —— 天气信息
 */
public class WeatherCmd extends JCompositeCommand {

    public static final WeatherCmd INSTANCE = new WeatherCmd();

    private final WeatherService weatherService = new WeatherServiceImpl();


    private WeatherCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "weather");
        setDescription("天气信息命令");
        setPrefixOptional(false);
    }

    @Description("获取指定地区的天气信息")
    @SubCommand
    public void area(CommandSender sender, @Name("地区") String area) {
        sender.sendMessage("");
    }


}
