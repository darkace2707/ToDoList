package com.company.obs;

import java.util.ArrayList;
import java.util.List;

public abstract class ProgressObservable {

    private int progress;

    private List<ProgressListener> progressListeners = new ArrayList<>();

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void subscribe(ProgressListener listener) {
        progressListeners.add(listener);
    }

    public void unsubscribe(ProgressListener listener) {
        progressListeners.remove(listener);
    }

    public void notifyListeners() {
        progressListeners.forEach((progressListener -> progressListener.update(progress)));
    }
}
