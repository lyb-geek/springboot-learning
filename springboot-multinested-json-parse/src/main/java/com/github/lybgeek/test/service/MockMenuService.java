package com.github.lybgeek.test.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.lybgeek.test.model.MenuResourceDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.github.lybgeek.test.constant.MenuI18nCodeConstant.*;
import static com.github.lybgeek.test.model.MenuResourceDTO.CHILDREN_COLUMN;
import static com.github.lybgeek.test.model.MenuResourceDTO.I18N_CODE_COLUMN;
import static com.github.lybgeek.test.service.MenuI18nService.mockI18nCache;

@Service
public class MockMenuService {

    public MenuResourceDTO getMenuResourceDTO(){
        MenuResourceDTO parentMenu = new MenuResourceDTO();
        parentMenu.setMenuName("顶级菜单");
        parentMenu.setI18NCode(TOP_MENU_KEY);
        parentMenu.setComponent("saas/index");
        parentMenu.setLinkUrl("/topUrl");
        parentMenu.setParentId(0);
        parentMenu.setId(1);
        parentMenu.setSort(9999);
        List<MenuResourceDTO> childMenus = new ArrayList<>();


        MenuResourceDTO userMenu = new MenuResourceDTO();
        userMenu.setMenuName("用户菜单");
        userMenu.setI18NCode(USER_MENU_KEY);
        userMenu.setComponent("saas/index");
        userMenu.setLinkUrl("/user");
        userMenu.setId(9);
        userMenu.setParentId(1);
        userMenu.setSort(9999);

        List<MenuResourceDTO> userChildMenus = new ArrayList<>();
        MenuResourceDTO userAddMenu = new MenuResourceDTO();
        userAddMenu.setMenuName("用户新增");
        userAddMenu.setI18NCode(USER_MENU_ADD_KEY);
        userAddMenu.setComponent("saas/index");
        userAddMenu.setLinkUrl("/user/add");
        userAddMenu.setId(8);
        userAddMenu.setParentId(9);
        userAddMenu.setSort(9999);
        userChildMenus.add(userAddMenu);

        MenuResourceDTO userUpdateMenu = new MenuResourceDTO();
        userUpdateMenu.setMenuName("用户编辑");
        userUpdateMenu.setI18NCode(USER_MENU_UPDATE_KEY);
        userUpdateMenu.setComponent("saas/index");
        userUpdateMenu.setLinkUrl("/user/update");
        userUpdateMenu.setId(7);
        userUpdateMenu.setParentId(9);
        userUpdateMenu.setSort(9999);
        userChildMenus.add(userUpdateMenu);
        userMenu.setChildren(userChildMenus);


        MenuResourceDTO deptMenu = new MenuResourceDTO();
        deptMenu.setMenuName("部门菜单");
        deptMenu.setI18NCode(DEPT_MENU_KEY);
        deptMenu.setComponent("saas/index");
        deptMenu.setId(10);
        deptMenu.setParentId(1);
        deptMenu.setSort(9999);
        deptMenu.setLinkUrl("/dept");

        List<MenuResourceDTO> deptMenuChildMenus = new ArrayList<>();
        MenuResourceDTO deptListMenu = new MenuResourceDTO();
        deptListMenu.setMenuName("部门列表");
        deptListMenu.setI18NCode(DEPT_MENU_LIST_KEY);
        deptListMenu.setComponent("saas/index");
        deptListMenu.setLinkUrl("/dept/list");
        deptListMenu.setId(11);
        deptListMenu.setParentId(10);
        deptListMenu.setSort(9999);
        deptMenuChildMenus.add(deptListMenu);

        MenuResourceDTO deptDeleteMenu = new MenuResourceDTO();
        deptDeleteMenu.setMenuName("部门删除");
        deptDeleteMenu.setI18NCode(DEPT_MENU_DELETE_KEY);
        deptDeleteMenu.setComponent("saas/index");
        deptDeleteMenu.setLinkUrl("/dept/delete");
        deptDeleteMenu.setId(12);
        deptDeleteMenu.setParentId(10);
        deptDeleteMenu.setSort(9999);
        deptMenuChildMenus.add(deptDeleteMenu);
        deptMenu.setChildren(deptMenuChildMenus);



        childMenus.add(userMenu);
        childMenus.add(deptMenu);
        parentMenu.setChildren(childMenus);

        return parentMenu;
    }

    public String getMenuJson(){
        return JSON.toJSONString(getMenuResourceDTO());
    }

    public String reBuildMenuJson(){
        String orginalMenuJson = getMenuJson();
        JSONObject jsonObject = JSON.parseObject(orginalMenuJson);
        jsonObject.put(I18N_CODE_COLUMN,mockI18nCache.get(jsonObject.get(I18N_CODE_COLUMN)));
        reBuildChildJson(jsonObject);
        return JSON.toJSONString(jsonObject);

    }

    private void reBuildChildJson(JSONObject curentObject){
        JSONArray children = curentObject.getJSONArray(CHILDREN_COLUMN);
        for (int i = 0; i < children.size(); i++) {
            JSONObject child = children.getJSONObject(i);
            child.put(I18N_CODE_COLUMN,mockI18nCache.get(child.get(I18N_CODE_COLUMN)));
            reBuildChildJson(child);
        }

    }
}
