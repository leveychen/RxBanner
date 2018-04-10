package cn.levey.bannerlib.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.levey.bannerlib.R;
import cn.levey.bannerlib.base.RxBannerConfig;
import cn.levey.bannerlib.impl.RxBannerClickListener;
import cn.levey.bannerlib.impl.RxBannerLoaderInterface;

/**
 * Created by Levey on 2018/4/2 15:28.
 * e-mail: m@levey.cn
 */

public class RxBannerAdapter extends RecyclerView.Adapter<RxBannerAdapter.RxBannerHolder> {

    private Context mContext;
    private List<Object> mList = new ArrayList<>();
    private RxBannerLoaderInterface mLoader;
    private int mSize;
    private int orientation;

    private RxBannerClickListener rxBannerClickListener;

    public void setRxBannerClickListener(RxBannerClickListener rxBannerClickListener) {
        this.rxBannerClickListener = rxBannerClickListener;
    }

    public RxBannerAdapter(Context context,int orientation, int size){
        this.mContext = context;
        this.mSize = size;
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public RxBannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.rx_banner_item_view, parent, false);
        RelativeLayout.LayoutParams lp;
        if(orientation == LinearLayout.VERTICAL){
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mSize);
        }else {
            lp = new RelativeLayout.LayoutParams(mSize,RelativeLayout.LayoutParams.MATCH_PARENT);
        }
        view.setLayoutParams(lp);
        return new RxBannerHolder(view);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull final RxBannerHolder holder, @SuppressLint("RecyclerView") final int position) {
        getLoader().show(mContext,mList.get(position),holder.image);
        if(rxBannerClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rxBannerClickListener.onItemClick(position,mList.get(position));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    rxBannerClickListener.onItemLongClick(position,mList.get(position));
                    return false;
                }
            });
        }
    }

    class RxBannerHolder extends RecyclerView.ViewHolder{
        private View image;
        private RxBannerHolder(RelativeLayout itemView) {
            super(itemView);
            RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
            image = getLoader().create(itemView.getContext());
            itemView.addView(image,lp);
        }
    }

    @Override
    public int getItemCount() {return mList.size();}

    public Object getItemData(int position) {
        return mList.get(position);
    }

    public List<Object> getDatas() {
        return mList;
    }

    public void addDatas(List<Object> list){
        if(list != null && !list.isEmpty()){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addItem(String item){
        mList.add(item);
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void setDatas(List<Object> list){
        if(list != null && !list.isEmpty()){
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    private RxBannerLoaderInterface getLoader() {
        if(mLoader == null){
            mLoader = RxBannerConfig.getInstance().getLoader();
        }
        return mLoader;
    }

    public void setLoader(RxBannerLoaderInterface mLoader) {
        this.mLoader = mLoader;
    }
}
