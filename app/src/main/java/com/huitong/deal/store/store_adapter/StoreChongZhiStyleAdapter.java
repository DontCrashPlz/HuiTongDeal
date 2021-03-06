package com.huitong.deal.store.store_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.huitong.deal.R;
import com.huitong.deal.beans.PayTypeEntity;
import com.huitong.deal.store.store_activities.StoreChongZhiActivity;

import java.util.ArrayList;

/**
 * Created by Zheng on 2018/5/18.
 */

public class StoreChongZhiStyleAdapter extends RecyclerView.Adapter<StoreChongZhiStyleAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PayTypeEntity> mList;

    public StoreChongZhiStyleAdapter(Context context, ArrayList<PayTypeEntity> list) {
        mContext= context;
        mList= list;
    }

    @Override
    public StoreChongZhiStyleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.store_item_chongzhi_style, parent, false));
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.store_item_pay_order, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PayTypeEntity entity= mList.get(position);
        holder.textView.setText(entity.getName());
        if ("unitscan".equals(entity.getPaytype())){
            holder.imageView.setImageResource(R.mipmap.recharge_unionpay);
        }else if ("alipay".equals(entity.getPaytype())){
            holder.imageView.setImageResource(R.mipmap.recharge_alipay);
        }else if ("wxpay".equals(entity.getPaytype())){
            holder.imageView.setImageResource(R.mipmap.recharge_wechat);
        }
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getInstall()== 0){
                    ((StoreChongZhiActivity)mContext).showShortToast("此方式暂不可用");
                    return;
                }
                ((StoreChongZhiActivity)mContext).doChongZhi(entity.getPaytype());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private FrameLayout frameLayout;
        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            //textView= itemView.findViewById(R.id.textView);
            frameLayout= itemView.findViewById(R.id.store_pay_type_panel);
            imageView= itemView.findViewById(R.id.store_pay_type_image);
            textView= itemView.findViewById(R.id.store_pay_type_text);
        }
    }
}
