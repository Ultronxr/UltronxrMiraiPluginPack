package cn.ultronxr.umpp.service.impl;

import cn.hutool.core.text.UnicodeUtil;
import cn.ultronxr.umpp.service.SayService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ultronxr
 * @date 2022/07/05 16:18
 */
@Slf4j
class SayServiceImplTest {

    public static void main(String[] args) {
        SayService sayService = new SayServiceImpl();
        log.debug("{}", UnicodeUtil.toString(sayService.sayCHP()));
    }

}