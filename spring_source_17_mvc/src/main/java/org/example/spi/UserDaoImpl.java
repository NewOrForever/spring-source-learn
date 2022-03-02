package org.example.spi;

/**
 * ClassName:UserDaoImpl
 * Package:org.example.spi
 * Description:
 *
 * @Date:2022/3/2 10:26
 * @Author:qs@1.com
 */
public class UserDaoImpl implements IUserDao{
    @Override
    public void save() {
        System.out.println("org.example.spi.UserDaoImpl#save");
    }
}
