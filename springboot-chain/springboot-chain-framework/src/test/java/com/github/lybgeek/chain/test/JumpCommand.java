package com.github.lybgeek.chain.test;


import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;

public class JumpCommand implements Filter {

    /**
     * 执行当前操作
     *
     * 此方法应由具体的操作实现类来覆盖，以提供具体的操作逻辑
     * 它负责根据给定的上下文执行业务逻辑，并返回一个布尔值表示执行结果
     *
     * @param context 执行操作的上下文，包含操作所需的信息和环境设置
     * @return boolean 表示操作执行的结果，true表示成功，false表示失败
     * @throws Exception 如果执行过程中发生错误，抛出异常
     */
    @Override
    public boolean execute(Context context) throws Exception {
        Object jump = context.get("jumpEnabled");
        boolean jumpEnabled = jump != null && "true".equalsIgnoreCase(jump.toString());
        System.out.println("拥有跳跃的能力:" + jumpEnabled);
        return jumpEnabled;
    }

    /**
     * 在跳跃能力判断完成后进行后处理
     *
     *
     * @param context 上下文环境，可能包含执行判断跳跃能力前后的相关环境信息
     * @param exception 在判断跳跃能力过程中可能发生的异常，如果没有异常，则为null
     * @return boolean 表示跳跃能力判断是否成功完成如果没有异常，则返回true，否则返回false
     */
    @Override
    public boolean postprocess(Context context, Exception exception) {
        System.out.println("判定跳跃能力完毕...");
        if(exception != null){
            System.out.println("执行异常:" + exception.getMessage());
        }
        return exception == null;
    }
}
