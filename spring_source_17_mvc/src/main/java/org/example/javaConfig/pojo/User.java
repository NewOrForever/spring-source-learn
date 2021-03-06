package org.example.javaConfig.pojo;

import java.util.Date;

/**
 * ClassName:pojo
 * Package:org.example.javaConfig
 * Description:
 *
 * @Date:2022/3/3 16:59
 * @Author:qs@1.com
 */
public class User {
    private Integer id;
    private String name;
    private Date birth;

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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "pojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                '}';
    }
}
