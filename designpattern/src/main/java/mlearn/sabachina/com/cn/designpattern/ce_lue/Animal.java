package mlearn.sabachina.com.cn.designpattern.ce_lue;

/**
 * Created by zhc on 2017/11/28 0028.
 */

public class Animal {
    RunBehavior runBehavior;
    SleepBehavior sleepBehavior;

    public void eat() {
        System.out.println("All animals can eat food");
    }

    public void performRun() {
        runBehavior.run();
    }

    public void performSleep() {
        sleepBehavior.sleep();
    }

    public void setRunBehavior(RunBehavior runBehavior) {
        this.runBehavior = runBehavior;
    }
}
