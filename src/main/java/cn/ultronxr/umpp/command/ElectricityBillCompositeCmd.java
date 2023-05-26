package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.service.ElectricityBillService;
import cn.ultronxr.umpp.service.impl.ElectricityBillServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;

/**
 * @author Ultronxr
 * @date 2023/05/26 12:52:07
 * @description 电费查询命令
 */
public class ElectricityBillCompositeCmd extends JCompositeCommand {

    public static final ElectricityBillCompositeCmd INSTANCE = new ElectricityBillCompositeCmd();

    private final ElectricityBillService electricityBillService = new ElectricityBillServiceImpl();

    private ElectricityBillCompositeCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "dfc", "dfconfig", "电费配置");
        setDescription("电费余额查询命令（配置项）");
        setPrefixOptional(false);
    }

    // >df qq <true/false>
    @Description("启用/禁用低电费QQ自动提醒功能")
    @SubCommand("qq")
    public void qq(CommandSender sender, @Name("true/false") Boolean status) {
        sender.sendMessage("QQ状态：" + status);
    }

    // >df sms <true/false>
    @Description("启用/禁用低电费短信自动提醒功能")
    @SubCommand("sms")
    public void sms(CommandSender sender, @Name("true/false") Boolean status) {
        sender.sendMessage("短信状态：" + status);
    }

}
