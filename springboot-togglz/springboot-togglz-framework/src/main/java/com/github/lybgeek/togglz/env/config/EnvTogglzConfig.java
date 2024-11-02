package com.github.lybgeek.togglz.env.config;


import com.github.lybgeek.togglz.env.factory.StateRepositoryFactory;
import com.github.lybgeek.togglz.env.feature.EnvFeature;
import com.github.lybgeek.togglz.env.properties.EnvTogglzProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.CollectionUtils;
import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.user.SingleUserProvider;
import org.togglz.core.user.UserProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://www.togglz.org/documentation/configuration">...</a>
 */

@Slf4j
@RequiredArgsConstructor
public class EnvTogglzConfig implements TogglzConfig , InitializingBean {

    private final ObjectProvider<List<StateRepositoryFactory>> listObjectProvider;
    private final EnvTogglzProperties envTogglzProperties;

    private Map<String,StateRepositoryFactory> stateRepositoryMap;
    @Override
    public Class<? extends Feature> getFeatureClass() {
        return EnvFeature.class;
    }

    /***
     * @see <a href="https://www.togglz.org/documentation/repositories"></a>
     * @return
     */
    @Override
    public StateRepository getStateRepository() {
        if(stateRepositoryMap == null || stateRepositoryMap.isEmpty()){
            log.warn("stateRepositoryFactories is empty,select inMemoryStateRepository");
            return new InMemoryStateRepository();
        }

        return stateRepositoryMap.get(envTogglzProperties.getStateRepositoryType()).create();

    }

    /**
     * @see <a href="https://www.togglz.org/documentation/authentication"></a>
     * @return
     */
    @Override
    public UserProvider getUserProvider() {
      return new SingleUserProvider("lybgeek", true);
    }

    @Override
    public void afterPropertiesSet() {

        List<StateRepositoryFactory> stateRepositoryFactories = listObjectProvider.getIfAvailable();
        if(CollectionUtils.isEmpty(stateRepositoryFactories)){
            return;
        }
        stateRepositoryMap = new HashMap<>(stateRepositoryFactories.size());
        stateRepositoryFactories.forEach(stateRepositoryFactory -> stateRepositoryMap.put(stateRepositoryFactory.supportType(),stateRepositoryFactory));


    }
}
