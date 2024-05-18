package com.github.lybgeek.thirdparty.autoconfigure;

import com.github.lybgeek.thirdparty.repository.ThirdpartyRepository;
import com.github.lybgeek.thirdparty.service.ThirdpartyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class ThirdpartyAutoConfiguration {


    @Bean
    @Primary
    public ThirdpartyRepository thirdpartyRepository(){
        return new ThirdpartyRepository();
    }

    @Bean
    public ThirdpartyService thirdpartyService(ThirdpartyRepository thirdpartyRepository){
        return new ThirdpartyService(thirdpartyRepository);
    }
}
