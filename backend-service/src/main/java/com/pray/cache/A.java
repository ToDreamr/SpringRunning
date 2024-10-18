package com.pray.cache;

import java.util.Scanner;

/**
 * A
 *
 * @author Cotton Eye Joe
 * @since 2024/10/8 19:38
 */
public class A {
    static class C{
        private final String fatherField = "time";

        private int fatherCField;
        public C() {
        }

        public C(int fatherCField) {
            this.fatherCField = fatherCField;
        }
    }
    static class D extends C{
        public D() {
        }

        public D(int fatherCField) {
            super(fatherCField);
        }
    }
    static class E extends D implements K{
        public E() {
        }

        public E(int fatherCField) {
            super(fatherCField);
        }

        @Override
        public void que() {

        }

        @Override
        public void defaultMethod() {
            K.interfaceStatic();
            K.super.defaultMethod();
        }

    }
    interface K{
        public static void interfaceStatic(){

        }
        public  abstract void que();
        default  void defaultMethod(){

        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        /**
         * "welcome to alibaba!" -> "!ABABali OT EMOCLEW"
         * "ali all in, Ali ilA" -> "ALI ILA ,NI LLA ali"
         * "keep ali" -> "ali PEEK"
         */
        C c=new D();
        c =new E();
        System.out.println(c.fatherField);

        E e =new E();
        e.defaultMethod();
        e.que();
    }
}
