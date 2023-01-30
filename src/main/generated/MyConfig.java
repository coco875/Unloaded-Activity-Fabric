package com.github.inzan123;

import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MyConfig extends ConfigWrapper<com.github.inzan123.MyConfigModel> {

    private final Option<java.lang.Boolean> debugLogs = this.optionForKey(new Option.Key("debugLogs"));
    private final Option<java.lang.Boolean> randomizeBlockUpdates = this.optionForKey(new Option.Key("randomizeBlockUpdates"));
    private final Option<java.lang.Boolean> growSaplings = this.optionForKey(new Option.Key("growSaplings"));
    private final Option<java.lang.Boolean> growCrops = this.optionForKey(new Option.Key("growCrops"));
    private final Option<java.lang.Boolean> growStems = this.optionForKey(new Option.Key("growStems"));
    private final Option<java.lang.Boolean> growSweetBerries = this.optionForKey(new Option.Key("growSweetBerries"));
    private final Option<java.lang.Boolean> growCocoa = this.optionForKey(new Option.Key("growCocoa"));

    private MyConfig() {
        super(com.github.inzan123.MyConfigModel.class);
    }

    public static MyConfig createAndLoad() {
        var wrapper = new MyConfig();
        wrapper.load();
        return wrapper;
    }

    public boolean debugLogs() {
        return debugLogs.value();
    }

    public void debugLogs(boolean value) {
        debugLogs.set(value);
    }

    public boolean randomizeBlockUpdates() {
        return randomizeBlockUpdates.value();
    }

    public void randomizeBlockUpdates(boolean value) {
        randomizeBlockUpdates.set(value);
    }

    public boolean growSaplings() {
        return growSaplings.value();
    }

    public void growSaplings(boolean value) {
        growSaplings.set(value);
    }

    public boolean growCrops() {
        return growCrops.value();
    }

    public void growCrops(boolean value) {
        growCrops.set(value);
    }

    public boolean growStems() {
        return growStems.value();
    }

    public void growStems(boolean value) {
        growStems.set(value);
    }

    public boolean growSweetBerries() {
        return growSweetBerries.value();
    }

    public void growSweetBerries(boolean value) {
        growSweetBerries.set(value);
    }

    public boolean growCocoa() {
        return growCocoa.value();
    }

    public void growCocoa(boolean value) {
        growCocoa.set(value);
    }




}

