package cn.ultronxr.service;

/**
 * @author Ultronxr
 * @date 2022/07/04 15:35
 *
 * 服务 —— 发言（彩虹屁、朋友圈语录、毒鸡汤、口吐芬芳（废弃）、火力全开（废弃））
 */
public interface SayService {

    /**
     * 彩虹屁
     */
    String sayCHP();

    /**
     * 朋友圈语录
     */
    String sayPYQ();

    /**
     * 毒鸡汤
     */
    String sayDJT();

    /**
     * 口吐芬芳（废弃）
     */
    @Deprecated
    String sayKTFF();

    /**
     * 火力全开（废弃）
     */
    @Deprecated
    String sayHLQK();

}
