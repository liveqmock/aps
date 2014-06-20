package com.mc.printer.server.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mc.printer.server.constants.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author woderchen@gmail.com
 */
public class BeanFactory {

    private static final Log log = LogFactory.getLog(BeanFactory.class);
    private static ApplicationContext application;
    private static BeanFactory beanfactory;

    public static BeanFactory instance() {
        if (beanfactory == null) {
            beanfactory = new BeanFactory();
            String classPath
                    = ResourceReader.getString(Constants.CONFIG_XMLPATH);

            if (log.isDebugEnabled()) {
                log.debug("application classpath:" + classPath);
            }
            String[] classPaths = classPath.split(",");
            application = new ClassPathXmlApplicationContext(classPaths);
        }
        return beanfactory;
    }

    public Object createBean(Class classMapper) {
        return application.getBean(classMapper);
    }

    public Object createBean(String beanName) {
        return application.getBean(beanName);
    }
}
