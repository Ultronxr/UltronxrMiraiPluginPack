package cn.ultronxr.umpp.eventHandler;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ultronxr
 * @date 2023/05/25 10:47:20
 * @description 群事件 - 群聊信息变更事件Handler，包括 群设置、群成员 等<br/>
 *              （这里的所有方法都针对群聊事件）<br/>
 *              <a href="https://github.com/mamoe/mirai/blob/dev/mirai-core-api/src/commonMain/kotlin/event/events/README.md#%E7%BE%A4">群事件</a>
 */
@Slf4j
public class GroupEventHandler {

    public static final GroupEventHandler INSTANCE = new GroupEventHandler();

    private static final String GROUP_NEW_MEMBER_JOIN_JPG_FILEPATH = "img/groupNewMemberJoin.jpg";


    /**
     * 群成员入群事件处理器，区分为“受邀入群”、“主动入群”两种情况
     *
     * @param memberJoinEvent 群成员入群事件
     */
    public void onMemberJoin(MemberJoinEvent memberJoinEvent){
        // 受邀入群 与 主动入群
        if(memberJoinEvent instanceof MemberJoinEvent.Invite){
            this.memberJoinInvite((MemberJoinEvent.Invite) memberJoinEvent);
        } else if(memberJoinEvent instanceof MemberJoinEvent.Active){
            this.memberJoinActive((MemberJoinEvent.Active) memberJoinEvent);
        }
    }

    /**
     * 群成员退群事件处理器，区分为“被踢退群”、“主动退群”两种情况
     *
     * @param memberLeaveEvent 群成员退群事件
     */
    public void onMemberLeave(MemberLeaveEvent memberLeaveEvent){
        // 被踢退群 与 主动退群
        if(memberLeaveEvent instanceof MemberLeaveEvent.Kick){
            this.memberLeaveKick((MemberLeaveEvent.Kick) memberLeaveEvent);
        } else if(memberLeaveEvent instanceof MemberLeaveEvent.Quit){
            this.memberLeaveQuit((MemberLeaveEvent.Quit) memberLeaveEvent);
        }
    }

    /**
     * 新成员被邀请入群事件处理器
     *
     * @param memberJoinInviteEvent 新成员被邀请入群事件
     */
    public void memberJoinInvite(MemberJoinEvent.Invite memberJoinInviteEvent) {
        Member member = memberJoinInviteEvent.getMember(),
                invitor = memberJoinInviteEvent.getInvitor();
        log.info("[function] 新成员受邀请入群事件。新成员信息：QQ {} ，昵称 {} ；邀请人信息：QQ {} ，昵称 {}",
                member.getId(), member.getNick(), invitor.getId(), invitor.getNick());

        defaultMemberJoinAction(memberJoinInviteEvent, member.getId());
    }

    /**
     * 新成员主动入群事件处理器
     *
     * @param memberJoinActiveEvent 新成员主动入群事件
     */
    public void memberJoinActive(MemberJoinEvent.Active memberJoinActiveEvent) {
        Member member = memberJoinActiveEvent.getMember();
        log.info("[function] 新成员主动入群事件。新成员信息：QQ {} ，昵称 {}", member.getId(), member.getNick());

        defaultMemberJoinAction(memberJoinActiveEvent, member.getId());
    }

    /**
     * 默认情况下新成员入群都调用这个方法
     * 如有其它需要，自行修改上面的方法 memberJoinXxx() 方法
     *
     * @param memberJoinEvent 新成员入群事件
     * @param newMemberId     新成员QQ号
     */
    private void defaultMemberJoinAction(MemberJoinEvent memberJoinEvent, Long newMemberId){
        Image image = null;
        try {
            InputStream inputStream = GroupEventHandler.class.getClassLoader().getResourceAsStream(GROUP_NEW_MEMBER_JOIN_JPG_FILEPATH);
            ExternalResource externalResource = ExternalResource.create(inputStream);
            image = memberJoinEvent.getGroup().uploadImage(externalResource);
            inputStream.close();
            externalResource.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.warn("[function] 新成员入群提示图片获取失败！");
        }

        MessageChainBuilder msgChainBuilder = new MessageChainBuilder()
                .append(new At(newMemberId))
                .append(" 欢迎新成员入群，请先阅读群内公告。");
        if(null != image){
            msgChainBuilder.append(image);
        }
        MessageChain msgChain = msgChainBuilder.build();

        memberJoinEvent.getGroup().sendMessage(msgChain);
        log.info("[msg-send] {}", msgChain.contentToString());
    }

    /**
     * 群成员被踢退群事件处理器
     *
     * @param memberLeaveKickEvent 群成员被踢退群事件
     */
    public void memberLeaveKick(MemberLeaveEvent.Kick memberLeaveKickEvent) {
        Member member = memberLeaveKickEvent.getMember(),
                operator = memberLeaveKickEvent.getOperator();
        String leaveInfo = "退群成员信息：QQ " + member.getId() + " ，昵称 " + member.getNick() + " ；操作人信息：QQ "
                + operator.getId() + " ，昵称 " + operator.getNick();
        String msg = "群成员被踢：" + leaveInfo;
        log.info("[function] 群成员被踢退群事件。" + leaveInfo);

        memberLeaveKickEvent.getGroup().sendMessage(msg);
        log.info("[msg-send] {}", msg);
    }

    /**
     * 群成员主动退群事件处理器
     *
     * @param memberLeaveQuitEvent 群成员主动退群事件
     */
    public void memberLeaveQuit(MemberLeaveEvent.Quit memberLeaveQuitEvent) {
        Member member = memberLeaveQuitEvent.getMember();
        String leaveInfo = "退群成员信息：QQ " + member.getId() + " ，昵称 " + member.getNick();
        String msg = "群成员主动退群：" + leaveInfo;
        log.info("[function] 群成员主动退群事件。" + leaveInfo);

        memberLeaveQuitEvent.getGroup().sendMessage(msg);
        log.info("[msg-send] {}", msg);
    }

}
