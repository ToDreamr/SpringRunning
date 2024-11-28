package com.pray.enums;

/**
 * SysResCodeEnum
 *
 * @author 九歌天上有
 * @since 2024/11/27 下午9:37
 */
public enum SysResCodeEnum {
    SUCCESS(200, "成功"),
    ERROR(500, "系统错误"),
    TIP_ERROR(12000, "错误: %s"),
    UN_USE_EMP(12010, "不存在可用的人员数据！"),
    EXIST_MULTIPLE_EMP(12011, "存在相同工号和密码的人员数据,请指定登录机构！"),
    FILE_IS_NOT_EXIST(12001, "文件不存在: %s"),
    FILE_DOWNLOAD_ERROR(12002, "文件下载异常: %s"),
    DB_ERROR(12003, "数据库错误: %s");

    String resMsg;
    int resCode;

    SysResCodeEnum(int resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }
}
