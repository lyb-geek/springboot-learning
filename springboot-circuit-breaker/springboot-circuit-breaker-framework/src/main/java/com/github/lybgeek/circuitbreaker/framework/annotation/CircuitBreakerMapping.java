package com.github.lybgeek.circuitbreaker.framework.annotation;


import com.alibaba.csp.sentinel.EntryType;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

import static com.alibaba.csp.sentinel.ResourceTypeConstants.COMMON_WEB;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface CircuitBreakerMapping {

    //----------------RequestMapping-------------------------------
    /**
     * Assign a name to this mapping.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used on both levels, a combined name is derived by concatenation
     * with "#" as separator.
     * @see org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
     * @see org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy
     */
    String name() default "";

    /**
     * The primary mapping expressed by this annotation.
     * <p>This is an alias for {@link #path}. For example
     * {@code @RequestMapping("/foo")} is equivalent to
     * {@code @RequestMapping(path="/foo")}.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this primary mapping, narrowing it for a specific handler method.
     */
    @AliasFor("path")
    String[] value() default {};

    /**
     * The path mapping URIs (e.g. "/myPath.do").
     * Ant-style path patterns are also supported (e.g. "/myPath/*.do").
     * At the method level, relative paths (e.g. "edit.do") are supported
     * within the primary mapping expressed at the type level.
     * Path mapping URIs may contain placeholders (e.g. "/${connect}").
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this primary mapping, narrowing it for a specific handler method.
     * @see org.springframework.web.bind.annotation.ValueConstants#DEFAULT_NONE
     * @since 4.2
     */
    @AliasFor("value")
    String[] path() default {};

    /**
     * The HTTP request methods to map to, narrowing the primary mapping:
     * GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this HTTP method restriction (i.e. the type-level restriction
     * gets checked before the handler method is even resolved).
     */
    RequestMethod[] method() default {};

    /**
     * The parameters of the mapped request, narrowing the primary mapping.
     * <p>Same format for any environment: a sequence of "myParam=myValue" style
     * expressions, with a request only mapped if each such parameter is found
     * to have the given value. Expressions can be negated by using the "!=" operator,
     * as in "myParam!=myValue". "myParam" style expressions are also supported,
     * with such parameters having to be present in the request (allowed to have
     * any value). Finally, "!myParam" style expressions indicate that the
     * specified parameter is <i>not</i> supposed to be present in the request.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this parameter restriction (i.e. the type-level restriction
     * gets checked before the handler method is even resolved).
     * <p>Parameter mappings are considered as restrictions that are enforced at
     * the type level. The primary path mapping (i.e. the specified URI value)
     * still has to uniquely identify the target handler, with parameter mappings
     * simply expressing preconditions for invoking the handler.
     */
    String[] params() default {};

    /**
     * The headers of the mapped request, narrowing the primary mapping.
     * <p>Same format for any environment: a sequence of "My-Header=myValue" style
     * expressions, with a request only mapped if each such header is found
     * to have the given value. Expressions can be negated by using the "!=" operator,
     * as in "My-Header!=myValue". "My-Header" style expressions are also supported,
     * with such headers having to be present in the request (allowed to have
     * any value). Finally, "!My-Header" style expressions indicate that the
     * specified header is <i>not</i> supposed to be present in the request.
     * <p>Also supports media type wildcards (*), for headers such as Accept
     * and Content-Type. For instance,
     * <pre class="code">
     * &#064;RequestMapping(value = "/something", headers = "content-type=text/*")
     * </pre>
     * will match requests with a Content-Type of "text/html", "text/plain", etc.
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this header restriction (i.e. the type-level restriction
     * gets checked before the handler method is even resolved).
     * @see org.springframework.http.MediaType
     */
    String[] headers() default {};

    /**
     * The consumable media types of the mapped request, narrowing the primary mapping.
     * <p>The format is a single media type or a sequence of media types,
     * with a request only mapped if the {@code Content-Type} matches one of these media types.
     * Examples:
     * <pre class="code">
     * consumes = "text/plain"
     * consumes = {"text/plain", "application/*"}
     * </pre>
     * Expressions can be negated by using the "!" operator, as in "!text/plain", which matches
     * all requests with a {@code Content-Type} other than "text/plain".
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings override
     * this consumes restriction.
     * @see org.springframework.http.MediaType
     * @see javax.servlet.http.HttpServletRequest#getContentType()
     */
    String[] consumes() default {};

    /**
     * The producible media types of the mapped request, narrowing the primary mapping.
     * <p>The format is a single media type or a sequence of media types,
     * with a request only mapped if the {@code Accept} matches one of these media types.
     * Examples:
     * <pre class="code">
     * produces = "text/plain"
     * produces = {"text/plain", "application/*"}
     * produces = MediaType.APPLICATION_JSON_UTF8_VALUE
     * </pre>
     * <p>It affects the actual content type written, for example to produce a JSON response
     * with UTF-8 encoding, {@link org.springframework.http.MediaType#APPLICATION_JSON_UTF8_VALUE} should be used.
     * <p>Expressions can be negated by using the "!" operator, as in "!text/plain", which matches
     * all requests with a {@code Accept} other than "text/plain".
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings override
     * this produces restriction.
     * @see org.springframework.http.MediaType
     */
    String[] produces() default {};



    //------------------------CircuitBreaker-------------------------------------

    EntryType entryType() default EntryType.OUT;

    int resourceType() default COMMON_WEB;

    String blockHandler() default "";

    Class<?>[] blockHandlerClass() default {};

    String fallback() default "";

    String defaultFallback() default "";

    Class<?>[] fallbackClass() default {};

    Class<? extends Throwable>[] exceptionsToTrace() default {Throwable.class};

    Class<? extends Throwable>[] exceptionsToIgnore() default {};
}
