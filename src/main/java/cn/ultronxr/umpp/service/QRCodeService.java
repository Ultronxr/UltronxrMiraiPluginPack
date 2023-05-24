package cn.ultronxr.umpp.service;

import net.mamoe.mirai.message.data.Image;

import java.io.ByteArrayOutputStream;

/**
 * @author Ultronxr
 * @date 2022/07/08 10:33
 *
 * 服务 —— 二维码
 */
public interface QRCodeService {

    /**
     * 生成二维码
     *
     * @param content 待生成二维码包含的内容
     * @return 生成的二维码图片的字节数组输出流 <br/>
     *         需要使用 {@code new ByteArrayInputStream(outputStream.toByteArray())} 转换成字节数组输入流， <br/>
     *         再用 {@code ExternalResource.uploadAsImage()} 上传图片。
     */
    ByteArrayOutputStream encode(String content);

    /**
     * 解析二维码
     *
     * @param miraiImg mirai图片数据类型 {@link Image}
     * @return 解析结果 <br/>
     *         注：有可能返回“解析二维码失败”内容的情况： <br/>
     *              1. 下载/读取图片失败； <br/>
     *              2. 图片中没有二维码。
     */
    String decode(Image miraiImg);

}
