package com.example.common.utils;

/**
 * @author ToTryEveryThing
 * @date 2024/1/14 11:39
 * @Description
 */
public class verifyRole {

    public static Boolean verify(String userRole, String controllerRole){
        if(userRole.equals("admin")) return true;
        return userRole.equals("user") && controllerRole.equals("user");
    }

}
