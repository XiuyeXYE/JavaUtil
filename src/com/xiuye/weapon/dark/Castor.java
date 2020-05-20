package com.xiuye.weapon.dark;

import com.xiuye.util.cls.XType;

public interface Castor {

	default <T> T cast() {
		return XType.cast(this);
	}
	
}
