package com.github.lybgeek.chain.delegete;


import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;

import java.util.List;

@RequiredArgsConstructor
public class CommandDelegete implements Command, InitializingBean {

    private final ObjectProvider<List<Command>> commandObjectProvider;

    private final Chain chain = new ChainBase();


    @Override
    public boolean execute(Context context) throws Exception {
        return chain.execute(context);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Command> commands = commandObjectProvider.getIfAvailable();
        if(CollectionUtil.isNotEmpty(commands)){
            commands.forEach(chain::addCommand);
        }

    }
}
