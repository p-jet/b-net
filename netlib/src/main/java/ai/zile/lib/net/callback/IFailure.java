package ai.zile.lib.net.callback;

public interface IFailure {

    /**
     * @param code request error code
     * @param desc request error description
     */
    void onFailure(int code, String desc);

}
