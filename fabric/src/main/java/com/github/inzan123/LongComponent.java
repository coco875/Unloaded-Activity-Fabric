package com.github.inzan123;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface LongComponent extends ComponentV3 {
    long getValue();

    void setValue(long lastTick);

}

