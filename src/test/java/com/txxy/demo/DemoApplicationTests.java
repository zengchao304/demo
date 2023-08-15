package com.txxy.demo;

import com.txxy.demo.schedule.AttendanceScheduleJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private AttendanceScheduleJob attendanceScheduleJob;


    @Test
    void contextLoads() throws MessagingException {
//        attendanceScheduleJob.attendanceSignIn();
        attendanceScheduleJob.attendanceSignOut();
    }

    @Test
    int addTest(int num1, int num2) {
        return num1 + num2;
    }

}
