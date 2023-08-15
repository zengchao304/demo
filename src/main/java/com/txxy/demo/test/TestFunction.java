package com.txxy.demo.test;

public class TestFunction {

    private static int function(int x){

        int left = 0;
        int right = x;

        while (left<right){
            long mid = left+((right-left)>>1);
            long s = mid* mid;

            if (s>x){
                right = (int) (mid-1);

            } else if (s < x) {
                if ((mid+1)*(mid+1)>x){
                    return (int) mid;
                }

                left = (int) (mid+1);

            }else {
                return (int) mid;
            }
        }

        return left;
    }

    private static int sum(int start , int end){

        int sum = start;
        for (int i = start+1;i<=end;i++){
            sum+=i;
        }
        return sum;
    }

    public static void main(String[] args) {

        System.out.println(sum(1, 36));
        System.out.println(function(2));
        System.out.println(function(4));
        System.out.println(function(8));
        System.out.println(function(16));
        System.out.println(function(32));
        System.out.println(function(64));
        System.out.println(function(128));
        System.out.println(function(256));
        System.out.println(function(512));
    }
}
