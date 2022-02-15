package org.example.parent;

/**
 * ClassName:Child
 * Package:org.example.parent
 * Description:
 *
 * @Date:2022/2/14 8:54
 * @Author:qs@1.com
 */
public class Child extends Parent{

    // 父类没有无参构造方法的话会报错的
    // 子类的构造方法中需要去指定父类的构造方法
    public Child(String userName) {
        super(userName);
    }

    public Child() {
        super("input userName");
    }
}
