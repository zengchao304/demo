package com.txxy.demo.leetcode;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Objects;

public class AddTwoNumbers {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        int carryFlag = 0;
        int sum = l1.val + l2.val;

        carryFlag = sum / 10;
        int remainder = sum % 10;

        ListNode sumNode = new ListNode(remainder);

        ListNode headNode = sumNode;


        while (l1.next != null || l2.next != null) {
            ListNode nextL1 = l1.next;
            ListNode nextL2 = l2.next;
            int nextSum;

            if (Objects.nonNull(nextL1)) {

                if (Objects.nonNull(nextL2)){

                    nextSum = nextL1.val + nextL2.val;
                    l2 = l2.next;

                }else{

                    nextSum = nextL1.val;

                }

                l1 = l1.next;

            }else {

                nextSum = nextL2.val;
                l2 = l2.next;

            }

            if (carryFlag > 0) {
                nextSum += carryFlag;
            }

            carryFlag = nextSum / 10;

            int nextNodeSumRemainder = nextSum % 10;

            ListNode nextSumNode = new ListNode(nextNodeSumRemainder);

            headNode.next = nextSumNode;
            headNode = nextSumNode;


        }

        if (carryFlag>0){
            headNode.next = new ListNode(1);
        }

        return sumNode;
    }


    public static void main(String[] args) {
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        int[] l1 = {9, 9, 9, 9, 9, 9, 9};
        int[] l2 = {9, 9, 9, 9};

        ListNode l1Nodes = new ListNode();

        ListNode headNode = l1Nodes;

        for (int i = 0; i < l1.length; i++) {
            if (i==0){
                l1Nodes.val = l1[0];
            }else {
                ListNode nextNode = new ListNode();
                nextNode.val = l1[i];

                headNode.next = nextNode;
                headNode = nextNode;

            }
        }

        ListNode l2Nodes = new ListNode();
        ListNode headNode2 = l2Nodes;

        for (int i = 0; i < l2.length; i++) {
            if (i==0){
                l2Nodes.val = l2[0];
            }else {
                ListNode nextNode = new ListNode();
                nextNode.val = l2[i];

                headNode2.next = nextNode;
                headNode2 = nextNode;
            }
        }


        ListNode listNode = addTwoNumbers.addTwoNumbers(l1Nodes, l2Nodes);
    }
}
