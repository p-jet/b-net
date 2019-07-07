package ai.zile.lib.net.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import ai.zile.lib.net.ZileNet;
import ai.zile.lib.net.callback.IFailure;
import ai.zile.lib.net.callback.ISuccess;
import ai.zile.lib.net.config.ConfigKeys;
import ai.zile.lib.net.config.Configurator;
import ai.zile.lib.net.http.IMethod;
import ai.zile.lib.net.loader.LoaderStyle;
import ai.zile.lib.net.base.BaseView;
import ai.zile.lib.net.rx.ZileNetLifeCycle;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ZileNetBuilder {

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
    private ZileNetLifeCycle mRxLifecycle;
    private BaseView mView;

    public ZileNetBuilder() {
    }

    public final ZileNetBuilder service(Class<?> service) {
        this.mService = service;
        return this;
    }

    public final ZileNetBuilder method(IMethod method) {
        this.mMethod = method;
        return this;
    }

    public final ZileNetBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final ZileNetBuilder params(Map<String, String> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final ZileNetBuilder params(String key, String value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final ZileNetBuilder params(String key, Object value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final ZileNetBuilder paramsValueObj(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final ZileNetBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final ZileNetBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final ZileNetBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final ZileNetBuilder context(Context context) {
        this.mContext = context;
        return this;
    }

    public final ZileNetBuilder bindLifeCycle(ZileNetLifeCycle lifeCycle) {
        this.mRxLifecycle = lifeCycle;
        return this;
    }

    public final ZileNetBuilder view(BaseView view) {
        this.mView = view;
        this.mContext = view.getViewContext();
        this.mRxLifecycle = view;
        return this;
    }

    public final ZileNetBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final ZileNetBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final ZileNetBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final ZileNetBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final ZileNet build() {
        HashMap<String, Object> globalParams = Configurator.getInstance().getConfiguration(ConfigKeys.NET_GLOBLE_PARAMS);
        if (null != globalParams) {
            PARAMS.putAll(globalParams);
        }
        String token = Configurator.getInstance().getConfiguration(ConfigKeys.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            PARAMS.put("token", token);
        }
        return new ZileNet(mUrl, mService,
                mMethod, PARAMS, mRequestBody,
                mContext, mLoaderStyle, mFile, mSuccess,
                mFailure, mRxLifecycle);
    }

}
