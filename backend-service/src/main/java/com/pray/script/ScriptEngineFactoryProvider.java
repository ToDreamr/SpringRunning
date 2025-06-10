package com.pray.script;


import com.pray.exception.ScriptExecutionException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * <p>
 * ScriptEngineFactoryProvider
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/6/10 22:11
 */
public class ScriptEngineFactoryProvider {

    /**
     * 获取JavaScript引擎
     * @return
     * @throws ScriptExecutionException
     */
    public static ScriptEngine createJavaScriptEngine() throws ScriptExecutionException {
        // 尝试标准方式
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        if (engine != null) return engine;
        // 尝试Nashorn
        try {
            engine = new org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory()
                    .getScriptEngine();
            if (engine != null) return engine;
            return engine;
        } catch (NoClassDefFoundError e) {
            // Nashorn不可用，继续尝试其他方式
        }
        return null;
    }
}
