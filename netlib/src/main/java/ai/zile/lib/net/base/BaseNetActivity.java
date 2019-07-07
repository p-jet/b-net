package ai.zile.lib.net.base;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import ai.zile.lib.net.rx.ZileNetLifeCycle;

/**
 * To handle possible memory leaks.
 * extends this or implements in your BaseActivity
 */
public class BaseNetActivity extends RxAppCompatActivity implements ZileNetLifeCycle {
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        // 指定在onDestroy方法被调用时取消订阅
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
