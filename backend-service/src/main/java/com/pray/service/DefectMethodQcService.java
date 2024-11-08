package com.pray.service;

import com.pray.entity.DefectMethod;
import com.pray.mapper.DefectMethodMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

/**
 * DefectMethodQcService
 *
 * @author Cotton Eye Joe
 * @since 2024/11/8 15:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefectMethodQcService {
    @Autowired
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
    }public void runTheWorldSomeThing(String args, DefectMethod methodItem){
        System.out.println("我想说句话:"+args);
        System.out.println("Here is my DM_ID:"+methodItem.getDmId());
    }

    public boolean getMethodQc(){
        List<DefectMethod> qcMethodItems = qcBizMapper.getQcMethodItems();
        for (DefectMethod defectMethod:qcMethodItems){
            Method methods = null;
            try {
                methods=this.getClass().getMethod(defectMethod.getMethodName(),String.class,DefectMethod.class);
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
