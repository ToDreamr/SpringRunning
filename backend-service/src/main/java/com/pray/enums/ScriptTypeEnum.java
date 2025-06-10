package com.pray.enums;


/**
 * <p>
 * ScriptTypeEnum (Rain)
 * <p>
 *
 * @author 花行
 * @since 2025/6/10 20:59
 */
public enum ScriptTypeEnum {
    JAVASCRIPT("js", "javascript"),
    PYTHON("py", "python"),
    GROOVY("groovy", "groovy"),
    RUBY("rb", "ruby"),
    LUA("lua", "lua"),
    SCALA("scala", "scala"),
    KOTLIN("kts", "kotlin"),
    CLOJURE("clj", "clojure");

    private final String fileExtension;  // 文件扩展名
    private final String engineName;     // 脚本引擎名称

    ScriptTypeEnum(String fileExtension, String engineName) {
        this.fileExtension = fileExtension;
        this.engineName = engineName;
    }

    // 根据文件扩展名获取枚举
    public static ScriptTypeEnum fromFileExtension(String ext) {
        if (ext == null || ext.isEmpty()) {
            return null;
        }
        ext = ext.startsWith(".") ? ext.substring(1) : ext;
        for (ScriptTypeEnum type : values()) {
            if (type.fileExtension.equalsIgnoreCase(ext)) {
                return type;
            }
        }
        return null;
    }

    // 根据引擎名称获取枚举
    public static ScriptTypeEnum fromEngineName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (ScriptTypeEnum type : values()) {
            if (type.engineName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    // Getters
    public String getFileExtension() {
        return fileExtension;
    }

    public String getEngineName() {
        return engineName;
    }

    @Override
    public String toString() {
        return name() + " (" + fileExtension + ")";
    }
}
