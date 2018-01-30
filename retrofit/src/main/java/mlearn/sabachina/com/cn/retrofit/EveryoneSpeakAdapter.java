package mlearn.sabachina.com.cn.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhc on 2018/1/16 0016.
 */

public class EveryoneSpeakAdapter extends RecyclerView.Adapter {
    private List<String> posts;
    private Context context;

    public EveryoneSpeakAdapter(Context context, List<String> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_everyone_speak_edit_comment, parent, false);
            return new MyCommentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.normal, parent, false);
            return new PostViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        switch (type) {
            case 0:
                break;
            case 1:
                break;
        }
    }



    @Override
    public int getItemCount() {
        return posts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    private static class MyCommentViewHolder extends RecyclerView.ViewHolder {

        private final TextView keep;
        private final TextView cancel;
        private final TextView mark;
        private final EditText commentEditText;
        private final View editCommentLayout;

        MyCommentViewHolder(View itemView) {
            super(itemView);
            keep = itemView.findViewById(R.id.keep);
            cancel = itemView.findViewById(R.id.cancel);
            mark = itemView.findViewById(R.id.mark);
            commentEditText = itemView.findViewById(R.id.edit_comment);
            editCommentLayout = itemView.findViewById(R.id.edit_comment_layout);
        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        private final TextView commentTv;

        PostViewHolder(View itemView) {
            super(itemView);
            commentTv = itemView.findViewById(R.id.comment_tv);
        }
    }
}
