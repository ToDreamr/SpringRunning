package com.pray.mybatis.entity;



import java.io.Serializable;


/**
* 方法测试表
* @TableName tb_defect_method
*/

public class DefectMethod implements Serializable {

    /**
     * 存在多个构造方法，而Mybatis不知道该如何选择，那么就会告诉我们找不到合适的构造方法，要解决这种问题也很简单，
     * 我们不需要删除这些多余的构造方法，只需添加一个无参构造或是全参构造即可
     */
    public DefectMethod() {
        System.out.println("Mybatis将会使用实体的无参构造方法进行反射构造填充数据");
    }

    /**
    * 方法ID
    */
    private Integer dmId;
    /**
    * 质控项名称
    */
    private String defectName;
    /**
    * 方法名
    */
    private String methodName;
    /**
    * 质控项目归属条件
    */
    private Integer stdClassifiedId;
    /**
    * 先决执行脚本条件
    */
    private String runScript;

    /**
     * 必要参数
     */
    private String runArgs;

    public Integer dmId() {
        return dmId;
    }

    public void setDmId(Integer dmId) {
        this.dmId = dmId;
    }

    public String defectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public String methodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer stdClassifiedId() {
        return stdClassifiedId;
    }

    public void setStdClassifiedId(Integer stdClassifiedId) {
        this.stdClassifiedId = stdClassifiedId;
    }

    public String runScript() {
        return runScript;
    }

    public void setRunScript(String runScript) {
        this.runScript = runScript;
    }

    public String runArgs() {
        return runArgs;
    }

    public void setRunArgs(String runArgs) {
        this.runArgs = runArgs;
    }

    @Override
    public String toString() {
        return "DefectMethod{" +
                "dmId=" + dmId +
                ", defectName='" + defectName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", stdClassifiedId=" + stdClassifiedId +
                ", runScript='" + runScript + '\'' +
                ", runArgs='" + runArgs + '\'' +
                '}';
    }
}
