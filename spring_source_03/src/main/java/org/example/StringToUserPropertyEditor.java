package org.example;

import org.springframework.beans.PropertyEditorRegistrySupport;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * ClassName:StringToUserPropertyEditor
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/24 15:02
 * @Author:qs@1.com
 */
public class StringToUserPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        User user = new User();
        user.setName(text);
        this.setValue(user);
    }
}
