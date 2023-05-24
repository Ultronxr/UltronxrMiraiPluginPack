package cn.ultronxr.command;

import cn.ultronxr.UltronxrMiraiPluginPack;
import cn.ultronxr.bean.CommandInstance;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.command.Command;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Ultronxr
 * @date 2022/07/05 15:37
 *
 * 命令 —— 维护 UMPP 命令的命令，请避免将本命令的执行权限下放到普通用户
 */
@Slf4j
public class CmdHelperCmd extends JCompositeCommand {

    public static final CmdHelperCmd INSTANCE = new CmdHelperCmd();

    private static final InputStream GENERATE_PERMISSION_PERMIT_HELP_INPUT_STREAM =
            CmdHelperCmd.class.getClassLoader().getResourceAsStream("img/generatePermissionPermitHelp.png");


    private CmdHelperCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "cmdhelper", "cp");
        setDescription("维护 UMPP 命令的命令，请避免将本命令的执行权限下放到普通用户");
        setPrefixOptional(false);
    }

    // >cmdhelper detail <主指令名>
    @Description("列出 UMPP 某个命令的详细信息")
    @SubCommand
    public void detail(CommandSender sender, @Name("主指令名") String primaryName) {
        Command cmd = CommandInstance.getCmd(primaryName);
        if(cmd == null) {
            sender.sendMessage("无匹配命令！");
            return;
        }
        sender.sendMessage(CommandInstance.detailCmd(cmd));
    }

    // >cmdhelper list
    @Description("列出 UMPP 所有命令的详细信息")
    @SubCommand
    public void list(CommandSender sender) {
        sender.sendMessage(CommandInstance.listAllCmd());
    }

    // >cmdhelper register <主指令名>
    @Description("注册命令")
    @SubCommand({"register", "reg"})
    public void register(CommandSender sender, @Name("主指令名") String primaryName) {
        Command cmd = CommandInstance.getCmd(primaryName);
        if(cmd == null) {
            sender.sendMessage("无匹配命令！");
            return;
        }
        if(CommandInstance.COMMAND_MANAGER.isCommandRegistered(cmd)) {
            sender.sendMessage("注册命令失败！该命令已注册！");
            return;
        }
        if(CommandInstance.COMMAND_MANAGER.registerCommand(cmd, true)) {
            sender.sendMessage("注册命令完成。");
            log.info("注册命令完成 - {}", primaryName);
            return;
        }
        sender.sendMessage("注册命令失败！命令冲突！");
    }

    // >cmdhelper unregister <主指令名>
    @Description("注销命令")
    @SubCommand({"unregister", "unreg"})
    public void unregister(CommandSender sender, @Name("主指令名") String primaryName) {
        // 特判 cmdhelper 本身，禁止动态注销
        if(primaryName.equals(INSTANCE.getPrimaryName())) {
            sender.sendMessage(INSTANCE.getPrimaryName() + " 不允许注销！");
            return;
        }
        Command cmd = CommandInstance.getCmd(primaryName);
        if(cmd == null) {
            sender.sendMessage("无匹配命令！");
            return;
        }
        if(CommandInstance.COMMAND_MANAGER.unregisterCommand(cmd)) {
            sender.sendMessage("注销命令完成。");
            log.info("注销命令完成 - {}", primaryName);
            return;
        }
        sender.sendMessage("注销命令失败！命令未注册！");
    }

    // >cmdhelper generatePermissionPermitForMember <主指令名> <群成员>
    @Description("生成向群成员授权命令的语句")
    @SubCommand({"generatePermissionPermitForMember", "gppM"})
    public void generatePermissionPermitForMember(CommandSender sender, @Name("主指令名") String primaryName, @Name("群成员") Member member) {
        Command cmd = CommandInstance.getCmd(primaryName);
        if(cmd == null) {
            sender.sendMessage("无匹配命令！");
            return;
        }

        StringBuilder sb = new StringBuilder();
        String groupId = String.valueOf(member.getGroup().getId()),
                memberId = String.valueOf(member.getId());
        sb.append(">perm permit")
                .append(" m").append(groupId).append(".").append(memberId)
                .append(" ").append(cmd.getPermission().getId())
                .append("\n");
        sender.sendMessage(sb.toString());
    }

    // >cmdhelper generatePermissionPermitHelp
    @Description("获取授权命令的帮助")
    @SubCommand({"generatePermissionPermitHelp", "gppHELP"})
    public void generatePermissionPermitHelp(CommandSender sender) {
        String originalUrl = "https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/Permissions.md#%E5%AD%97%E7%AC%A6%E4%B8%B2%E8%A1%A8%E7%A4%BA";
        //Image img = ExternalResource.uploadAsImage(GENERATE_PERMISSION_PERMIT_HELP_INPUT_STREAM, sender.getSubject());
        InputStream inputStream = CmdHelperCmd.class.getClassLoader().getResourceAsStream("img/generatePermissionPermitHelp.png");
        Image img = ExternalResource.uploadAsImage(
                Objects.requireNonNull(inputStream),
                Objects.requireNonNull(sender.getSubject())
        );
        MessageChain msgChain = new MessageChainBuilder().append(originalUrl).append(img).build();
        sender.sendMessage(msgChain);
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
