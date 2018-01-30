package mlearn.sabachina.com.cn.designpattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mlearn.sabachina.com.cn.designpattern.ce_lue.Animal;
import mlearn.sabachina.com.cn.designpattern.ce_lue.Cat;
import mlearn.sabachina.com.cn.designpattern.ce_lue.ChangeRun;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animal animal = new Cat();
        animal.eat();
        animal.performRun();
        animal.performSleep();
        animal.setRunBehavior(new ChangeRun());
        animal.performRun();
    }
}
