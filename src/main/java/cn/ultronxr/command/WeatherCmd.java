package cn.ultronxr.command;

import cn.ultronxr.UltronxrMiraiPluginPack;
import cn.ultronxr.service.WeatherService;
import cn.ultronxr.service.impl.WeatherServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

/**
 * @author Ultronxr
 * @date 2022/07/06 10:42
 *
 * 命令 —— 天气信息
 */
public class WeatherCmd extends JSimpleCommand {

    public static final WeatherCmd INSTANCE = new WeatherCmd();

    private final WeatherService weatherService = new WeatherServiceImpl();


    private WeatherCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "weather");
        setDescription("获取指定地区的天气信息（缺省值“杭州”）");
        setPrefixOptional(false);
    }

    // >weather
    @Handler
    public void onCommand(CommandSender sender) {
        sender.sendMessage(weatherService.allInOne("杭州"));
    }

    // >weather <地区>
    @Handler
    public void onCommand(CommandSender sender, @Name("地区") String area) {
        sender.sendMessage(weatherService.allInOne(area));
    }

}
