package org.example.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:MyAnnotationApplicationContext
 * Package:org.example.spring
 * Description:
 *
 * @Date:2021/12/21 10:57
 * @Author:qs@1.com
 */
public class MyAnnotationConfigApplicationContext {

    public static final String SINGLETON = "singleton";
    private Class<?> configClazz;
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final Map<String, Object>  singletonObjects = new HashMap<>();
    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public MyAnnotationConfigApplicationContext() {
    }

    public MyAnnotationConfigApplicationContext(Class<?> configClazz) {
        this.configClazz = configClazz;
        // 注册configClazz，创建beandefinition
        BeanDefinition configBd = new BeanDefinition();
        configBd.setType(configClazz);
        configBd.setScope(SINGLETON);
        beanDefinitionMap.put(Introspector.decapitalize(configClazz.getSimpleName()), configBd);

        // 扫描componentscan指定的路径，将所有的component放入beanDefinitionMap中
        componentScan(configClazz);

        // 遍历beanmap，将单例bean放到单例池中
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.getScope().equals(SINGLETON) && !beanDefinition.getLazy()) {
                // 单例bean则放入单例bean缓存池
                Object singletonObject = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, singletonObject);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        Object instance= null;

        try {
            instance = clazz.getConstructor().newInstance();

            // 依赖注入（属性赋值）
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }
            }

            // postconstruct注解
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    method.invoke(instance);
                }
            }

            // aware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware)instance).setBeanName(beanName);
            }

            // beanpostprocessor，初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            // initializationbean接口
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // beanpostprocessor，初始化后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }


    public Object getBean(String beanName) {
        // 根据beanname去获取bean
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException("没找到beanname对应的bean");
        }
        // 单例bean则取singletonobjects中获取
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.getScope().equals(SINGLETON)){
            Object singletonObject = singletonObjects.get(beanName);
            // 懒加载的话一开始不创建bean
            if (singletonObject == null){
                singletonObject = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, singletonObject);
            }
            return singletonObject;
        }
        else {
            // prototype
            Object prototypeObject = createBean(beanName, beanDefinition);
            return prototypeObject;
        }

    }

    private void componentScan(Class<?> configClazz) {
        if (configClazz.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnno = configClazz.getAnnotation(ComponentScan.class);
            String path = componentScanAnno.value().replace(".", "/"); // org/example/my
            ClassLoader classLoader = MyAnnotationConfigApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getPath());
            if (file.isDirectory()) {
                for (File childFile : file.listFiles()) {
                    String absolutePath = childFile.getAbsolutePath(); // file的绝对路径名
                    absolutePath = absolutePath.substring(absolutePath.indexOf("org"), absolutePath.indexOf(".class"));
                    absolutePath = absolutePath.replace("\\", "."); // org.example.my.UserService
                    try {
                        // 获取所有有component注解的类，并将class放入map
                        Class<?> clazz = classLoader.loadClass(absolutePath);
                        if (clazz.isAnnotationPresent(Component.class)) {
                            // 为beandefinition复赋值
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setType(clazz);
                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if (beanName.isEmpty()) {
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }

                            // 实现BeanPostProcessor的类，存放到对应map
                            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                beanPostProcessorList.add((BeanPostProcessor) clazz.getConstructor().newInstance());
                            }

                            // scope注解,有则设置值，没有则是默认单例
                            if (clazz.isAnnotationPresent(Scope.class)){
                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                String scopeValue = scopeAnnotation.value();
                                scopeValue = scopeValue == null || scopeValue.isEmpty() ? SINGLETON : scopeValue;
                                beanDefinition.setScope(scopeValue);
                            }
                            else{
                                beanDefinition.setScope(SINGLETON);
                            }

                            beanDefinitionMap.put(beanName, beanDefinition);
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
