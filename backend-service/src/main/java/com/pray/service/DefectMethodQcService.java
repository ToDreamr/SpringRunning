package com.pray.service;

import com.pray.entity.po.DefectMethod;
import com.pray.mapper.DefectMethodMapper;
import com.pray.script.JavaScriptExecutor;
import com.pray.script.MultiScriptExecutorProvider;
import com.pray.script.ScriptExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

import static com.pray.enums.ScriptTypeEnum.JAVASCRIPT;

/**
 * DefectMethodQcService
 *
 * @author Cotton Eye Joe
 * @since 2024/11/8 15:01
 */
@Slf4j
@Service
@AllArgsConstructor
public class DefectMethodQcService {

    private DefectMethodMapper qcBizMapper;

    public void yellTheWorldSomeThing(String args, DefectMethod methodItem){
        System.out.println("我想说句话:"+args);
        System.out.println("Here is my DM_ID:"+methodItem.getDmId());
    }
    public void cryTheWorldSomeThing(String args, DefectMethod methodItem){
        System.out.println("我想说句话:"+args);
        System.out.println("Here is my DM_ID:"+methodItem.getDmId());
    }
    public void smileTheWorldSomeThing(String args, DefectMethod methodItem){
        System.out.println("我想说句话:"+args);
        System.out.println("Here is my DM_ID:"+methodItem.getDmId());
    }
    public void runTheWorldSomeThing(String args, DefectMethod methodItem){
        System.out.println("我想说句话:"+args);
        System.out.println("Here is my DM_ID:"+methodItem.getDmId());
    }

    public boolean getMethodQc(){
        List<DefectMethod> qcMethodItems = qcBizMapper.getQcMethodItems();
        for (DefectMethod defectMethod:qcMethodItems){
            Method methods = null;
            try {
                methods=this.getClass().getMethod(defectMethod.getMethodName(),String.class,DefectMethod.class);
                if (StringUtils.isNoneEmpty(defectMethod.getRunScript())){
                    JavaScriptExecutor javaScriptExecutor = (JavaScriptExecutor) MultiScriptExecutorProvider.getExecutorWithScriptType(JAVASCRIPT);
                    String result = (String) javaScriptExecutor.execute(defectMethod.getRunScript(),null);
                    System.out.println(result);
                }
                methods.invoke(this,"自弁はいつも信じ出る！！",defectMethod);
                log.info(defectMethod.getDmId()+",测试通过");
            }catch (Exception e){
                if (e instanceof NoSuchMethodException){
                    log.error("测试失败，没有找到dm_id为:"+defectMethod.getDmId()+",方法名为:"+defectMethod.getMethodName()+"的方法");
                }
                throw new RuntimeException("没有这个方法,dmId:"+defectMethod.getDmId()+"\r\n"+e.getMessage());
            }
        }
        return true;
    }

}
