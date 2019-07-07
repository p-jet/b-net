package ai.zile.lib.net;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import ai.zile.lib.net.callback.IFailure;
import ai.zile.lib.net.callback.ISuccess;
import ai.zile.lib.net.config.ConfigKeys;
import ai.zile.lib.net.config.Configurator;
import ai.zile.lib.net.http.HttpCreator;
import ai.zile.lib.net.http.IMethod;
import ai.zile.lib.net.loader.LoaderStyle;
import ai.zile.lib.net.rx.SwitchSchedulers;
import ai.zile.lib.net.rx.ZileNetLifeCycle;
import ai.zile.lib.net.rx.ZileNetObserver;
import ai.zile.lib.net.utils.ZileNetBuilder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;

public class ZileNet {

    private final String mUrl;
    private final WeakHashMap<String, Object> mParams = new WeakHashMap<>();
    private final RequestBody mBody;
    private final LoaderStyle mLoaderStyle;
    private final Context mContext;
    private final File mFile;
    private final ISuccess<Object> mSuccess;
    private final IFailure mFailure;
    private IMethod mNetMethod;
    private Class<?> mService;
    private final ZileNetLifeCycle mLifeCycle;
    private Class<Observer> mObserverClass;

    public ZileNet(String url,
                   Class<?> service,
                   IMethod method,
                   Map<String, Object> params,
                   RequestBody body,
                   Context context,
                   LoaderStyle loaderStyle,
                   File file,
                   ISuccess<Object> success,
                   IFailure failure,
                   ZileNetLifeCycle lifecycle
    ) {
        this.mUrl = url;
        this.mService = service;
        this.mNetMethod = method;
        if (!mParams.isEmpty()) {
            mParams.clear();
        }
        mParams.putAll(params);
        this.mBody = body;
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        this.mFile = file;
        this.mSuccess = success;
        this.mFailure = failure;
        this.mLifeCycle = lifecycle;
    }

    public static ZileNetBuilder builder() {
        return new ZileNetBuilder();
    }

    public static Configurator init(Context context) {
        Configurator.getInstance().getNetConfigs().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return ZileNet.getConfigurator();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    /**
     * 可用来设置 全局变量
     */
    public static <T> T getConfiguration(Object key) {
        return Configurator.getInstance().getConfiguration(key);
    }

    public static Context getApp() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    /**
     * 构造请求Observable 可在项目中RxJava 串行使用
     */
    public <T> Observable<T> request() throws Exception {

        Observable observable = null;
        if (null == mNetMethod) {
            throw new Exception("net method can not be null");
        }
        Object service;
        if (null != mService) {
            service = HttpCreator.getService(mService);
        } else {
            throw new Exception("Attention : service method parameters can not be null");
        }
        if (mParams.isEmpty()) {
            throw new Exception("params can not be null");
        }
        observable = mNetMethod.ob(service, mParams);
        if (null == observable) {
            throw new Exception("observable can not be null");
        }
        return observable;
    }

    private interface CallListener<T> {

        void onSucc(T data);

        void onFail(int code, String desc);
    }

    /**
     * 调用次方法 执行具体的请求操作
     */
    public void excute() {
        try {
            Observable o = request();
            if (null != o) {
                execute(o, new CallListener() {
                    @Override
                    public void onSucc(Object data) {
                        if (null != mSuccess) {
                            mSuccess.onSuccess(data);
                        }
                    }

                    @Override
                    public void onFail(int code, String desc) {
                        if (null != mFailure) {
                            mFailure.onFailure(code, desc);
                        }
                    }
                });
            } else {
                throw new Exception("Observable get failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置请求的具体回调监听
     */
    private <T> void execute(Observable<T> observable, final CallListener callBack) {
        Observable<T> tObservable = observable.compose(new SwitchSchedulers<T>().applySchedulers());
        if (null != mLifeCycle) {
            tObservable = tObservable.compose(mLifeCycle.<T>bindToLife());
        }
        tObservable.subscribe(new ZileNetObserver<T>() {
            @Override
            public void onSuccess(T data) {
                callBack.onSucc(data);
            }

            @Override
            public void onFailure(int code, String desc) {
                callBack.onFail(code, desc);
            }
        });
    }

}
