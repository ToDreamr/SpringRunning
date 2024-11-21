package com.pray.build;

import com.sun.security.auth.module.NTLoginModule;

/**
 * NormalBuild
 *
 * @author Cotton Eye Joe
 * @since 2024/11/21 21:37
 */
public class NormalBuild {
    private int ageField;
    private String nameField;

    private NormalBuild(){

    }

    private NormalBuild(int ageField, String nameField) {
        this.nameField=nameField;
        this.ageField=ageField;
    }

    public NormalBuild ageField(int ageField){
        this.ageField=ageField;
        return this;
    }
    public NormalBuild nameField(String nameField){
        this.nameField=nameField;
        return this;
    }

    public static NormalBuild builder(){
        return new NormalBuild();
    }
    public NormalBuild build(){
        return new NormalBuild(ageField,nameField);
    }

    @Override
    public String toString() {
        return "NormalBuild{" +
                "ageField=" + ageField +
                ", nameField='" + nameField + '\'' +
                '}';
    }


    public static void main(String[] args) {
        NormalBuild build = NormalBuild.builder().ageField(12).nameField("32").build();
        System.out.println(build);
    }
}
