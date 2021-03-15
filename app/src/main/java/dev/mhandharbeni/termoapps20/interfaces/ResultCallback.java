package dev.mhandharbeni.termoapps20.interfaces;

import com.manishkprboilerplate.base.UiView;

public class ResultCallback {
    public interface ResultUICallback{

    }

    public interface ResultResponseCallback extends UiView {
        void fetchLoad(String message);
        void fetchSuccess(Object anyClass);
        void fetchFailed();
        void fetchComplete();
    }
}
