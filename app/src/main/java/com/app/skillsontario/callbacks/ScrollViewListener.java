package com.app.skillsontario.callbacks;

import com.app.skillsontario.utils.ObservableScrollView;

public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView observableScrollView, int x, int y, int oldx, int oldy);
}
