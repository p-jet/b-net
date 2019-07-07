package ai.zile.app.demo;

import android.app.Application;

import ai.zile.lib.net.ZileNet;
import ai.zile.lib.net.interceptors.ZileNetInterceptor;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ZileNet.init(this)
                .withApiHost("http://***.***.***")
//                .withRespFilter()
                .withInterceptor(new ZileNetInterceptor())
                .withToken("1234567890")
                .withLoggerAdapter()
                .withDebugMode(true)
                .configure();

    }
}
