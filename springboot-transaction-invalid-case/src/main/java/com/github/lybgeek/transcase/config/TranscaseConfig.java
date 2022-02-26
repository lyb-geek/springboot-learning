package com.github.lybgeek.transcase.config;


import com.github.lybgeek.transcase.serviceWithoutInjectSpring.TranInvalidCaseWithoutInjectSpring;
import com.github.lybgeek.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranscaseConfig  {



    @Bean
    public TranInvalidCaseWithoutInjectSpring tranInvalidCaseWithoutInjectSpring(UserService userService){
        return new TranInvalidCaseWithoutInjectSpring(userService);
    }








    //    @Bean
//    public TransactionAttributeSource transactionAttributeSource(){
//        return new AnnotationTransactionAttributeSource(false);
//    }


}
