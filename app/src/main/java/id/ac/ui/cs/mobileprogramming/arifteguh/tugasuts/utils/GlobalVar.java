package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.utils;

import android.app.Application;

public class GlobalVar extends Application {

    private long time;

    public long getSomeVariable() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSomeVariable(long time) {
        this.time = time;
    }
}