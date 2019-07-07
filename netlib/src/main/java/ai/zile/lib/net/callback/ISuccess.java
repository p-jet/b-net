package ai.zile.lib.net.callback;

public interface ISuccess<T> {

    /**
     * request success callback method
     *
     * @param response request success data
     */
    void onSuccess(T response);

}
