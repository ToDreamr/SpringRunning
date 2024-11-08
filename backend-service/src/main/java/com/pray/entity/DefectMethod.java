package com.pray.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;



/**
* 方法测试表
* @TableName tb_defect_method
*/
@Setter
@Getter
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

}
