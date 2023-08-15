package com.txxy.demo.schedule;

import com.txxy.demo.attendance.Attendance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

/**
 * @author zengch
 * @date 2021-08-04 11:45
 */
@Component
public class AttendanceScheduleJob {

    @Resource
    private Attendance attendance;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;

    //MON,TUES,WED,THU,FRI,SAT,SUN
//    @Scheduled(cron = "0 0 8 ? * FRI,SUN")
//    @Scheduled(cron = "0 0 8 ? * MON-SAT")
//    @Scheduled(cron = "0/10 * * * * *")
    public void attendanceSignIn() throws MessagingException {
        String originStr = "th nowrap style=\"text-align: center;\"";
        String afterStr = "th nowrap style=\"text-align: center;width: 200px;\"";
        //随机睡眠60秒内
        randomSleep();
        String signInResult = attendance.dutySignIn();
        Document document = Jsoup.parse(signInResult);
        String table = document.select("table").toString();
        table = table.replace(originStr, afterStr);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("天下信用上班打卡结果反馈");
        helper.setFrom("920253445@qq.com");
        helper.setTo("zengchao237@163.com");
        helper.setSentDate(new Date());

        Context context = new Context();
        context.setVariable("table", table);
        context.setVariable("date", LocalDate.now().toString());
        String process = templateEngine.process("attendance.html", context);
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);

    }

//    @Scheduled(cron = "0 1 18 ? * FRI,SUN")
//    @Scheduled(cron = "0 1 18 ? * MON-SAT")
//    @Scheduled(cron = "0/30 * * * * *")
    public void attendanceSignOut() throws MessagingException {
        String originStr = "th nowrap style=\"text-align: center;\"";
        String afterStr = "th nowrap style=\"text-align: center;width: 200px;\"";
        //随机睡眠60秒内
        randomSleep();
        String signOutResult = attendance.dutySignOut();
        Document document = Jsoup.parse(signOutResult);
        String table = document.select("table").toString();
        table = table.replace(originStr, afterStr);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("天下信用下班打卡结果反馈");
        helper.setFrom("920253445@qq.com");
        helper.setTo("zengchao237@163.com");
        helper.setSentDate(new Date());

        Context context = new Context();
        context.setVariable("table", table);
        context.setVariable("date", LocalDate.now().toString());
        String process = templateEngine.process("attendance.html", context);
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);

    }

    /**
     * 随机睡眠120秒内
     */
    public void randomSleep() {
        Random random = new Random();
        int sleep = random.nextInt(120000);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
