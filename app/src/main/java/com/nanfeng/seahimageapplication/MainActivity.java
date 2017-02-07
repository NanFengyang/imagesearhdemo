package com.nanfeng.seahimageapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nanfeng.seahimageapplication.adapter.GridResultAdapter;
import com.nanfeng.seahimageapplication.bean.ImageBean;
import com.nanfeng.seahimageapplication.utils.JsonUtil;
import com.nanfeng.seahimageapplication.utils.JsoupUtil;
import com.nanfeng.seahimageapplication.utils.LogUtil;
import com.squareup.okhttp.Request;
import com.ycl.chooseavatar.library.OnChoosePictureListener;
import com.ycl.chooseavatar.library.UpLoadHeadImageDialog;
import com.ycl.chooseavatar.library.YCLTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnChoosePictureListener, AdapterView.OnItemClickListener {
    private String upload_imgUrl = "http://image.baidu.com/pictureup/uploadshitu";
    private GridView mGridView;
    private GridResultAdapter mGridResultAdapter;
    private String mUploadImageLocalPath;
    private ProgressDialog mPbarDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(this);
        YCLTools.getInstance().setOnChoosePictureListener(this);

    }

    /**
     * 开始图片选择
     *
     * @param view
     */
    public void onChooseClick(View view) {
        new UpLoadHeadImageDialog(MainActivity.this).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //must set like this
        YCLTools.getInstance().upLoadImage(requestCode, resultCode, data);
    }

    @Override
    public void OnChoose(String filePath) {
        upLoadImageAndSearh(filePath);

    }

    @Override
    public void OnCancel() {

    }

    /**
     * 上传并且查询图片和识别图片
     *
     * @param filePath
     */
    private void upLoadImageAndSearh(String filePath) {
        mPbarDialog=  ProgressDialog.show( this, " " , " 搜索中. Please wait ... ", true);
        mUploadImageLocalPath = filePath;
        File file = new File(filePath);
        OkHttpUtils.post()//
                .addFile("image", "iamge.png", file)//
                .url(upload_imgUrl)
                .build()//
                .execute(new MyStringCallback());
    }

    /**
     * 自定义回调接口
     */
    class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            Log.e("MyStringCallback", "onBefore:" + request.url().toString());
        }

        @Override
        public void onAfter() {
            super.onAfter();
            Log.e("MyStringCallback", "onAfter:");
        }

        //响应的结果太长了，这里截断显示
        @Override
        public void onResponse(String response) {

            LogUtil.LogShitou("onResponse", response.substring(1000,response.length()));
            List<ImageBean> list = JsonUtil.getImageList(JsoupUtil.getImageUrl(response));
            if(list == null | list.size()<=0){
                mPbarDialog.setMessage("搜索完成，没有找到结果。");
                delayLoadingDismiss();
                return;
            }
            mPbarDialog.setMessage("搜索完成");
            Log.i("List", "List:" + list.size());
            mGridResultAdapter = new GridResultAdapter(MainActivity.this, list);
            mGridView.setAdapter(mGridResultAdapter);
            mPbarDialog.cancel();
        }

        @Override
        public void inProgress(float progress) {
            Log.e("MyStringCallback", "inProgress:" + progress);

        }

        @Override
        public void onError(Request request, Exception e) {
            Log.e("MyStringCallback", "onError:" + e.toString());
            mPbarDialog.setMessage("识别错误："+ e.toString());
            delayLoadingDismiss();
        }
    }

    private void delayLoadingDismiss(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPbarDialog.cancel();
            }
        },3000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.DETAILACTIVITY_KEY, mGridResultAdapter.getItem(i).from_url);
        startActivity(intent);

    }

}
