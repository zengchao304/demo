package com.txxy.demo.wati;

import com.txxy.demo.domain.TestDO;
import com.txxy.demo.mapper.TestMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Component
public class AddController {

    @Resource
    private TestMapper testMapper;


    @GetMapping("/add")
    public int add(int num1, int num2) {

        int sum = num1 + num2;

        TestDO testDO = new TestDO();
        testDO.setNum1(num1);
        testDO.setNum2(num2);
        testDO.setSum(sum);
        testMapper.insert(testDO);

        return sum;
    }
}
