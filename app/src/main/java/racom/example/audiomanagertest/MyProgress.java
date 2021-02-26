package racom.example.audiomanagertest;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义的进度条
 *
 * @author HB.yangzuozhe
 * @date 2021-02-26
 */
public class MyProgress extends FrameLayout {
    MyAdapter mAdapter;
    MyAdapter mMaxAdapter;
    RecyclerView mRvProgress;
    RecyclerView mRvMax;
    int mNumber;

    public MyProgress(@NonNull Context context) {
        this(context, null);
    }

    public MyProgress(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgress(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.fl_layout, this);
        mRvProgress = findViewById(R.id.rvProgress);
        mRvMax = findViewById(R.id.rvMaxProgress);
    }


    public void setProgress(int maxNumber, int number) {
        setMaxProgress(maxNumber);
        initAdapter(number);
    }

    private void setMaxProgress(int number) {
        if (mMaxAdapter == null) {
            mMaxAdapter = new MyAdapter(number, Color.RED);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRvMax.setLayoutManager(layoutManager);
            mRvMax.setAdapter(mMaxAdapter);
            mMaxAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter(int number) {
        mNumber = number;
        if (mAdapter == null) {
            mAdapter = new MyAdapter(mNumber, Color.GREEN);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRvProgress.setLayoutManager(layoutManager);
            mRvProgress.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private int mNumber;
        private int mColor;

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView iv;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                iv = itemView.findViewById(R.id.iv);
                iv.setBackgroundColor(mColor);
            }
        }

        public MyAdapter(int number, int color) {
            mNumber = number;
            mColor = color;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.progress_item, null);
            final MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mNumber;
        }


    }

}
