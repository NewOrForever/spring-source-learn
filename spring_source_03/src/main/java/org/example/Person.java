package org.example;

/**
 * ClassName:Person
 * Package:org.example
 * Description:
 *
 * @Date:2021/12/24 17:04
 * @Author:qs@1.com
 */
public class Person {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                '}';
    }
}
