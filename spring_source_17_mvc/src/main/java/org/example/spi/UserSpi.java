package org.example.spi;

import java.util.ServiceLoader;

/**
 * ClassName:UserSpi
 * Package:org.example.spi
 * Description:
 *
 * @Date:2022/3/2 10:21
 * @Author:qs@1.com
 */
public class UserSpi {
    public static void main(String[] args) {
        ServiceLoader<IUserDao> userDaos = ServiceLoader.load(IUserDao.class);
        for (IUserDao userDao : userDaos) {
            userDao.save();
        }
    }
}
