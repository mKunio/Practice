package mlearn.sabachina.com.cn.volley;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
/*    Android自动化测试中经常需要获取当前应用的activity名字
http://blog.csdn.net/vinomvp/article/details/52228377  比较老旧的打开各个手机系统的设置页面
            最快捷的方法

    查看当前的界面的activity方法  可以在手机上先打开APP权限设置页面然后运行以下命令

    adb shell dumpsys activity | grep mFocusedActivity

    windows使用如下命令:

    adb shell dumpsys activity | find "mFocusedActivity"*/

    public void click(View view) {
        // 小米手机 MIUI8打开权限设置页面的方法，9也是如此
        //加不加action好像都无所谓
//        Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        Intent localIntent = new Intent();
        localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        // 用于判断是跳转到具体哪个应用的权限设置页面
        localIntent.putExtra("extra_pkgname", getPackageName());
        try {
            startActivity(localIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, Build.BRAND, Toast.LENGTH_SHORT).show();
        }
//        try {
//            Intent intent = new Intent();
//            ComponentName componentName = new ComponentName("com.miui.securitycenter","com.miui.permcenter.MainAcitivty");
//            intent.setComponent(componentName);
////            intent.putExtra("extra_pkgname", getPackageName());
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "打开失败", Toast.LENGTH_SHORT).show();
//        }
    }
}
//public class MiuiUtils {
//    /**
//     * 跳转到MIUI应用权限设置页面
//     *
//     * @param context context
//     */
//    public static void jumpToPermissionsEditorActivity(Context context) {
//        if (isMIUI()) {
//            try {
//                // MIUI 8
//                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
//                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
//                localIntent.putExtra("extra_pkgname", context.getPackageName());
//                context.startActivity(localIntent);
//            } catch (Exception e) {
//                try {
//                    // MIUI 5/6/7
//                    Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
//                    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
//                    localIntent.putExtra("extra_pkgname", context.getPackageName());
//                    context.startActivity(localIntent);
//                } catch (Exception e1) {
//                    // 否则跳转到应用详情
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//                    intent.setData(uri);
//                    context.startActivity(intent);
//                }
//            }
//        }
//    }
