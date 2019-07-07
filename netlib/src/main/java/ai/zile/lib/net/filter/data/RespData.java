package ai.zile.lib.net.filter.data;

import java.io.Serializable;

public class RespData<T> implements Serializable {

    public IRespEntity<T> respEntity;
    public IRespError<T> respError;

}
