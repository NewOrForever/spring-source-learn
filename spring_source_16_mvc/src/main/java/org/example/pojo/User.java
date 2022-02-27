package org.example.pojo;

import java.io.Serializable;

/**
 * ClassName:User
 * Package:org.example.pojo
 * Description:
 *
 * @Date:2022/2/25 13:00
 * @Author:qs@1.com
 */
public class User implements Serializable {

    private static final long serialVersionUID = -3224968394701421851L;

    private Integer id;
    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
