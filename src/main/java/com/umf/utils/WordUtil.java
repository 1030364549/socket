package com.umf.utils;


import java.util.Random;

public class WordUtil {

    /**
     * 生成随机数量的汉字
     *
     * @date 2020/11/4 10:01
     * @param arr（在数组中随机取出一个数字，生成对应数量的随机汉字）
     * @return
     */
    public static String getWord(int[] arr){
        Random random = new Random();
        int num = arr[random.nextInt(arr.length)];
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<num; i++){
            sb.append(WordUtil.getRandomChinese());
        }
        return sb.toString();
    }

    /**
     * 生成固定数量的汉字
     *
     * @date 2020/11/4 10:01
     * @param num（在数组中随机取出一个数字，生成对应数量的随机汉字）
     * @return
     */
    public static String getWord(int num){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<num; i++){
            sb.append(WordUtil.getRandomChinese());
        }
        return sb.toString();
    }

    /**
     * 获取随机的单个汉字
     *
     * @date 2020/11/2 17:19
     * @return
     */
    public static String getRandomChinese(){
        Random random = new Random();
        int code1,code2,code3,code4;    //分别代表四个位码
        String[] rBase = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

        code1 = random.nextInt(3) + 11;
        String str_r1 = rBase[code1];   //获得第一个位码

        if(code1 == 13) {
            code2 = random.nextInt(7);
        } else {
            code2 = random.nextInt(16);
        }
        String str_r2 = rBase[code2];   //第二个位码

        code3 = random.nextInt(6) + 10;
        String str_r3 = rBase[code3];   //第三个位码

        if(code3 == 10) {
            code4 = random.nextInt(15) + 1;
        } else if(code3 == 15) {
            code4 = random.nextInt(15);
        } else {
            code4 = random.nextInt(16);
        }
        String str_r4 = rBase[code4];   //第四个位码

        byte[] bytes = new byte[2];
        String str_r12 = str_r1 + str_r2;
        int tempLow = Integer.parseInt(str_r12, 16);
        bytes[0] = (byte) tempLow;      //低位字节

        String str_r34 = str_r3 + str_r4;
        int tempHigh = Integer.parseInt(str_r34, 16);
        bytes[1] = (byte) tempHigh;     //高位字节

        //生成汉字
        String checkCode = new String(bytes);
        return checkCode;
    }
}
