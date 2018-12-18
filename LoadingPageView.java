package com.lwd.phdyw.mvp.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwd.phdyw.R;
import com.lwd.phdyw.app.utils.Constant;

import java.util.List;
import java.util.Map;

/**
 * Email:wwwiiivip@yeah.net
 * Created by ZhengQian on 2017/7/11.
 */

public class LoadingPageView extends LinearLayout {


    private TextView emptyTv;
    private LinearLayout loadingLl;
    private LinearLayout loadingFailLl;
    private LinearLayout loadingEmptyLl;
    private boolean isLoading;
    private boolean isEmptyShow;
    private ClickReloadCallback clickReloadCallback;

    public LoadingPageView(Context context) {
        this(context, null);
    }

    public LoadingPageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        View view = View.inflate(context, R.layout.loading_layout, null);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, this);
        findView(view);
    }


//    public LoadingPageView(Context context) {
//        super(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout,null);
//        findView(view);
//    }


    private void findView(View view) {

        emptyTv = (TextView) view.findViewById(R.id.activity_base_loading_no_data_tv);
        loadingLl = (LinearLayout) view.findViewById(R.id.activity_base_loading_ll);
        loadingFailLl = (LinearLayout) view.findViewById(R.id.activity_base_loading_fail_ll);
        loadingEmptyLl = (LinearLayout) view.findViewById(R.id.activity_base_loading_no_data_ll);
        initListener();
    }

    private void initListener() {
        loadingFailLl.setOnClickListener(view -> {
            if(clickReloadCallback != null)
                clickReloadCallback.clickReload();
        });
    }

    public void setClickReload(ClickReloadCallback clickReloadCallback){
        this.clickReloadCallback = clickReloadCallback;
    }

    //设置是否加载
    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    //检查数据显示相应界面
    public String checkData(Object data) {

        if (data == null) {
            loadingEmpty();
            return Constant.LOADING_EMPTY;
        }
        if (data instanceof Integer && (int) data == -1) {
            loadingFail();
            return Constant.LOADING_FAIL;
        }
        if (data instanceof List && ((List) data).size() == 0) {
            loadingEmpty();
            return Constant.LOADING_EMPTY;
        }
        if (data instanceof Map && ((Map) data).size() == 0) {
            loadingEmpty();
            return Constant.LOADING_EMPTY;
        }
        hideLoadingPage();
        return Constant.LOADING_SUCCESS;
    }

    //开启加载动画
    public void startLodingAnim() {
        loadingLl.setVisibility(View.VISIBLE);



    }

    //停止加载动画
    public void stopLodingAnim() {
        loadingLl.setVisibility(View.GONE);


    }

    //加载失败
    public void loadingFail() {
        isEmptyShow = false;
        loadingPage();
    }

    //加载数据为空
    public void loadingEmpty() {
        isEmptyShow = true;
        loadingPage();
    }

    //隐藏所有加载页面
    public void hideLoadingPage() {
        stopLodingAnim();
        loadingFailLl.setVisibility(View.GONE);
        loadingEmptyLl.setVisibility(View.GONE);
    }

    //加载页面
    public void loadingPage() {
        stopLodingAnim();
        loadingEmptyLl.setVisibility(isEmptyShow ? View.VISIBLE : View.GONE);
        loadingFailLl.setVisibility(!isEmptyShow ? View.VISIBLE : View.GONE);
    }

    //设置加载空数据显示文字
    public void setEmptyTv(String emptyStr) {
        if (emptyStr == null || emptyStr.equals(""))
            return;
        emptyTv.setText(emptyStr);
    }

    public interface ClickReloadCallback{
        void clickReload();
    }
}
