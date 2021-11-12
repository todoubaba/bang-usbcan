package com.czsub.bang.usbcan;

import com.sun.jna.platform.win32.WinDef;

/**
 * 工作模式
 */
public enum WorkingMode {
    /**
     * 正常模式
     */
    NORMAL(0),

    /**
     * 只听模式（只接收，不影响总线）
     */
    RECEIVE_ONLY(1),

    /**
     * 自发自收模式（环回模式）
     */
    LOOP(2);

    private final WinDef.BYTE value;

    WorkingMode(int value) {
        this.value = new WinDef.BYTE(value);
    }

    public WinDef.BYTE getValue() {
        return this.value;
    }
}
