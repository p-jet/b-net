package cn.jet.base.net.filter.data;

public interface IRespError<T> {

    int code();

    String errorMsg();

    void setCode(int code);

    void setErrorMsg(String errorMsg);

    T t();

    void setT(T t);


}
