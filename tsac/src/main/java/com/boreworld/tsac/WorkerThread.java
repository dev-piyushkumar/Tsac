package com.boreworld.tsac;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class WorkerThread extends Thread {

    @IntDef({T_STATE_RUNNING, T_STATE_PAUSED, T_STATE_EXIT})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface tState{}
    public static final int T_STATE_RUNNING = 0;
    public static final int T_STATE_PAUSED = 1;
    public static final int T_STATE_EXIT = 2;

    private static final Object wakeLock = new Object();
    private static WorkerThread instance;

    public static WorkerThread getInstance() {
        if (instance==null || instance.getState()==State.TERMINATED) {
            initInstance();
        }
        return instance;
    }

    private static void initInstance() {
        instance = new WorkerThread();
    }

    private WorkerThread() {
    }

    private WorkerThread(Runnable target) {
        super(target);
    }

    private WorkerThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    private WorkerThread(String name) {
        super(name);
    }

    private WorkerThread(ThreadGroup group, String name) {
        super(group, name);
    }

    private WorkerThread(Runnable target, String name) {
        super(target, name);
    }

    private WorkerThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    private WorkerThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
    }

    @Override
    public void run() {
    }
}
