package com.github.lybgeek.chaincontext.parser;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The default key parser, either get key info from header or if null get from parameter
 * 
 * @author linyb
 *
 */
public class DefaultRequestKeyParser implements IRequestKeyParser {

    @Override
    public String parse(String key, HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(key)).orElse(request.getParameter(key));
    }

}
