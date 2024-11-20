package com.pray.secure.interceptor;

import com.alibaba.fastjson2.JSON;
import com.pray.common.DbOperateLog;
import com.pray.common.PrayThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * MybatisCustomInterceptor
 *
 * @author Cotton Eye Joe
 * @since 2024/11/9 20:26
 */
@Slf4j
@Component
@Intercepts(@Signature(type = StatementHandler.class,
        method = "prepare", args = {Connection.class, Integer.class}
))
public class MybatisCustomInterceptor implements Interceptor {
    private final ExecutorService pool = PrayThreadPoolExecutor.getPrayExecutor(10,15,20L, TimeUnit.MINUTES,10).build();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LocalDateTime now = LocalDateTime.now();
        Object ret = invocation.proceed();

        try {
            this.doProceed(invocation,now);
        } catch (Exception var5) {
            log.error("日志记录错误！", var5);
        }
        return ret;
    }

    private void doProceed(Invocation invocation, LocalDateTime now) {

        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();

        if (target instanceof StatementHandler) {
            RoutingStatementHandler handler = (RoutingStatementHandler) target;
            MetaObject metaObject = MetaObject.forObject(handler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                    SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                    new DefaultReflectorFactory());
            MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            this.doExProceed(args, now, ms);
        }
    }
    private void doExProceed(Object[] args, LocalDateTime now, MappedStatement ms) {
        DbOperateLog dbOperateLog = (new DbOperateLog()).setCreateTime(now).setLogPosition(getStackTrace(ms.getId(), "com.sun.proxy")).setOperateContent(String.format("【数据库%s日志】 值：%s", ms.getSqlCommandType().equals(SqlCommandType.INSERT) ? "新增" : (ms.getSqlCommandType().equals(SqlCommandType.DELETE) ? "删除" : "更新"), JSON.toJSONString(args[1])));
        BoundSql boundSql = ms.getBoundSql(args[1]);
        String sql = this.getSql(boundSql, ms);
        this.pool.execute(() -> {
            log.info("Execute SQL: "+sql);
            log.info(dbOperateLog.toString());
        });
    }

    private String getSql(BoundSql boundSql, MappedStatement ms) {
        String sql = boundSql.getSql();
        sql = sql.replace("\n", " ");
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            Iterator mappingIterator = parameterMappings.iterator();

            while(mappingIterator.hasNext()) {
                ParameterMapping parameterMapping = (ParameterMapping)mappingIterator.next();
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    String propertyName = parameterMapping.getProperty();
                    Object value;
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = ms.getConfiguration().newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }

                    sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(this.getParameter(value)));
                }
            }
        }
        return sql;
    }

    public String getParameter(Object parameter) {
        if (parameter == null) {
            return "null";
        } else {
            return parameter instanceof String ? "'" + parameter + "'" : parameter.toString();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
    public static String getStackTrace(String method, String className) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for(int i = 0; i < stackTrace.length; ++i) {
            StackTraceElement traceElement = stackTrace[i];
            if (method.contains(traceElement.getMethodName()) && traceElement.getClassName().contains(className) && i < stackTrace.length - 1 && !"java.base".equals(stackTrace[i + 1].getModuleName()) && !stackTrace[i + 1].getClassName().startsWith("org.apache.ibatis") && !stackTrace[i + 1].getClassName().startsWith("org.mybatis")) {
                return stackTrace[i + 1].toString();
            }
        }

        return null;
    }
}
