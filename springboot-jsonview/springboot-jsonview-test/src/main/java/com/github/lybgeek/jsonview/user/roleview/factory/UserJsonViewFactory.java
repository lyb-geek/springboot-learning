package com.github.lybgeek.jsonview.user.roleview.factory;


import com.github.lybgeek.jsonview.factory.JsonViewFactory;
import com.github.lybgeek.jsonview.support.PublicJsonView;
import com.github.lybgeek.jsonview.user.roleview.AdminJsonView;
import com.github.lybgeek.jsonview.user.roleview.UserJsonView;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserJsonViewFactory implements JsonViewFactory {

    private Map<String,Class<? extends PublicJsonView>> jsonViewClassMap = new ConcurrentHashMap<>();
    @Override
    public Class<? extends PublicJsonView> getJsonViewClass(HttpServletRequest request,Object... body) {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            return null;
        }
        return jsonViewClassMap.get(token);
    }

    @Override
    public void initJsonViewClasses() {
        jsonViewClassMap.put("123", PublicJsonView.class);
        jsonViewClassMap.put("456", UserJsonView.class);
        jsonViewClassMap.put("789", AdminJsonView.class);
    }
}
