package cn.ultronxr.umpp.command.codeToolkit;

import cn.hutool.core.util.URLUtil;
import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

/**
 * @author Ultronxr
 * @date 2023/05/25 18:38:53
 * @description 命令 —— Url 编解码
 */
public class UrlCmd extends JCompositeCommand {

    public static final UrlCmd INSTANCE = new UrlCmd();

    private UrlCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "url");
        setDescription("URL编/解码命令，转义查询字符串中的特殊字符（UTF-8编码）");
        setPrefixOptional(false);
    }

    // >url en <文本内容>
    @Description("URL编码（若有特殊字符使用英文双引号\"\"括起）")
    @SubCommand("en")
    public void encode(CommandSender sender, @Name("文本内容") @NotNull String plainText) {
        sender.sendMessage(URLUtil.encodeQuery(plainText, StandardCharsets.UTF_8));
    }

    // >url de <文本内容>
    @Description("URL解码（若有特殊字符使用英文双引号\"\"括起）")
    @SubCommand("de")
    public void decode(CommandSender sender, @Name("文本内容") @NotNull String plainText) {
        sender.sendMessage(URLUtil.decode(plainText, StandardCharsets.UTF_8));
    }

}
