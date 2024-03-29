package cn.ultronxr.umpp.service;

import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author Ultronxr
 * @date 2021/04/29 12:03
 * @description 有关图片、图像处理的Service
 */
public interface ImgService {

    /**
     * 生成PornHub图标样式的图片
     * 如果需要输出成图片文件，请使用 {@code ImageIO.write(bufferedImage, "png", new File("new.png"));}
     *
     * 这个方法使用Java awt绘制图片，如果需要更完美的解决方案，请访问：
     * {@link "https://www.logoly.pro/"}
     *
     * @param prefixText 前缀文字内容（黑底白字）
     * @param suffixText 后缀文字内容（黄底黑字）
     */
    BufferedImage createPornHubIconImg(@NotNull String prefixText, @NotNull String suffixText);

    /**
     * 生成PornHub图标样式的图片输入流
     *
     * @param prefixText 前缀文字内容（黑底白字）
     * @param suffixText 后缀文字内容（黄底黑字）
     */
    InputStream createPornHubIconImgInputStream(@NotNull String prefixText, @NotNull String suffixText);

}
