package cn.ultronxr.umpp.command;

import cn.ultronxr.umpp.UltronxrMiraiPluginPack;
import cn.ultronxr.umpp.eventHandler.GroupEventHandler;
import cn.ultronxr.umpp.service.LegendsOfRuneterraPOCService;
import cn.ultronxr.umpp.service.impl.LegendsOfRuneterraPOCServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JCompositeCommand;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ultronxr
 * @date 2023/12/13 14:51:15
 * @description 命令 —— LOR POC 英雄之路相关
 */
@Slf4j
public class LORPOCCmd extends JCompositeCommand {

    public static final LORPOCCmd INSTANCE = new LORPOCCmd();

    private final LegendsOfRuneterraPOCService lorPOCService = new LegendsOfRuneterraPOCServiceImpl();

    private static final String CHAMPION_LEVEL_UP_TOTAL_EXP_IMG_FILEPATH = "img/LOR/POCChampionLevelUpTotalExp.png",
                                EXP_GAIN_IN_ADVENTURE = "img/LOR/POCExpGainInAdventure.png";

    private LORPOCCmd() {
        super(UltronxrMiraiPluginPack.INSTANCE, "lorpoc");
        setDescription("LOR POC 英雄之路相关命令");
        setPrefixOptional(false);
    }

    // >lorpoc exp2Level <当前经验值> <冒险难度星级> <是否是周常> <携带几倍的经验宝珠(0/5/10)>
    @Description("使用经验查询英雄等级")
    @SubCommand("exp2Level")
    public void exp2Level(CommandSender sender,
                          @Name("当前经验值") @NotNull Integer exp,
                          @Name("冒险难度星级") @NotNull String adventureDifficultyStar,
                          @Name("是否是周常") @NotNull Boolean isWeekly,
                          @Name("携带几倍的经验宝珠(0/5/10)") @NotNull Integer multiplier) {
        LegendsOfRuneterraPOCService.CalResult
                levelNow = lorPOCService.calChampLevelByTotalExp(exp),

                gainExp = lorPOCService.calExpGainInAdventure(adventureDifficultyStar, isWeekly, false, multiplier),
                level = lorPOCService.calChampLevelByGainExp(exp, gainExp.getResult()),

                gainExpFirst3 = lorPOCService.calExpGainInAdventure(adventureDifficultyStar, isWeekly, true, multiplier),
                levelFirst3 = lorPOCService.calChampLevelByGainExp(exp, gainExpFirst3.getResult());

        sender.sendMessage(
                "当前英雄等级：【" + unboxingCalResult(levelNow) + "】\n"
                + "通关该冒险后，将会获得经验值：【" + unboxingCalResult(gainExp) + "】，英雄升级到等级：【" + unboxingCalResult(level) + "】\n"
                + "只打前3关，将会获得经验值：【" + unboxingCalResult(gainExpFirst3) + "】，英雄升级到等级：【" + unboxingCalResult(levelFirst3) + "】"
        );
    }

    // >lorpoc needExp <当前经验值> <目标等级>
    @Description("查询升级到指定等级所需的经验值")
    @SubCommand("needExp")
    public void needExp(CommandSender sender,
                          @Name("当前经验值") @NotNull Integer exp,
                          @Name("目标等级") @NotNull Integer targetLevel) {
        LegendsOfRuneterraPOCService.CalResult
                levelNow = lorPOCService.calChampLevelByTotalExp(exp),
                expNeeded2LevelUp = lorPOCService.calExpNeeded2LevelUp(exp, targetLevel);

        sender.sendMessage(
                "当前英雄等级：【" + unboxingCalResult(levelNow) + "】\n"
                + "升级到目标等级 " + targetLevel + "，所需经验值【" + unboxingCalResult(expNeeded2LevelUp) + "】"
        );
    }

    // >lorpoc exp2LevelImg
    @Description("英雄总经验值-等级对照表")
    @SubCommand("exp2LevelImg")
    public void exp2LevelImg(CommandSender sender) {
        sendMsgFromImgStream(CHAMPION_LEVEL_UP_TOTAL_EXP_IMG_FILEPATH, sender);
    }

    // >lorpoc needExpImg
    @Description("冒险关卡的获取经验值表")
    @SubCommand("needExpImg")
    public void needExpImg(CommandSender sender) {
        sendMsgFromImgStream(EXP_GAIN_IN_ADVENTURE, sender);
    }

    private String unboxingCalResult(LegendsOfRuneterraPOCService.CalResult calResult) {
        if(calResult.getCode() == 0) {
            return String.valueOf(calResult.getResult());
        }
        return calResult.getMsg();
    }

    private void sendMsgFromImgStream(String imgFilePath, CommandSender sender) {
        Image image = null;
        try {
            InputStream inputStream = GroupEventHandler.class.getClassLoader().getResourceAsStream(imgFilePath);
            ExternalResource externalResource = ExternalResource.create(inputStream);
            image = sender.getSubject().uploadImage(externalResource);
            sender.sendMessage(image);
            inputStream.close();
            externalResource.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.warn("[function] 图片读取失败！");
        }
    }

}
