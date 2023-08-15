package com.txxy.demo.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class Sentinel {

    private static void initFlowRules() {
        ArrayList<FlowRule> rules = new ArrayList<>();

        FlowRule rule = new FlowRule();

        rule.setResource("HelloWord");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        rules.add(rule);

        FlowRuleManager.loadRules(rules);
    }


    public static void main(String[] args) {

        initFlowRules();

        while (true) {
            int i = 0;
            try (Entry entry = SphU.entry("HelloWord1")) {

                log.info("hello word");
//                System.out.println("hello word!");
            } catch (BlockException e) {

                log.error("block");
//                System.out.println("block!" + i++);
            }
        }

    }
}
