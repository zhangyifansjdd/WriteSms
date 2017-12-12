package com.zyf.program;

import com.zyf.excel.ExcelReader;
import com.zyf.excel.ExcelWriter;
import com.zyf.sms.SmsCollector;

import java.io.File;

/**
 * Created by ZhangYifan on 2017/8/11.
 */
public class AllModel {
    public static void main(String[] args) {
//        161614848

        SmsCollector smsCollector = new SmsCollector();
//        smsCollector.addSmsContentFile("G:\\SmsContet\\所有原文2.xlsx", 0, 0);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\忽略标点匹配\\合并所有原文.xlsx", 0, 0);
//        smsCollector.addSmsContentFile("G:\\SmsContet\\第四批\\第三批后未匹配出.xlsx", 0, 8);
        smsCollector.addSmsContentFile("G:\\SmsContet\\原文加端口号\\整理\\out_all-quzaohou0.xls", 0,0,1);
        smsCollector.addSmsContentFile("G:\\SmsContet\\原文加端口号\\整理\\out_all-quzaohou1.xls", 0,0,1);
        smsCollector.addSmsContentFile("G:\\SmsContet\\原文加端口号\\整理\\out_all-quzaohou2.xls", 0,0,1);
        smsCollector.addSmsContentFile("G:\\SmsContet\\原文加端口号\\整理\\out_all-quzaohou3.xls", 0,0,1);
        smsCollector.addSmsContentFile("G:\\SmsContet\\原文加端口号\\整理\\out_all-quzaohou4.xls", 0,0,1);

        smsCollector.prepare();

        ExcelReader excelReader = new ExcelReader(new File("G:\\SmsContet\\新全量模版\\9.06模板总表.xlsx"));
        System.out.println("读取完成，开始匹配");
        excelReader.moveToPrevious();
        int i = 0;
        long totalTime = 0;
        while (excelReader.moveToNext()) {
            boolean skip=false;
            long time1 = System.currentTimeMillis();
            String content = null;
            if (!skip) {
                String port=excelReader.getStringByIndex(1);
                String model = excelReader.getStringByIndex(4);
                content = smsCollector.getSmsContentFromModel(port,model);
                excelReader.getCurrentRow().add(content);
            }
            long time2 = System.currentTimeMillis();
            i++;
            totalTime = totalTime + time2 - time1;
            long averageTime = totalTime / i;
            float shengyu = 1F * averageTime * (excelReader.getRowCount() - i) / 1000 / 60;
            System.out.println("预计剩余-->" + shengyu + "分钟    |原文--->" + content);
        }

        ExcelWriter.writeDataToExcelFile("G:\\SmsContet\\新全量模版\\9.06模板总表-结果.xls", excelReader.getData());

//        List<String> models=new ArrayList<>();
//        models.add("尊敬的全球通客户：您好，03月09日您成功充值1.00元，当前您的充值账户余额为118.00元。查费缴费请登录浙江移动手机营业厅 http://dx.10086.cn/sdtxcz 。【中国移动】");
//        models.add("截至19日11时57分，您本月您实际使用总流量3.76MB，其中3G(TD)流量0.00MB，其中4G(TD_LTE)流量3.76MB，各流量套餐具体使用情况如下：综合VPMN套餐 石化专用、10M本地通用流量、本地流量优惠包10M（营销活动专用，需审批）、官微专用本地流量（1M1月），共包含本地通用流量121.00MB，已使用3.76MB，剩余117.24MB；如需变更为其他移动数据流量套餐请在3分钟内直接回复Y。【中国移动】");
//        models.add("您好，您fghfgh浦发信用卡查询密码已设置成功，该密码用于自助语音服务系统及在线账户服务的登录。温馨提醒，请您妥善保管密码，切勿告知他人！网银用卡安全不容忽视，一键定制您的专属防盗刷服务，戳http://dwz.cn/2Agesa【浦发银行】");
//        models.add("流量提醒：您本月的套餐外优惠省内流量共有100.00MB，截至26日14时45分，已使用100%。安徽移动手机营业厅，轻松查流量、话费、详单，优惠交话费、加流量、换套餐，详点 ah.10086.cn/dt/khd");
//        for (String model : models) {
//            String s=smsCollector.getSmsContentFromModelMutilThread(model);
//            System.out.println("结果："+s);
//        }

        System.out.println("----------结束");
    }
}
