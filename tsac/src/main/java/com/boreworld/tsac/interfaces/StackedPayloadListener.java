package com.boreworld.tsac.interfaces;

public interface StackedPayloadListener<PARAM> extends PostPayloadListener<PARAM> {
    void onPayloadCreated();
}
