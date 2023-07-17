package com.github.lybgeek.apolloconfig.service;

import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.github.lybgeek.apolloconfig.model.OpenItemAndReleaseDTO;

import java.util.LinkedHashSet;
import java.util.Set;

@FunctionalInterface
public interface ApolloConfigService {

    boolean publishAndListenerItem(OpenItemAndReleaseDTO openItemAndReleaseDTO);

    default boolean publishAndListenerItem(String key,String value){
        OpenItemDTO openItemDTO = new OpenItemDTO();
        openItemDTO.setKey(key);
        openItemDTO.setValue(value);


        NamespaceReleaseDTO releaseDTO = new NamespaceReleaseDTO();
        releaseDTO.setReleaseTitle("publish key: " + key);

        Set<String> interestedKeys = new LinkedHashSet<>();
        interestedKeys.add(key);

        OpenItemAndReleaseDTO openItemAndReleaseDTO = OpenItemAndReleaseDTO
                .builder().openItemDTO(openItemDTO)
                .releaseDTO(releaseDTO)
                .interestedKeys(interestedKeys)
                .build();

        return publishAndListenerItem(openItemAndReleaseDTO);


    }

    default void registerListener(String key){

    }
}
