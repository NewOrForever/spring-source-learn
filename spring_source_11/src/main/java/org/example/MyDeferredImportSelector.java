package org.example;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ClassName:MyDeferredImportSelector
 * Package:org.example
 * Description:
 *
 * @Date:2022/1/27 16:41
 * @Author:qs@1.com
 */
public class MyDeferredImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MyDeferredSelectImports.class.getName()};
    }
}
