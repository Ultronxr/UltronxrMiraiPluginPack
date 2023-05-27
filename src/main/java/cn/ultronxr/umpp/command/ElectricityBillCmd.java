package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.service.ElectricityBillService;
import cn.ultronxr.umpp.service.impl.ElectricityBillServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

/**
 * @author Ultronxr
 * @date 2023/05/26 12:52:07
 * @description 电费查询命令
 */
public class ElectricityBillCmd extends JSimpleCommand {

    public static final ElectricityBillCmd INSTANCE = new ElectricityBillCmd();

    private final ElectricityBillService electricityBillService = new ElectricityBillServiceImpl();

    private ElectricityBillCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "df", "电费");
        setDescription("电费余额查询命令");
        setPrefixOptional(false);
    }

    // >df
    @Handler
    public void onCommand(CommandSender sender) {
        sender.sendMessage(electricityBillService.getResult().toString());
    }

}
