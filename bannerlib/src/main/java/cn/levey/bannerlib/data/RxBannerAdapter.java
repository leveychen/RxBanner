package cn.levey.bannerlib.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.levey.bannerlib.R;
import cn.levey.bannerlib.impl.RxBannerLoaderInterface;
import cn.levey.bannerlib.base.RxBannerConfig;

/**
 * Created by Levey on 2018/4/2 15:28.
 * e-mail: m@levey.cn
 */

public class RxBannerAdapter extends RecyclerView.Adapter<RxBannerAdapter.RxBannerHolder> {

    private Context mContext;
    private ArrayList<Object> mList = new ArrayList<>();
    private RxBannerLoaderInterface mLoader;


    public RxBannerAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public RxBannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.rx_banner_item_view, parent, false);
        return new RxBannerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RxBannerHolder holder, int position) {
        getLoader().show(mContext,mList.get(position),holder.image);
    }

    class RxBannerHolder extends RecyclerView.ViewHolder{
        private View image;
        private RxBannerHolder(RelativeLayout itemView) {
            super(itemView);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            image = getLoader().create(itemView.getContext());
            itemView.addView(image,lp);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Object getItemData(int position) {
        return mList.get(position);
    }

    public ArrayList<Object> getDatas() {
        return mList;
    }

    public void addList(ArrayList<Object> list){
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

    public void setDatas(ArrayList<?> list){
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
