package mlearn.sabachina.com.cn.tab;

/**
 * Created by zhc on 2018/3/27 0027.
 */

public class T<T extends Person> {
    private String name;
    private T sex;

    public T getSex() {
        return sex;
    }

    public void setSex(T sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
