package Graphh;

import java.text.DecimalFormat;
import java.util.Random;
import java.math.BigDecimal;
import java.util.HashSet;

public class Getrandom {
    public static int GetevenNum() {
        Random random = new Random();
        int s=random.nextInt(2001);
        return s;

    }
    public static int Getweight(int n){
        Random random = new Random();
        int s= random.nextInt(n);
        return s;
    }
    public static int Getidk(int n){
        Random random = new Random();
        int s= random.nextInt(n);
        return s;
    }
    public static double  Getchance(){
        Random random = new Random();
        double s=random.nextDouble();
        // 新建格式化器，设置格式
        BigDecimal decimal=new BigDecimal(s);
        // 将数据四舍五入为两位小说的double值
        double s2=decimal.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
        return s2;
    }
    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
        if (n > (max - min + 1) || max < min) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num);// 将不同的数存入HashSet中
        }
        int setSize = set.size();
        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
        if (setSize < n) {
            randomSet(min, max, n - setSize, set);// 递归
        }
    }

    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }


//    public static void main(String[] args){
//
//        double a = Getrandom.Getchance();
//
//        System.out.println(a);
//    }
}
