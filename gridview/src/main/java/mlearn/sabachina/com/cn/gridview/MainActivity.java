package mlearn.sabachina.com.cn.gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView view = (GridView) findViewById(R.id.gv);
        view.setAdapter(new Myadapter());
    }
    class  Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout, viewGroup, false);
            TextView view1 = inflate.findViewById(R.id.message_right_bottom_text);
            if (i == 2){
                view1.setText("我真的是哦");
            }
            return inflate;
        }
    }
}
