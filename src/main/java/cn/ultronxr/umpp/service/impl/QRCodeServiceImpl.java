package cn.ultronxr.umpp.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeException;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.ultronxr.umpp.service.QRCodeService;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Ultronxr
 * @date 2022/07/08 10:33
 */
@Slf4j
public class QRCodeServiceImpl implements QRCodeService {

    @Override
    public ByteArrayOutputStream encode(String content) {
        // 默认二维码图片大小200px*200px
        int size = 200;
        // 根据内容长度切换二维码图片大小
        if(content.length() > 90) {
            size = 600;
        } else if(content.length() > 45) {
            size = 400;
        }

        QrConfig config = QrConfig.create()
                .setHeight(size).setWidth(size).setMargin(1)
                .setBackColor(Color.WHITE).setForeColor(Color.BLACK)
                .setCharset(StandardCharsets.UTF_8)
                .setErrorCorrection(ErrorCorrectionLevel.M);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            QrCodeUtil.generate(content, config, ImgUtil.IMAGE_TYPE_PNG, outputStream);
        } catch (Exception e) {
            log.error("生成二维码失败！");
            e.printStackTrace();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        return outputStream;
    }

    @Override
    public String decode(Image miraiImg) {
        String content = null;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new URL(Image.queryUrl(miraiImg)));
        } catch (IOException e) {
            content = "解析二维码失败：无法下载/读取图片！";
            log.error(content);
            e.printStackTrace();
            return content;
        }
        try {
            content = QrCodeUtil.decode(bufferedImage);
        } catch (QrCodeException e) {
            if(e.getCause() instanceof NotFoundException){
                content = "解析二维码失败：图片中未发现二维码！";
                log.error(content);
                //e.printStackTrace();
                return content;
            }
        }
        return content;
    }

}
