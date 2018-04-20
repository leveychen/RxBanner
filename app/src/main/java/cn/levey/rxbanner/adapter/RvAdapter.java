package cn.levey.rxbanner.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.levey.rxbanner.R;
import cn.levey.rxbanner.fake.RvItemData;

/**
 * Created by Levey on 2018/4/20 11:23.
 * e-mail: m@levey.cn
 */
public class RvAdapter extends BaseQuickAdapter<RvItemData,BaseViewHolder> {
    public RvAdapter(int layoutResId, @Nullable List<RvItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RvItemData item) {
        helper.setText(R.id.rv_title,item.title);
        helper.setText(R.id.rv_content,item.content);
        helper.setText(R.id.rv_author,item.author);
    }
}
