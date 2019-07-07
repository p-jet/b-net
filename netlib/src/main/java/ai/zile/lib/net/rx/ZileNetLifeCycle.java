package ai.zile.lib.net.rx;


import com.trello.rxlifecycle2.LifecycleTransformer;

@FunctionalInterface
public interface ZileNetLifeCycle {
    /**
     * bind life cycle
     */
    <T> LifecycleTransformer<T> bindToLife();
}
