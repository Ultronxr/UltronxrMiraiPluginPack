package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.service.ImgService;
import cn.ultronxr.umpp.service.impl.ImgServiceImpl;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ultronxr
 * @date 2023/05/25 11:55:08
 * @description 命令 —— 图片相关
 */
public class ImgCmd extends JCompositeCommand {

    public static final ImgCmd INSTANCE = new ImgCmd();

    private final ImgService imgService = new ImgServiceImpl();

    private ImgCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "img");
        setDescription("图片处理相关命令");
        setPrefixOptional(false);
    }

    // >img ph <前缀文本> <后缀文本>
    @Description("生成PxxnHxb图标样式的图片")
    @SubCommand
    public void ph(CommandSender sender, @Name("前缀文本") @NotNull String prefix, @Name("后缀文本") @NotNull String suffix) {
        try {
            InputStream inputStream = imgService.createPornHubIconImgInputStream(prefix, suffix);
            ExternalResource externalResource = ExternalResource.create(inputStream);
            Image image = sender.getSubject().uploadImage(externalResource);
            sender.sendMessage(image);
            externalResource.close();
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
