package ai.zile.lib.net.http;

import java.util.WeakHashMap;

import io.reactivex.Observable;

@FunctionalInterface
public interface IMethod<X> {
    /**
     * 接口方法调用
     *
     * @param service 具体的Service类
     * @param params  request params
     * @return 返回的Observable<>对象W
     */
    Observable ob(X service, WeakHashMap<String, Object> params);
}
