package com.pray.func;

import java.util.Arrays;

/**
 * GeneralConfig
 *
 * @author Cotton Eye Joe
 * @since 2024/11/20 23:15
 */
public class GeneralConfig extends AbstractGeneralConfig<GeneralConfig>{
    public String profileActivation;
    public int profileId;
    public static GeneralConfig INSTANCE;
    public GeneralConfig getConfigRegistry(String profileActivation){
        GeneralConfig generalConfig = getInstance();
        generalConfig.profileActivation=profileActivation;
        return generalConfig;
    }
    public GeneralConfig getConfigRegistry(int profileId){
        GeneralConfig generalConfig = getInstance();
        generalConfig.profileId=profileId;
        return generalConfig;
    }

    public GeneralConfig getConfigRegistry(String[] distribution){
        GeneralConfig generalConfig = getInstance();
        generalConfig.distribution=distribution;
        return generalConfig;
    }
    public String profileActivation() {
        return profileActivation;
    }

    private GeneralConfig() {
    }
    public static GeneralConfig getInstance(){
        synchronized (GeneralConfig.class){
            if (INSTANCE==null){
                synchronized (GeneralConfig.class){
                    INSTANCE=new GeneralConfig();
                    return INSTANCE;
                }
            }
            return INSTANCE;
        }
    }
    public GeneralConfig addConfig(InnerConfig<GeneralConfig> config,String profileActivation){
        config.customize(getConfigRegistry(profileActivation));
        return INSTANCE;
    }
    public GeneralConfig addConfig(InterfaceSingleMethod<GeneralConfig> config,int profileId){
        config.customize(getConfigRegistry(profileId));
        return INSTANCE;
    }

    @Override
    public GeneralConfig addConfig(InnerConfig<GeneralConfig> config, String[] distribution) {
        config.customize(getConfigRegistry(distribution));
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "GeneralConfig{" +
                "profileActivation='" + profileActivation + '\'' +
                ", profileId=" + profileId +
                ", distribution=" + Arrays.toString(distribution) +
                '}';
    }
}
