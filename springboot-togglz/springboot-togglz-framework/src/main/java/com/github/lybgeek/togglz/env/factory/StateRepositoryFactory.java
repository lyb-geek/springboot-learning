package com.github.lybgeek.togglz.env.factory;


import org.togglz.core.repository.StateRepository;

public interface StateRepositoryFactory {

    StateRepository create();

    String supportType();
}
