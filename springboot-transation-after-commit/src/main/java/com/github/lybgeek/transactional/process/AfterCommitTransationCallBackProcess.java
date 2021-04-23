package com.github.lybgeek.transactional.process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @description: 事务提交处理执行器
 *
 **/
@Slf4j
@Component
public class AfterCommitTransationCallBackProcess extends TransactionSynchronizationAdapter implements Executor {
    //用于存放同个事务中多个执行事件
    private ThreadLocal<List<Runnable>> currentRunablesCombineThreadLocal = new ThreadLocal<>();

    @Override
    public void execute(Runnable runnable) {
         boolean isTransactionActive = registerSynchronizationActiveTransaction(runnable);
         //如果处于非事务状态，则直接运行
         if(!isTransactionActive){
             log.warn("No transaction is active");
             runnable.run();
         }
    }

    @Override
    public void afterCommit() {
        processEvent();
    }

    @Override
    public void afterCompletion(int status) {
        currentRunablesCombineThreadLocal.remove();
    }

    /**
     * 事件处理
     */
    private void processEvent(){
        log.info("processEvent after transation commit");
        List<Runnable> runnables = currentRunablesCombineThreadLocal.get();
        try {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
        } catch (Exception e) {
            log.error("afterCommit error : "+e.getMessage(),e);
        }
    }

    /**
     * 注册处于活动状态的事务
     * @param runnable
     * @return
     */
    private boolean registerSynchronizationActiveTransaction(Runnable runnable){
        boolean registerTransactionActive = false;
        //当事务启动同步且当前事务处于活动状态，则进行注册
        if (TransactionSynchronizationManager.isSynchronizationActive() &&
                TransactionSynchronizationManager.isActualTransactionActive()) {
            registerTransactionActive = true;
            List<Runnable> runnables = currentRunablesCombineThreadLocal.get();
            //同一个事务的合并到一起处理
            if(CollectionUtils.isEmpty(runnables)){
                runnables = new ArrayList<>();
                currentRunablesCombineThreadLocal.set(runnables);
                TransactionSynchronizationManager.registerSynchronization(this);
            }
            runnables.add(runnable);

        }

        return registerTransactionActive;
    }
}
