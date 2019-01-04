package com.boreworld.tsac.interfaces;

public interface PayloadListener<P> {
    public void payLoad(P... params);
}
