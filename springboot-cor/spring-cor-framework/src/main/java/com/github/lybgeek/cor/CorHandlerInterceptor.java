package com.github.lybgeek.cor;


import com.github.lybgeek.cor.chain.MethodInterceptorChain;
import com.github.lybgeek.cor.handler.AbstarctHandler;
import com.github.lybgeek.cor.model.Invocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorHandlerInterceptor {

    private MethodInterceptorChain chain;


    public Object invoke(Invocation invocation) throws Exception {
        List<AbstarctHandler> abstarctHandlers = chain.getHanlders();
        if(CollectionUtils.isEmpty(abstarctHandlers)){
            invocation.invoke();
        }

        boolean isCanExec = true;
        for (AbstarctHandler abstarctHandler : abstarctHandlers) {
             if(!abstarctHandler.preHandler(invocation)){
                 isCanExec = false;
                 break;
             }
        }

           try{
               if(isCanExec){
                   return invocation.invoke();
               }
           }catch (Exception e){
               throw new Exception(e);
           }finally {
//               Collections.reverse(abstarctHandlers);
//               for (AbstarctHandler abstarctHandler : abstarctHandlers) {
//                   abstarctHandler.afterCompletion(invocation);
//               }

               for (int i = 0; i < abstarctHandlers.size(); i++) {
                   int j = abstarctHandlers.size() - 1 - i;
                   abstarctHandlers.get(j).afterCompletion(invocation);
               }
           }

        return null;
    }



}
