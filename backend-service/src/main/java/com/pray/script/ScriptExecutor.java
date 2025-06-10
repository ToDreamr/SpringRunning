package com.pray.script;


/**
 * <p>
 * ScriptExecutor
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/6/10 20:53
 */
import com.pray.exception.ScriptExecutionException;

import java.util.Map;

public abstract class ScriptExecutor {


    public final Object execute(String script, Map<String, Object> bindings) throws ScriptExecutionException {
        // 1. 初始化脚本引擎
        initEngine();

        // 2. 设置绑定变量
        setBindings(bindings);

        // 3. 执行脚本
        Object result = executeScript(script);

        // 4. 清理资源
        cleanup();

        return result;
    }

    protected abstract void initEngine() throws ScriptExecutionException;


    protected abstract void setBindings(Map<String, Object> bindings) throws ScriptExecutionException;


    protected abstract Object executeScript(String script) throws ScriptExecutionException;


    protected abstract void cleanup();


    public abstract String getLanguageName();

}
