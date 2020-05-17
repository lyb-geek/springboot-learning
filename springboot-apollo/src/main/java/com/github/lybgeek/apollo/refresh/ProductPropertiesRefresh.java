package com.github.lybgeek.apollo.refresh;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.github.lybgeek.apollo.model.Product;
import com.github.lybgeek.apollo.util.PrintChangeKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductPropertiesRefresh {

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private Product product;


    @ApolloConfigChangeListener(value="product.properties",interestedKeyPrefixes = {"product."})
    private void refresh(ConfigChangeEvent changeEvent){

        refreshScope.refresh("product");

        PrintChangeKeyUtils.printChange(changeEvent);
    }






}
