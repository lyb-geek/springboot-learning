package com.github.lybgeek.gateway.predicate;

import com.github.lybgeek.gateway.util.HttpRequestParserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class ParamRoutePredicateFactory
		extends AbstractRoutePredicateFactory<ParamRoutePredicateFactory.Config> {

	public static final String PARAM_KEY = "param";

	public static final String PARAM_VALUES = "values";

	public static final String SEPARATOR = "&";

	public ParamRoutePredicateFactory() {
		super(Config.class);
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList(PARAM_KEY,PARAM_VALUES);
	}

	@Override
	public ShortcutType shortcutType() {
		return ShortcutType.DEFAULT;
	}

	@Override
	public Predicate<ServerWebExchange> apply(Config config) {
		return exchange -> isHitTargetParam(config, exchange);
	}

	private boolean isHitTargetParam(Config config, ServerWebExchange exchange) {
		boolean hasParamkey = HttpRequestParserUtils.hasKey(config.param.toLowerCase(), exchange);
		if(hasParamkey){
			String value = HttpRequestParserUtils.parse(config.param.toLowerCase(), exchange);
			if(StringUtils.hasText(config.values) && config.values.contains(SEPARATOR)){
				String[] valueArr = config.values.split(SEPARATOR);
				for (String targetValue : valueArr) {
					if(targetValue.equals(value)){
						log.info(">>>>>>>>>>>>>>>>>>>> Request Key --> 【{}】 Hit Value --> 【{}】 In Target Values 【{}】", config.param,value, config.values);
						return true;
					}
				}
			}

		}
		return false;
	}

	@Validated
	public static class Config {

		@NotEmpty
		private String param;

		private String values;

		public String getParam() {
			return param;
		}

		public Config setParam(String param) {
			this.param = param;
			return this;
		}

		public String getValues() {
			return values;
		}

		public Config setValues(String values) {
			this.values = values;
			return this;
		}

		@Override
		public String toString() {
			return "Config{" +
					"param='" + param + '\'' +
					", values=" + values +
					'}';
		}
	}

}
