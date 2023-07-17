package com.github.lybgeek.apolloconfig.model;


import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenReleaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenItemAndReleaseDTO {

    private OpenItemDTO openItemDTO;

    private NamespaceReleaseDTO releaseDTO;

    private Set<String> interestedKeys = new LinkedHashSet<>();

}
