package mlearn.sabachina.com.cn.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhc on 2018/1/17 0017.
 */

public class MyFragment extends Fragment implements MultiScrollableViewHelper.ScrollableContainer{

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0;i<=20;i++){
            list.add("测试");
        }
        EveryoneSpeakAdapter adapter = new EveryoneSpeakAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View getScrollableView() {
        return recyclerView;
    }
}
