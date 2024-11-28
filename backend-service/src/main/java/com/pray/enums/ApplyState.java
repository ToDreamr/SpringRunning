package com.pray.enums;

/**
 * Title: 申请审核状态<br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 杨子叶
 * @date
 */
public enum ApplyState implements BaseEnum {
    All("全部", 99),
    Unaudit("待审核", 0),
    Pass("", 1),
    Fail("未通过", 2),
    UnAuditFinish("待审核完成", 30),
    Finish("完成", 3),
    Repeal("撤销", 4);
    private final String desc;
    private final int value;

    private ApplyState(String desc, int value) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * //将数值转换成枚举值
     *
     * @param value
     * @return
     */
    public static ApplyState intToEnum(int value) {
        switch (value) {
            case 99:
                return All;
            case 0:
                return Unaudit;
            case 1:
                return Pass;
            case 2:
                return Fail;
            case 30:
                return UnAuditFinish;
            case 3:
                return Finish;
            case 4:
                return Repeal;
            default:
                return null;
        }
    }

    public Integer getValue() {
        return this.value;
    }

    /**
     * @return
     */
    @Override
    public String getDescription() {
        return this.desc;
    }

}
