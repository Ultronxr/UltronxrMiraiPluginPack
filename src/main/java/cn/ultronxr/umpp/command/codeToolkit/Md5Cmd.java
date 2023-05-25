package cn.ultronxr.umpp.command.codeToolkit;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.util.MD5Utils;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2023/05/25 18:33:24
 * @description 命令 —— MD5
 */
public class Md5Cmd extends JSimpleCommand {

    public static final Md5Cmd INSTANCE = new Md5Cmd();

    private Md5Cmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "md5");
        setDescription("生成32位MD5结果（若有特殊字符使用英文双引号\"\"括起）");
        setPrefixOptional(false);
    }

    // >md5 <文本内容>
    @Handler
    public void onCommand(CommandSender sender, @Name("文本内容") @NotNull String plainText) {
        sender.sendMessage(MD5Utils.md5(plainText));
    }

    // >md5 <文本内容> <盐>
    @Handler
    public void onCommand(CommandSender sender, @Name("文本内容") @NotNull String plainText, @Name("盐值") @NotNull String salt) {
        sender.sendMessage(MD5Utils.md5WithSalt(plainText, salt));
    }

}
