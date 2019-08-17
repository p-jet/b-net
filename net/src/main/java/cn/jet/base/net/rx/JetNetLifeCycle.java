package cn.jet.base.net.rx;


import com.trello.rxlifecycle2.LifecycleTransformer;

@FunctionalInterface
public interface JetNetLifeCycle {
    /**
     * bind life cycle
     */
    <T> LifecycleTransformer<T> bindToLife();
}
