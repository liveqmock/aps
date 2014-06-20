/*
 * 2014 Chengdu JunChen Technology
 */
package com.mc.printer.server.utils;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author 305027939
 */
public class NetInterceptAdapter extends HandlerInterceptorAdapter {

    private static final Log log = LogFactory.getLog(NetInterceptAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        log.debug("Begin to Filter session");

        /*二维码生成也放在这里*/
        //
      

        return super.preHandle(request, response, handler);
    }

}
