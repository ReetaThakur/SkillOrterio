package com.app.skillontario.callbacks;

import com.app.skillontario.utils.ObservableScrollView;

public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView observableScrollView, int x, int y, int oldx, int oldy);
}
