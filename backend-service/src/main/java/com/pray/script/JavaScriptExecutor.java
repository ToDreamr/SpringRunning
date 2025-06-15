package com.pray.script;

import com.pray.enums.ScriptTypeEnum;
import com.pray.exception.ScriptExecutionException;

import javax.script.*;
import java.io.Reader;
import java.util.Map;

/**
 * <p>
 * JavaScriptExecutor
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/6/10 20:55
 */
public class JavaScriptExecutor extends ScriptExecutor {

    public volatile static JavaScriptExecutor instance;

    private ScriptEngine engine;

    private JavaScriptExecutor() {

    }

    public static JavaScriptExecutor getInstance(){
        if (instance==null){
            synchronized (JavaScriptExecutor.class){
                if (instance==null){
                    instance=new JavaScriptExecutor();
                }
            }
        }
        return instance;
    }

    @Override
    protected void initEngine() throws ScriptExecutionException {
        try {
            // 方法1：尝试标准方式获取
            ScriptEngineManager manager = new ScriptEngineManager();
            this.engine = manager.getEngineByName("javascript");

            if (this.engine == null) {
                this.engine = ScriptEngineFactoryProvider.createJavaScriptEngine();
            }
        } catch (Exception e) {
            throw new ScriptExecutionException("JavaScript引擎初始化失败，请确保已添加依赖：\n" +
                    "Maven:\n" +
                    "<dependency>\n" +
                    "    <groupId>org.openjdk.nashorn</groupId>\n" +
                    "    <artifactId>nashorn-core</artifactId>\n" +
                    "    <version>15.4</version>\n" +
                    "</dependency>\n\n" +
                    "或GraalVM依赖:\n" +
                    "<dependency>\n" +
                    "    <groupId>org.graalvm.js</groupId>\n" +
                    "    <artifactId>js</artifactId>\n" +
                    "    <version>22.3.0</version>\n" +
                    "</dependency>");
        }

    }

    @Override
    protected void setBindings(Map<String, Object> bindings) {
        if (bindings != null && !bindings.isEmpty()) {
            engine.setBindings(new SimpleBindings(bindings), ScriptContext.ENGINE_SCOPE);
        }
    }

    @Override
    protected Object executeScript(String script) throws ScriptExecutionException {
        // 1. 前置检查
        if (engine == null) {
            throw new ScriptExecutionException("引擎未初始化，请先调用initEngine()");
        }
        if (script == null || script.trim().isEmpty()) {
            throw new ScriptExecutionException("脚本内容不能为空");
        }

        // 2. 执行脚本（带详细错误处理）
        try {
            // 添加绑定变量检查
            Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            System.out.println("当前绑定变量: " + bindings.keySet());

            System.out.println("执行脚本: " + script);
            Object result = engine.eval(script);
            System.out.println("执行结果: " + result);
            return result;

        } catch (ScriptException e) {
            // 提取JavaScript引擎的错误信息
            String errorMsg = extractJSError(e);
            throw new ScriptExecutionException("JS执行错误: " + errorMsg, e);
        } catch (Exception e) {
            throw new ScriptExecutionException("执行时发生系统异常", e);
        }
    }

    @Override
    protected void cleanup() {
        // JavaScript引擎通常不需要特殊清理
    }

    @Override
    public String getLanguageName() {
        return ScriptTypeEnum.JAVASCRIPT.getEngineName();
    }

    // 解析JavaScript引擎的详细错误
    private String extractJSError(ScriptException e) {
        String msg = e.getMessage();
        // Nashorn错误格式处理
        if (msg.contains("ReferenceError") || msg.contains("SyntaxError")) {
            return msg.split("\\n")[0];
        }
        // GraalVM错误格式处理
        if (msg.contains("TypeError")) {
            return msg.split("at ")[0];
        }
        return msg;
    }

    private String readScript(Reader reader) throws ScriptException {
        // 简单实现，实际可能需要更健壮的读取方式
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[1024];
        int length;
        try {
            while ((length = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, length);
            }
        } catch (Exception e) {
            throw new ScriptException(e);
        }
        return sb.toString();
    }
}
