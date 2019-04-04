package com.wcc.pojo;

/**
 * @Description: Pojo类
 * @ClassName: Persion
 * @Auther: changchun_wu
 * @Date: 2019/2/23 15:32
 * @Version: 1.0
 **/
public class Person {
    private Long id;
    private String name;
    public Person(Long id, String name){
        this.id = id;
        this.name = name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
