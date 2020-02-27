package com.gongsi.app.config;

import com.gongsi.app.config.jmx.ProfilingController;
import com.gongsi.app.persistence.UserDao;
import com.gongsi.app.service.UserService;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class<?>> map = new HashMap<>();
    @Autowired
    private ProfilingController profilingController;/* = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        ManagementFactory.getPlatformMBeanServer()
                .registerMBean(profilingController, new ObjectName("profiling", "name", "controller"));
    }*/

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName)
            throws BeansException {
        if (bean instanceof UserService || bean instanceof UserDao) {
            map.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        Class<?> beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (!profilingController.isEnabled()) {
                                return method.invoke(bean, args);
                            }

                            long before = System.currentTimeMillis();
                            Object returnValue = method.invoke(bean, args);
                            long after = System.currentTimeMillis();
                            String profilingMessage = getProfilingMessage(method, before, after, bean);
                            System.out.println(profilingMessage);

                            return returnValue;
                        }
                    });
        }
        return bean;
    }

    private String getProfilingMessage(Method method, long before, long after,
                                       @NonNull Object bean) {
        //bean.getClass().getSuperclass() since some of them wrapped into Spring AOP proxy(because of @Transactional)
        String className = bean.getClass().getSimpleName();
        int index = className.indexOf('$');
        if (index >= 0) {
            className = className.substring(0, index);
        }
        return String.format("%s::%s execution time: %d (ms)", className,
                method.getName(), (after - before));
    }
}
