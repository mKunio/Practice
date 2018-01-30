package mlearn.sabachina.com.cn.designpattern.ce_lue;

/**
 * Created by zhc on 2017/11/28 0028.
 */

public class Sleep implements SleepBehavior {
    @Override
    public void sleep() {
        System.out.println("I'm real sleep!");
    }
}
