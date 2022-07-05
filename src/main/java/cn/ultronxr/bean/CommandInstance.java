package cn.ultronxr.bean;

import cn.ultronxr.command.ClearCmd;
import cn.ultronxr.command.CmdHelperCmd;
import cn.ultronxr.command.RandomCmd;
import cn.ultronxr.command.SayCmd;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.command.Command;
import net.mamoe.mirai.console.command.CommandManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Ultronxr
 * @date 2022/07/04 15:40
 *
 * 统一集中管理所有Command的实例
 */
@Slf4j
public class CommandInstance {

    private static final ArrayList<Command> COMMANDS = new ArrayList<>();

    private static final CommandManager COMMAND_MANAGER = CommandManager.INSTANCE;

    static {
        addCmd(CmdHelperCmd.INSTANCE);
        addCmd(ClearCmd.INSTANCE);
        addCmd(RandomCmd.INSTANCE.COMPOSITE);
        addCmd(SayCmd.INSTANCE);
    }

    public static void addCmd(Command cmd) {
        COMMANDS.add(cmd);
    }

    public static void registerAllCmd() {
        COMMANDS.forEach(c -> {
            COMMAND_MANAGER.registerCommand(c, true);
            log.info("注册命令：{}", c.getPrimaryName());
        });
    }

    public static void unregisterAllCmd() {
        COMMANDS.forEach(c -> {
            COMMAND_MANAGER.unregisterCommand(c);
            log.info("注销命令：{}", c.getPrimaryName());
        });
    }

    public static String detailCmd(@NotNull Command cmd) {
        return "◆⭕ 主指令名 PrimaryName\n" + cmd.getPrimaryName() +
                "\n⭕ 次要指令名 SecondaryNames\n" + Arrays.toString(cmd.getSecondaryNames()) +
                "\n⭕ 可能的参数列表 Overloads\n" + cmd.getOverloads() +
                "\n⭕ 用法说明 Usage\n" + cmd.getUsage() +
                "\n⭕ 描述 Description\n" + cmd.getDescription() +
                "\n⭕ 为此指令分配的权限 Permission\n" + cmd.getPermission() +
                "\n⭕ 指令前缀是否可选 PrefixOptional\n" + cmd.getPrefixOptional() +
                "\n⭕ 指令拥有者 Owner\n" + cmd.getOwner() +
                "\n";
    }

    public static String listAllCmd() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== listAllCmd =====\n");
        COMMANDS.forEach(c -> sb.append(detailCmd(c)).append("\n"));
        sb.append("===== listAllCmd =====\n");
        return sb.toString();
    }

}
