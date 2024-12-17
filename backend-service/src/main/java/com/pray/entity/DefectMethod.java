package com.pray.entity;


import lombok.ToString;

import java.io.Serializable;


/**
* 方法测试表
* @TableName tb_defect_method
*/
@ToString
public class DefectMethod implements Serializable {

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

    public Integer getDmId() {
        return dmId;
    }

    public void setDmId(Integer dmId) {
        this.dmId = dmId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public Integer getStdClassifiedId() {
        return stdClassifiedId;
    }

    public void setStdClassifiedId(Integer stdClassifiedId) {
        this.stdClassifiedId = stdClassifiedId;
    }

    public String getRunScript() {
        return runScript;
    }

    public void setRunScript(String runScript) {
        this.runScript = runScript;
    }

    public String getRunArgs() {
        return runArgs;
    }

    public void setRunArgs(String runArgs) {
        this.runArgs = runArgs;
    }
}
