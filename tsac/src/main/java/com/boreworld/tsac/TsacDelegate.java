package com.boreworld.tsac;

public class TsacDelegate {
    private static final TsacDelegate ourInstance = new TsacDelegate();

    private TsacDelegate() {
    }

    public static TsacDelegate getInstance() {
        return ourInstance;
    }
}
