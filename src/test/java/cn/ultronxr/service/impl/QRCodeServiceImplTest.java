package cn.ultronxr.service.impl;

import cn.ultronxr.service.QRCodeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ultronxr
 * @date 2022/07/08 11:08
 */
@Slf4j
class QRCodeServiceImplTest {

    public static void main(String[] args) {
        QRCodeService qrCodeService = new QRCodeServiceImpl();
        qrCodeService.encode("123" +
                "456" +
                "789");
    }

}