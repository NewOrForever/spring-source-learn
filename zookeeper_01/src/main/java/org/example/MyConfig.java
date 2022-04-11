package org.example;

public class MyConfig {
    private String key;
    private String name;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MyConfig{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
