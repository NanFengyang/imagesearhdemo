package com.nanfeng.seahimageapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanfeng.seahimageapplication.R;
import com.nanfeng.seahimageapplication.bean.ImageBean;
import com.nanfeng.seahimageapplication.utils.ScreenUtil;

import java.util.List;

/**
 * Created by yangyoutao on 2016/12/23.
 */

public class GridResultAdapter extends BaseAdapter {
    private List<ImageBean> mList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mWidth;

    public GridResultAdapter(Context context, List<ImageBean> list) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mList = list;
        this.mContext = context;
        this.mWidth = ScreenUtil.getScreenWidth(context) / 3;
    }

    @Override
    public int getCount() {
        return this.mList.size();
    }

    @Override
    public ImageBean getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (null == view) {
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.grid_image_resultlayout, null);
            viewHolder.imageView_img = (ImageView) view.findViewById(R.id.image_result);
            viewHolder.textView_from = (TextView) view.findViewById(R.id.from_result);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageBean imageBean = getItem(i);
        Log.i("imageBean", "image_url:" + imageBean.image_url);
        if (null != imageBean) {
            Glide.with(mContext).load(imageBean.image_url).into(viewHolder.imageView_img);//显示gif动态图片
            viewHolder.textView_from.setVisibility(View.GONE);
        }
        RelativeLayout.LayoutParams newlayoutpa = new RelativeLayout.LayoutParams(this.mWidth, this.mWidth);
        view.setLayoutParams(newlayoutpa);
        return view;
    }

    private static class ViewHolder {
        ImageView imageView_img;
        TextView textView_from;
    }
}
