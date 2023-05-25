package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.service.QRCodeService;
import cn.ultronxr.umpp.service.impl.QRCodeServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Ultronxr
 * @date 2022/07/08 10:31
 *
 * 命令 —— 二维码
 */
public class QRCodeCmd extends JCompositeCommand {

    public static final QRCodeCmd INSTANCE = new QRCodeCmd();

    private final QRCodeService qrCodeService = new QRCodeServiceImpl();


    private QRCodeCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "qrcode");
        setDescription("二维码命令，生成或解析二维码");
        setPrefixOptional(false);
    }

    // >qrcode en <文本内容>
    @Description("生成二维码（若有特殊字符使用英文双引号\"\"括起）")
    @SubCommand("en")
    public void encode(CommandSender sender, @Name("文本内容") @NotNull String content) {
        ByteArrayOutputStream outputStream = qrCodeService.encode(content);
        if(outputStream != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            Image img = ExternalResource.uploadAsImage(inputStream, sender.getSubject());
            sender.sendMessage(img);
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        sender.sendMessage("生成二维码失败！");
    }

    // >qrcode de <二维码图片>
    @Description("扫描解析二维码内容（图片数量限一张，与命令在同一条消息中且紧跟于命令后）")
    @SubCommand("de")
    public void decode(CommandSender sender, @Name("二维码图片") @NotNull Image img) {
        String content = qrCodeService.decode(img);
        sender.sendMessage(content);
    }

}
