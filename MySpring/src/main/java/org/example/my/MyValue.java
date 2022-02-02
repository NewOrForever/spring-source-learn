package org.example.my;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:MyValue
 * Package:org.example.my
 * Description:
 *
 * @Date:2021/12/22 16:19
 * @Author:qs@1.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyValue {
    String value() default "";
}
