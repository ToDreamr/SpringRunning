package com.pray;

/**
 * MethodTest
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 22:15
 */
public class ApplicationStarter {
    public static void say(String[] args){
        String constantsWords = "Just say something let us know that you are alive!";
        StringBuilder sb  = new StringBuilder(constantsWords);
        for (String s:args) {
            sb.append(s).append(" ");
        }
        System.out.println(sb.toString());
    }
    public  void fun(String[] args){
        String constantsWords = "Just say something let us know that you are alive!";
        StringBuilder sb  = new StringBuilder(constantsWords);
        for (String s:args) {
            sb.append(s).append(" ");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) throws Exception {
        LoadApplication.init();
    }
}
