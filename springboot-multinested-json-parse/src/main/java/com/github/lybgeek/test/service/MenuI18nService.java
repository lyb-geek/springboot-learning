package com.github.lybgeek.test.service;


import com.github.lybgeek.json.service.I18nService;
import com.github.lybgeek.json.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.lybgeek.test.constant.MenuI18nCodeConstant.*;

@Service
@Primary
@RequiredArgsConstructor
public class MenuI18nService implements I18nService, InitializingBean {
    
    public static final Map<String,String> mockI18nCache = new ConcurrentHashMap<>();

    private final MockMenuService mockMenuService;
    
    @Override
    public String getTargetContent(String code) {
       return mockI18nCache.get(code);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        mockI18nCache.put(TOP_MENU_KEY,"topMenu");
        mockI18nCache.put(USER_MENU_KEY,"userMenu");
        mockI18nCache.put(USER_MENU_ADD_KEY,"userMenuAdd");
        mockI18nCache.put(USER_MENU_UPDATE_KEY,"userUpdateAdd");
        mockI18nCache.put(DEPT_MENU_KEY,"deptMenu");
        mockI18nCache.put(DEPT_MENU_LIST_KEY,"deptMenuList");
        mockI18nCache.put(DEPT_MENU_DELETE_KEY,"deptMenuDelete");

        printMenuI18nCodeByOgnl();

        System.out.println(mockMenuService.reBuildMenuJson());

    }

    private void printMenuI18nCodeByOgnl() throws Exception {
        String menuJson = mockMenuService.getMenuJson();
        Map<String, Object> map = JsonUtil.parse(menuJson, Map.class);
        /**
         * {@link https://blog.51cto.com/rickcheung/238578}
         */
        Object topMenu = JsonUtil.getValue( map,"i18NCode");
        Object userMenu = JsonUtil.getValue( map,"children[0].i18NCode");
        Object userMenuAdd = JsonUtil.getValue( map,"children[0].children[0].i18NCode");
        Object userMenuUpdate = JsonUtil.getValue( map,"children[0].children[1].i18NCode");
        Object deptMenu = JsonUtil.getValue( map,"children[1].i18NCode");
        Object deptMenuList = JsonUtil.getValue( map,"children[1].children[0].i18NCode");
        Object deptMenuDelete = JsonUtil.getValue( map,"children[1].children[1].i18NCode");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Print MenuI18nCode By Ognl Start <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,");
        System.out.println(topMenu);
        System.out.println(userMenu);
        System.out.println(userMenuAdd);
        System.out.println(userMenuUpdate);
        System.out.println(deptMenu);
        System.out.println(deptMenuList);
        System.out.println(deptMenuDelete);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Print MenuI18nCode By Ognl End <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,");
    }
}
