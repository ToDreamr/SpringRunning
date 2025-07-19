package com.pray.template;


import com.pray.common.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * <p>
 * SpTransactionTemplate
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/6/26 23:03
 */
@Service
public class SpTransactionTemplate {

    public void executeWithTransaction(CommonBusinessCallBack commonBusinessCallBack, TransactionTemplate transactionTemplate, Result<?> result) {
        execute(commonBusinessCallBack, transactionTemplate, result);
    }

    public void executeWithNoTransaction(CommonBusinessCallBack commonBusinessCallBack, Result<?> result) {
        execute(commonBusinessCallBack, null, result);
    }

    private void execute(final CommonBusinessCallBack callBack,TransactionTemplate transactionTemplate, Result<?> result) {
        try {

            callBack.check();

            if (transactionTemplate !=null ) {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        try {
                            callBack.execute();
                            result.setCode(200);
                        } catch (Throwable throwable) {
                            result.setCode(500);
                            status.setRollbackOnly();
                            throw throwable;
                        }
                    }
                });
            }else {
                result.setCode(200);
                callBack.execute();
            }

        } catch (Throwable throwable) {
            result.setCode(500);
            throw throwable;
        }
    }

}
