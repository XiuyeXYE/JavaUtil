package com.xiuye.sharp;

import com.xiuye.util.cls.XType;

public interface Castor {

    default <T> T cast() {
        return cast(this);
    }

    default <R extends I, I> R cast(I in) {
        return XType.cast(in);
    }

}
