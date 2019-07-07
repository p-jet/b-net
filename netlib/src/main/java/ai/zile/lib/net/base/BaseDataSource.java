package ai.zile.lib.net.base;

import java.util.List;

public interface BaseDataSource {

    interface LoadTasksCallback<T> {

        void onTasksLoaded(List<T> tasks);

        void onDataNotAvailable(int errorType, String message);
    }

    interface GetTaskCallback<T> {

        void onTaskLoaded(T task);

        void onDataNotAvailable(int code,String desc);
    }

}
