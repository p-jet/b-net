package cn.jet.base.net.base;

import android.content.Context;
import cn.jet.base.net.rx.JetNetLifeCycle;

public interface BaseView<T> extends JetNetLifeCycle {

    /**t
     * Presenter 获取view 的 Context
     *
     * @return
     */
    Context getViewContext();

}
