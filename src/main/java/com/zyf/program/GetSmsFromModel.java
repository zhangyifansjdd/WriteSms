package com.zyf.program;

import com.zyf.sms.SmsCollector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangYifan on 2017/7/24.
 */
public class GetSmsFromModel {
    public static void main(String[] args) {
        SmsCollector smsCollector = new SmsCollector();
        smsCollector.addSmsContentFile("G:\\SmsContet\\所有原文.xlsx", 0, 0);
        smsCollector.addSmsContentFile("G:\\SmsContet\\去噪前文档.xlsx", 0, 0);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\1.xlsx", 0, 3);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\2.xlsx", 0, 2);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\3.xlsx", 1, 1);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\4.xlsx", 0, 1);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\5.xlsx", 0, 1);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\1227-108收集短信原文.xlsx", 0, 1);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\短信收集整理20170117（原始）.xlsx", 0, 1);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\0628导出活动收集短信30万.xlsx", 0, 3);

        System.out.println("读取完成，开始匹配");

        List<String> models = new ArrayList<>();
        List<String> yuanwenList = new ArrayList<>();

//        models.add("【小米】${txt_验证码}（小米帐号安全验证，用于验证身份修改密码密保等，请勿将验证码透露给他人）");
//        models.add("您尾号${txt_尾号}卡18:25人民币活期代发转入${txt_金额},活期余额6,350.52。【浦发银行】");

        models.add("【去哪儿网】您有一张${txt_发车时间}从${txt_出发地}开往${txt_目的地}的火车票，登录客户端查看订单详情${txt_查看订单}");
        models.add("【同程旅游】紧急通知：您预订的${txt_出发地}-${txt_目的地}北${txt_车次}火车票，请在${txt_提示}。");
        models.add("【铁路客服】订单${txt_订单号},${txt_姓名}您已变更${txt_>日_发车时间}日${txt_>次_车次}次${txt_>铺_座号}铺${txt_出发地}开。");
        models.add("【铁路客服】订单${txt_订单号},${txt_姓名}您已签${txt_>日_发车时间}日${txt_>次_车次}次${txt_>铺_座号}铺${txt_出发地}开。");
        models.add("订单号${txt_订单号}，${txt_姓名}您已购${txt_>日_发车时间}日${txt_>次_车次}次${txt_>铺_座号}铺${txt_出发地}开。${txt_提示}。【铁路客服】");
        models.add("【铁路客服】订单${txt_订单号},${txt_姓名}您已购${txt_>次_车次}次${txt_>铺_座号}铺${txt_出发地}开,检票口${txt_检票口}。");
        models.add("客官，捷报，${txt_出发地}-${txt_目的地}02月${txt_发车时间}开${txt_车次}火车票${txt_状态}，${txt_姓名}某${txt_座号}（${txt_座别}），${txt_提示}，取票号${txt_取票号}。若在火车站窗口办理退票操作，请致电4007-777777转7协助退款。【同程旅游】");
        models.add("【高铁管家】您的火车票${txt_状态},订单号${txt_订单号},${txt_发车日期}日${txt_车次}次${txt_发车时间},${txt_座号}。");
        models.add("【铁路客服】订单${txt_订单号},${txt_姓名}您已购${txt_>日_发车时间}日${txt_>次_车次}次${txt_>铺_座号}铺${txt_出发地}开。${txt_提示}。");

        models.add("航旅纵横提醒您关注新的行程：${txt_出发时间}，${txt_出发机场}-${txt_目的机场}，${txt_航班号}航班。感谢您使用航旅纵横【中国民航信息】");
        models.add("您所乘坐的航班${txt_航班号}已经${txt_航班状态}，请立刻前往${txt_登机口}登机口登机。【飞常准】");
        models.add("【春秋航空】春秋航空很抱歉的通知您,因${txt_延误原因}原因,您乘坐的${txt_出发时间},${txt_航班号},预计延误${txt_延误时长},给您带来不便,敬请谅解!${txt_提示}");
        models.add("【同程旅游】${txt_航班号}航班的旅客,请前往${txt_登机口}登机口登机(供参考),${txt_提示}");
        models.add("【同程旅游】尊敬的旅客，您的${txt_航班号}，${txt_姓名}已${txt_状态}，${txt_出发日期}请${txt_提示}，同程旅游，快乐每一程！${txt_ignore}");
        models.add("【中国国航】国航小秘书温馨提示：${txt_航班号}/${txt_出发机场}至${txt_目的机场}/${txt_出发时间}/总票款${txt_票款}/保险费${txt_保险费}/票号${txt_票号}/${txt_提示}，如有疑问请致电${txt_电话}。登录国航无线管理您的行程m.airchina.com");
        models.add("【东方航空】从${txt_出发地}到${txt_目的地}，${txt_航班号}航班，起飞时间：${txt_出发时间}；乘客${txt_姓名}，订单号:${txt_订单号}，${txt_提示}。${txt_ignore}");
        models.add("【中国民航信息】${txt_姓名}/(${txt_航空公司})${txt_航班号}/${txt_出发日期}/${txt_出发地}-${txt_目的地}，${txt_提示}");
        models.add("【携程网】${txt_状态}：订单${txt_订单号}『${txt_航空公司}（实际承运航班${txt_航班号}，请至${txt_值机地址}办理值机）${txt_出发地}-${txt_目的地}，票号${txt_票号}』请${txt_值机时间}至机场值机。${txt_提示}。可在${txt_ignore}");
        models.add("【去哪儿网】您购买的${txt_出发地}到${txt_目的地}的航班${txt_航班号}：${txt_出发日期}，${txt_出发时间}，订单${txt_订单号}已${txt_状态}。如支付后出现代理商不出票、加价出票、机票无法使用，请您立即拨打${txt_提示}进行投诉，担保通全程预订保障，去哪儿都放心。");

        models.add("恭喜您${txt_状态}，请凭出票码到${txt_取票地址}。取票码:${txt_取票码}，影院:${txt_影厅}，影片:${txt_影片名称}，放映时间:${txt_观影时间}，祝您观影愉快！客服电话${txt_联系电话}【百度糯米电影】");
        models.add("【百度】您已${txt_状态}。请凭取票码${txt_取票码}到${txt_取票地址}取票。影票信息：${txt_观影时间},${txt_影片名称},${txt_影厅},${txt_座位}。如有疑问请拨客服电话${txt_联系电话}。");
        models.add("【猫眼电影】${txt_状态}。开场前凭验证码${txt_验证码}到${txt_取票地址}取票。${txt_观影时间}华夏国际影城(鲁广店)《${txt_影片名称}》${txt_座位}，客服${txt_联系电话}");
        models.add("恭喜您${txt_状态}，请凭取票号:${txt_取票码}，到${txt_取票地址}。影院:${txt_影厅}，影片:${txt_影片名称}，放映时间:${txt_观影时间}，祝您观影愉快！客服电话${txt_联系电话}【百度糯米电影】");
        models.add("【腾讯科技】${txt_观影时间}大地影院—孝感中商百货${txt_影片名称},${txt_座位}已买,凭取票码${txt_取票码}到${txt_取票地址}兑换取票「QQ电影票|微票儿」回T退订");

        models.add("【中国移动139邮箱】发件人：${txt_发件人}主题：${txt_邮件标题}正文：${txt_邮件内容}查看：${txt_ignore}");
        models.add("【中国移动139邮箱】发件人：${txt_发件人}主题：${txt_邮件标题}正文：${txt_邮件内容}[使用客户端查看：${txt_使用客户端查看}，回${txt_ignore}]");
        models.add("【中国移动139邮箱】发件人：${txt_发件人}主题：${txt_邮件标题}附件:${txt_附件}正文：${txt_邮件内容}查看：${txt_查看}【回Q关闭通知，回复按标准资费计算】");
        models.add("新邮件提醒,发件人:${txt_发件人},主题:${txt_邮件标题}");
        models.add("您有来自${txt_发件人}的新OA邮件主题：${txt_邮件标题}20日凌晨测试人员：${txt_测试人}（${txt_联系电话}）。");
        models.add("您有来自${txt_发件人}的新OA邮件主题：${txt_邮件标题}。附件数:${txt_附件数}。");
        models.add("你有来自OA<${txt_发件人}>的邮件(${txt_邮件标题}):${txt_邮件内容}");
        models.add("【腾讯科技】【${txt_邮件标题}】发件人：${txt_发件人}");
        models.add("【网易】${txt_发件人}向${txt_收件人}发来邮件，${txt_邮件内容}。回复TD退订");

        models.add("【京东钱包】校验码：${txt_校验码}，您正${txt_用途}，${txt_提示}哦！");
        models.add("【广东南粤银行】尊敬的客户:您${txt_用途}，验证码：${txt_验证码}，有效期${txt_有效期}，感谢您的参与!");
        models.add("【广州百田】验证码：${txt_验证码}，该验证码${txt_用途}，请在${txt_有效期}输入。${txt_提示}");
        models.add("您正${txt_用途}，手机交易码${txt_交易码}。${txt_提示}，请慎重操作！【中国银行】");
        models.add("【世纪龙】手机登录动态密码：{txt_动态密码}，{txt_有效期}有效，${txt_提示}，否则将导致帐号被盗或财产损失。");



        for (String model : models) {
            String yuanwen = smsCollector.getSmsContentFromModel(model);
            if (yuanwen != null && yuanwen.length() > 0) {
                System.out.println(yuanwen);
            } else {
                System.out.println("--------没匹配到-->"+model);
            }
        }

        System.out.println("结束");
    }
}
