package org.example.aspectj;

/**
 * ClassName:IntroduceUser
 * Package:org.example.aspectj
 * Description:
 *
 * @Date:2022/2/13 17:02
 * @Author:qs@1.com
 */
public class IntroduceUserImpl implements IntroduceUserInterface {

    @Override
    public void intoduceUserExecute() {
        System.out.println("IntroduceUserImpl#intoduceUserExecute");
    }
}
