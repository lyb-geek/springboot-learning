package com.github.lybgeek.chain.autoconfigure;

import com.github.lybgeek.chain.delegete.CommandDelegete;
import org.apache.commons.chain.Command;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class CommandAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public CommandDelegete commandDelegete(ObjectProvider<List<Command>> commandObjectProvider){
        return new CommandDelegete(commandObjectProvider);

    }






}
