package com.txxy.demo.leetcode;

import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {

        int[] resultArr = new int[2];

        for (int i = 0; i < nums.length; i++) {

            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) {

                    resultArr[0] = i;
                    resultArr[1] = j;

                }
            }
        }

        return resultArr;
    }

    public int[] twoSum2(int[] nums, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            int remain = target - nums[i];

            if (map.containsKey(remain)) {
                return new int[]{i, map.get(remain)};
            }

            map.put(nums[i], i);
        }

        return new int[0];
    }

    public static void main(String[] args) {

        Solution solution = new Solution();
        int[] nums = {2, 7, 11, 15};
        int[] twoSum = solution.twoSum2(nums, 9);

        System.out.println(Arrays.stream(twoSum).toArray());

    }
}