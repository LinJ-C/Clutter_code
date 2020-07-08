package com.markerhub.util;

import com.markerhub.shiro.AccoutProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author: Joe
 * @created: 2020/7/2 10:23
 */
public class ShiroUtil {

    public static AccoutProfile getProfile(){
        return (AccoutProfile)SecurityUtils.getSubject().getPrincipal();
    }
}
