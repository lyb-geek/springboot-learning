package com.github.lybgeek.transactional.event;


import com.github.lybgeek.remote.RpcMockService;
import com.github.lybgeek.user.constant.Constant;
import com.github.lybgeek.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionalListenerEvent {
    @Autowired
    private RpcMockService rpcMockService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void executeRpcCall(User user){
        if(Constant.MOCK_EXCEPTION_USER.equals(user.getUsername())){
            rpcMockService.mockExceptionCall(user);
        }else{
            rpcMockService.mockLongTimeNotity(user);
        }
    }
}
