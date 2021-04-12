package com.github.lybgeek.sentinel.fallback;

import com.github.lybgeek.common.model.AjaxResult;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: 降级回调方法
 *
 **/
public class SentinelRulesTestControllerFallBack {


    /**
     * 细节点：对应的处理方法必须static修饰;返回类型与原方法一致;参数类型需要和原方法相匹配，Sentinel 1.6开始，也可在方法最后加 Throwable 类型的参数。
     * @param msg
     * @param e
     * @see <a href="https://www.jianshu.com/p/48dbcea80d96">https://www.jianshu.com/p/48dbcea80d96</a>
     * @return
     */
    public static AjaxResult<String> fallback(@PathVariable("msg") String msg,Throwable e){
        System.out.println(String.format("msg : %s,exception:%s",msg,e.getMessage()));
        return AjaxResult.error("触发熔断降级回调",500);
    }
}
