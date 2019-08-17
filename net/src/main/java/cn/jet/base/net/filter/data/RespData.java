package cn.jet.base.net.filter.data;

import java.io.Serializable;

public class RespData<T> implements Serializable {

    public IRespEntity<T> respEntity;
    public IRespError<T> respError;

}
