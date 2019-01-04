package com.boreworld.tsac.interfaces;

public interface ProgressPayloadListener<PARAM, PROG> extends StackedPayloadListener<PARAM> {
    public void onProgress(PROG progress);
}
