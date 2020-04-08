package com.order.dining.utils;

import com.order.dining.common.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: baojx
 * @Date: 2020/04/08 14:16
 * @Desc: Cookie工具类，用于读取用户Cookie数据
 */
public class CookieUtil {

    public static void set(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request,
                             String name) {
        Map<String, Cookie> cookieMap = getCookie(request);
        return cookieMap.getOrDefault(name, null);
    }

    public static void remove(HttpServletRequest request, HttpServletResponse response,
                              String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), name)) {
                    System.out.println("移除:" + cookie.getName());
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }

    private static Map<String, Cookie> getCookie(HttpServletRequest request) {
        Map<String, Cookie> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie);
            }
        }
        return map;
    }

}
