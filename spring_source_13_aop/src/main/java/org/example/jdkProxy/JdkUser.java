package org.example.jdkProxy;

/**
 * ClassName:JdkUser
 * Package:org.example.jdkProxy
 * Description:
 *
 * @Date:2022/2/11 13:52
 * @Author:qs@1.com
 */
public class JdkUser implements IJdkUserInterface {

    @Override
    public void execute() {
        System.out.println("JdkUser#execute");
    }
}
