package com.pray.rbac.type;

/**
 * RoleEnabled
 *
 * @author 九歌天上有
 * @since 2024/12/20 下午2:19
 */
public enum RoleEnabled {
    /**
     * 启用
     */
    enabled(1),

    /**
     * 禁用
     */
    disabled(0);

    private final int value;

    private RoleEnabled(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
