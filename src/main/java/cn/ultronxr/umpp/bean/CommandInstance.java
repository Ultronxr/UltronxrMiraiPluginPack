package cn.ultronxr.umpp.bean;

import cn.ultronxr.umpp.command.*;
import cn.ultronxr.umpp.command.codeToolkit.*;
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

    /**
     * “命令维护容器”<br/>
     * 注：该容器只维护所有命令实例对象，不负责维护其是否注册/注销等额外内容，如有需求请手动维护（优先考虑使用指令管理器）
     */
    private static final ArrayList<Command> COMMANDS = new ArrayList<>();

    /** Mirai Console 内置的 “指令管理器” */
    public static final CommandManager COMMAND_MANAGER = CommandManager.INSTANCE;


    static {
        // 在这里把所有的Command单例添加到容器
        addCmd(CmdHelperCmd.INSTANCE);
        addCmd(ClearCmd.INSTANCE);
        addCmd(QRCodeCmd.INSTANCE);
        addCmd(RandomCmd.INSTANCE);
        addCmd(SayCmd.INSTANCE);
        addCmd(WeatherCmd.INSTANCE);
        addCmd(EpicCmd.INSTANCE);
        addCmd(ImgCmd.INSTANCE);
        addCmd(Md5Cmd.INSTANCE);
        addCmd(UrlCmd.INSTANCE);
        addCmd(UnicodeCmd.INSTANCE);
        addCmd(ElectricityBillCmd.INSTANCE);
        addCmd(ElectricityBillCompositeCmd.INSTANCE);
    }

    /**
     * 向维护容器中 添加 命令实例
     *
     * @param cmd 命令实例
     */
    public static void addCmd(@NotNull Command cmd) {
        COMMANDS.add(cmd);
    }

    /**
     * 从维护容器中 获取 指定主指令名的 命令实例<br/>
     * 注：这个方法无法获取到 Mirai Console 的内建命令！
     *
     * @param primaryName 主指令名
     * @return 对应的命令实例，如果没有符合条件的则返回null
     */
    public static Command getCmd(@NotNull String primaryName) {
        return COMMANDS.stream()
                .filter(c -> c.getPrimaryName().equals(primaryName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 列出一个 指定命令实例的 详细信息
     *
     * @param cmd 命令实例
     * @return 其详细信息
     */
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

    /**
     * 列出维护容器中 所有命令实例的 详细信息
     *
     * @return 详细信息
     */
    public static String listAllCmd() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== listAllCmd =====\n");
        COMMANDS.forEach(c -> sb.append(detailCmd(c)).append("\n"));
        sb.append("===== listAllCmd =====\n");
        return sb.toString();
    }

    /**
     * （指令管理器）注册 维护容器中的 所有命令实例
     */
    public static void registerAllCmd() {
        COMMANDS.forEach(c -> {
            if(COMMAND_MANAGER.registerCommand(c, true)) {
                log.info("注册命令：{}", c.getPrimaryName());
            } else {
                log.info("注册命令失败：{}", c.getPrimaryName());
            }
        });
    }

    /**
     * （指令管理器）注销 维护容器中的 所有命令实例
     */
    public static void unregisterAllCmd() {
        COMMANDS.forEach(c -> {
            if(COMMAND_MANAGER.isCommandRegistered(c) && COMMAND_MANAGER.unregisterCommand(c)) {
                log.info("注销命令：{}", c.getPrimaryName());
            }
        });
    }

}
