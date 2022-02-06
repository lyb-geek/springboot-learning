package com.github.lybgeek.gateway.dashboard.route.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteRule {

    private String routeId;

    private String uri;

    private String predicate;

    private String filter;
}
