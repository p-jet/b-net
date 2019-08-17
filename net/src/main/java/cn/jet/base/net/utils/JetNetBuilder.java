package cn.jet.base.net.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import cn.jet.base.net.JetNet;
import cn.jet.base.net.callback.IFailure;
import cn.jet.base.net.callback.ISuccess;
import cn.jet.base.net.config.ConfigKeys;
import cn.jet.base.net.config.Configurator;
import cn.jet.base.net.http.IMethod;
import cn.jet.base.net.loader.LoaderStyle;
import cn.jet.base.net.base.BaseView;
import cn.jet.base.net.rx.JetNetLifeCycle;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class JetNetBuilder {

    private String mUrl = null;
    private Map<String, Object> PARAMS = new WeakHashMap<>();
    private RequestBody mRequestBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private ISuccess mSuccess = null;
    private IFailure mFailure = null;
    private IMethod mMethod;
    private Class<?> mService;
    private JetNetLifeCycle mRxLifecycle;
    private BaseView mView;

    public JetNetBuilder() {
    }

    public final JetNetBuilder service(Class<?> service) {
        this.mService = service;
        return this;
    }

    public final JetNetBuilder method(IMethod method) {
        this.mMethod = method;
        return this;
    }

    public final JetNetBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final JetNetBuilder params(Map<String, String> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final JetNetBuilder params(String key, String value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final JetNetBuilder params(String key, Object value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final JetNetBuilder paramsValueObj(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final JetNetBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final JetNetBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final JetNetBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final JetNetBuilder context(Context context) {
        this.mContext = context;
        return this;
    }

    public final JetNetBuilder bindLifeCycle(JetNetLifeCycle lifeCycle) {
        this.mRxLifecycle = lifeCycle;
        return this;
    }

    public final JetNetBuilder view(BaseView view) {
        this.mView = view;
        this.mContext = view.getViewContext();
        this.mRxLifecycle = view;
        return this;
    }

    public final JetNetBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final JetNetBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final JetNetBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final JetNetBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final JetNet build() {
        HashMap<String, Object> globalParams = Configurator.getInstance().getConfiguration(ConfigKeys.NET_GLOBLE_PARAMS);
        if (null != globalParams) {
            PARAMS.putAll(globalParams);
        }
        String token = Configurator.getInstance().getConfiguration(ConfigKeys.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            PARAMS.put("token", token);
        }
        return new JetNet(mUrl, mService,
                mMethod, PARAMS, mRequestBody,
                mContext, mLoaderStyle, mFile, mSuccess,
                mFailure, mRxLifecycle);
    }

}
