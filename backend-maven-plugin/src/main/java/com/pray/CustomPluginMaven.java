package com.pray;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * CustomPluginMaven
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 21:25
 */
@Mojo(name = "custom", defaultPhase = LifecyclePhase.CLEAN)
@Execute(phase = LifecyclePhase.CLEAN)
public class CustomPluginMaven extends AbstractMojo {

    @Parameter
    private String serverPort;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("com.pray.func.HttpConfig");
            String[] runArgs = {serverPort};
            Method method = clazz.getMethod("main", String[].class);
            method.invoke(null, (Object) runArgs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
