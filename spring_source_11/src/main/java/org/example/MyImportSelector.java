package org.example;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ClassName:MyImportSelector
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/27 16:20
 * @Author:qs@1.com
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MySelectImports.class.getName()};
    }
}
