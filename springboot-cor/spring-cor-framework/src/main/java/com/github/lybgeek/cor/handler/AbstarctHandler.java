package com.github.lybgeek.cor.handler;


import com.github.lybgeek.cor.model.Invocation;
import org.springframework.core.Ordered;

public interface AbstarctHandler extends Ordered {

    /**
     * 预处理回调，实现服务的预处理
     * @return true表示流程继续，false表示流程中断，不会继续调用其他处理器或者服务
     */
    default boolean preHandler(Invocation invocation){
        return true;
    }

    /**
     * 整个请求处理完毕回调方法。类似try-catch-finally中的finally。多个afterCompletion按倒叙输出
     */
    default void afterCompletion(Invocation invocation){}


}
