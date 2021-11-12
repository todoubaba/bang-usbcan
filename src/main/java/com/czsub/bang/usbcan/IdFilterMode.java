package com.czsub.bang.usbcan;

import com.sun.jna.platform.win32.WinDef;

public enum IdFilterMode {
    /**
     * 滤波器同时对标准帧与扩展帧过滤
     */
    ALL(1),

    /**
     * 滤波器只对标准帧过滤，扩展帧将直接被滤除
     */
    STANDARD(2),

    /**
     * 滤波器只对扩展帧过滤，标准帧将直接被滤除。
     */
    EXTENSION(3);

    private final WinDef.BYTE value;

    IdFilterMode(int value) {
        this.value = new WinDef.BYTE(value);
    }

    public WinDef.BYTE getValue() {
        return value;
    }
}
