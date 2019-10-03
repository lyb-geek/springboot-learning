package com.github.lybgeek.common.swagger.version.config;

import com.github.lybgeek.common.swagger.version.util.ApiVersionUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义匹配requestMapping的请求匹配规则
 */
@Data
@Slf4j
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
 
    /**
     * 接口路径中的版本号前缀，如: api/1.0.0/user/config
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("/([1-9]\\d|[1-9])(\\.([1-9]\\d|\\d)){2}/");

    /**
     * 当前版本号
     */
    private String curApiVersion;
 
    ApiVersionCondition(String apiVersion) {
        this.curApiVersion = apiVersion;
    }

    /**
     * 同另一个condition组合，例如，方法和类都配置了@RequestMapping的url，可以组合
     * @param other 为方法级别配置的版本号
     */
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 最近优先原则，方法定义的 @ApiVersion > 类定义的 @ApiVersion
        //直接返回方法的版本，可以保证方法优先
        return new ApiVersionCondition(other.getCurApiVersion());
    }

    /**
     * 检查request是否匹配，可能会返回新建的对象，例如，如果规则配置了多个模糊规则，可能当前请求
     * 只满足其中几个，那么只会返回这几个条件构建的Condition
     */
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {

        // 正则匹配请求的uri，看是否有版本号
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            // 获得符合匹配条件的ApiVersionCondition
            String requestUriApiVersion = ApiVersionUtil.withoutHeadAndTailDiagonal(m.group());
            Integer compareResult = ApiVersionUtil.INSTANCE.compareTo(requestUriApiVersion,getCurApiVersion());
           // 低于最低的版本号均返回不匹配
            if (compareResult >= 0) {
                return this;
            }
        }

        return null;
    }

    /**
     * 比较，请求同时满足多个Condition时，可以区分优先使用哪一个
     */
    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 当出现多个符合匹配条件的ApiVersionCondition，优先匹配版本号较大的
        return ApiVersionUtil.INSTANCE.compareTo(other.getCurApiVersion(),getCurApiVersion());
    }


 
}