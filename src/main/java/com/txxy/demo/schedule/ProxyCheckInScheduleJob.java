package com.txxy.demo.schedule;

import com.txxy.demo.proxy.ProxyCheckIn;
import com.txxy.demo.util.JacksonConverter;
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
import java.util.Map;

/**
 * @Author zengch
 * @Date 2023-06-16
 **/
@Component
public class ProxyCheckInScheduleJob {

    @Resource
    private ProxyCheckIn proxyCheckIn;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;

    //MON,TUES,WED,THU,FRI,SAT,SUN
//    @Scheduled(cron = "0 0 8 ? * FRI,SUN")
//    @Scheduled(cron = "0 0 8 ? * MON-SUN")
//    @Scheduled(fixedDelay = 86400000)
//    @Scheduled(cron = "0/50 * * * * *")
    public void attendanceSignIn() throws MessagingException {

        sendCheckInResultFor920253445Account();
        sendCheckInResultOnFoxMailAccount();

    }

    private void sendCheckInResultFor920253445Account() throws MessagingException {
        Map checkInMap = proxyCheckIn.checkIn();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("GLados自动签到结果反馈");
        helper.setFrom("920253445@qq.com");
        helper.setTo("zengchao237@163.com");
        helper.setSentDate(new Date());

        Context context = new Context();
        context.setVariable("table", JacksonConverter.write(checkInMap));
        context.setVariable("date", LocalDate.now().toString());
        String process = templateEngine.process("GladosCheckIn.html", context);
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);
    }

    private void sendCheckInResultOnFoxMailAccount() throws MessagingException {
        Map checkInMap = proxyCheckIn.checkInFor88FoxMail();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("88Foxmail@GLados自动签到结果反馈");
        helper.setFrom("920253445@qq.com");
        helper.setTo("zengchao237@163.com");
        helper.setSentDate(new Date());

        Context context = new Context();
        context.setVariable("table", JacksonConverter.write(checkInMap));
        context.setVariable("date", LocalDate.now().toString());
        String process = templateEngine.process("GladosCheckIn.html", context);
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);
    }

}
