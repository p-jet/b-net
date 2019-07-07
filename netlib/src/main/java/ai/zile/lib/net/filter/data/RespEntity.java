package ai.zile.lib.net.filter.data;

import java.io.Serializable;

public class RespEntity<T> implements IRespEntity, Serializable {

    T t;

    public RespEntity(T t) {
        this.t = t;
    }

    @Override
    public T entity() {
        return t;
    }

    @Override
    public void setEntity(Object o) {
        t = (T) o;
    }

}
