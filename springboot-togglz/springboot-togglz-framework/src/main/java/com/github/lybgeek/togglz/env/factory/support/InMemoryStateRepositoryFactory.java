package com.github.lybgeek.togglz.env.factory.support;


import com.github.lybgeek.togglz.env.factory.StateRepositoryFactory;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;

import static com.github.lybgeek.togglz.env.constant.TogglzConstant.STORE_TYPE_INMEMORY;

public class InMemoryStateRepositoryFactory implements StateRepositoryFactory {
    @Override
    public StateRepository create() {
        return new InMemoryStateRepository();
    }

    @Override
    public String supportType() {
        return STORE_TYPE_INMEMORY;
    }


}
