package ai.zile.app.demo;

import android.app.Application;

import cn.jet.base.net.JetNet;
import cn.jet.base.net.interceptors.JetNetInterceptor;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        JetNet.init(this)
                .withApiHost("http://***.***.***")
//                .withRespFilter()
                .withInterceptor(new JetNetInterceptor())
                .withToken("1234567890")
                .withLoggerAdapter()
                .withDebugMode(true)
                .configure();

    }
}
