package cn.ultronxr.umpp.command.codeToolkit;

import cn.hutool.core.text.UnicodeUtil;
import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2023/05/25 18:41:20
 * @description 命令 —— Unicode 编解码
 */
public class UnicodeCmd extends JCompositeCommand {

    public static final UnicodeCmd INSTANCE = new UnicodeCmd();

    private UnicodeCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "unicode", "uni");
        setDescription("Unicode编/解码命令");
        setPrefixOptional(false);
    }

    // >uni en <文本内容>
    @Description("Unicode编码（若有特殊字符使用英文双引号\"\"括起）")
    @SubCommand("en")
    public void encode(CommandSender sender, @Name("文本内容") @NotNull String plainText) {
        sender.sendMessage(UnicodeUtil.toUnicode(plainText, false));
    }

    // >uni de <文本内容>
    @Description("Unicode解码（必须使用英文双引号\"\"括起）")
    @SubCommand("de")
    public void decode(CommandSender sender, @Name("文本内容") @NotNull String plainText) {
        String result = "解码失败！";
        try {
            result = UnicodeUtil.toString(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.sendMessage(result);
    }

}
