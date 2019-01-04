package com.boreworld.tsac.interfaces;

public interface PostPayloadListener<PARAM> extends PayloadListener<PARAM> {
    public void onPayloadCompleted();
}
