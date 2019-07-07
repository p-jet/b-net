package ai.zile.lib.net.base;

import android.content.Context;
import ai.zile.lib.net.rx.ZileNetLifeCycle;

public interface BaseView<T> extends ZileNetLifeCycle {

    /**t
     * Presenter 获取view 的 Context
     *
     * @return
     */
    Context getViewContext();

}
