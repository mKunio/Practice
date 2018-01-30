package mlearn.sabachina.com.cn.designpattern.ce_lue;

/**
 * Created by zhc on 2017/11/28 0028.
 */

public class Cat extends Animal {
    protected int num = 20;
    public Cat(){
        runBehavior = new Run();
        sleepBehavior = new Sleep();
    }
}
