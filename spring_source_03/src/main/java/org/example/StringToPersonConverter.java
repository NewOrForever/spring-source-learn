package org.example;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * ClassName:StringToUserConverter
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/24 15:52
 * @Author:qs@1.com
 */
public class StringToPersonConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType().equals(String.class) && targetType.getType().equals(Person.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Person.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Person person = new Person();
        person.setId(Integer.valueOf(source.toString()));
        return person;
    }
}
