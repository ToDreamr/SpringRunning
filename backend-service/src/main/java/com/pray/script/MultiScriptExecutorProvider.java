package com.pray.script;


import com.pray.enums.ScriptTypeEnum;
import com.pray.exception.ScriptExecutionException;

/**
 * <p>
 * MultiScriptExecutorProvider
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/6/11 9:45
 */
public class MultiScriptExecutorProvider {

    public static ScriptExecutor getExecutorWithScriptType(ScriptTypeEnum scriptType) {
        switch (scriptType) {
            case JAVASCRIPT:
                return JavaScriptExecutor.getInstance();
            default:
                throw new ScriptExecutionException("Unknown script type: " + scriptType);
        }
    }
}
