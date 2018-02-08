package mlearn.sabachina.com.cn.practiceview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 创建被观察者
//        method1();
//        method2();
//        method3();
//        method4();
        method6();
    }

    // 常规调用
    private void method1() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
                Log.d(TAG, String.valueOf(value));
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };
        observable.subscribe(observer);
    }

    // 链式调用
    private void method2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                //  切断观察者与被观察者的联系
//               d.dispose();
            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    // rxjava各种操作符释义举例
    private void method3() {
//        just 快速创建Observable对象并发送事件  相当于create之后执行 e.onNext(1); e.onNext(2); e.onNext(3) e.onNext(4); e.onComplete();
        Observable.just(1, 2, 3, 4).subscribe(new RxJavaObserver<Integer>());
        //fromArray 接收数组参数，适用于需要发送多个事件创建，因为just最多接收10个参数
        Integer[] arr = {1, 2, 3, 4};
        Observable.fromArray(arr).subscribe(new RxJavaObserver<Integer>());
        // 接收list对象
        Observable.fromIterable(new ArrayList<Integer>()).subscribe(new RxJavaObserver<Integer>());

    }

    // rxjava  延迟事件
    private void method4() {
        // 延迟2S，每隔3S就会发送一个事件
        Observable.interval(2, 3, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.d(TAG, "accept到了" + aLong);
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
//                Log.d(TAG, String.valueOf(value));
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    // rxjava 变换操作符  将事件类型为int的值变换为String类型的值  map变换操作符
    private void method5() {
        Observable.just(1, 2, 3, 4).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.valueOf(integer);  // 此处变换值
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    // 线程切换
    private void method6() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, Thread.currentThread().getName());
            }
        });
    }
}
