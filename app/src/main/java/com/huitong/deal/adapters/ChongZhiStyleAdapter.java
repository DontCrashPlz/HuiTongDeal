package com.huitong.deal.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.activities.ChongZhiActivity;
import com.huitong.deal.beans.PayTypeEntity;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/18.
 */

public class ChongZhiStyleAdapter extends RecyclerView.Adapter<ChongZhiStyleAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PayTypeEntity> mList;

    public ChongZhiStyleAdapter(Context context, ArrayList<PayTypeEntity> list) {
        mContext= context;
        mList= list;
    }

    @Override
    public ChongZhiStyleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chongzhi_style, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PayTypeEntity entity= mList.get(position);
        holder.textView.setText(entity.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getInstall()== 0){
                    ((ChongZhiActivity)mContext).showShortToast("此方式暂不可用");
                    return;
                }
                ((ChongZhiActivity)mContext).doChongZhi(entity.getPaytype());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.textView);
        }
    }
}
