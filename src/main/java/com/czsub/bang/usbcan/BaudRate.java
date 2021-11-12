package com.czsub.bang.usbcan;

import com.sun.jna.platform.win32.WinDef;

public enum BaudRate {
    BPS_10K((byte)0x31, (byte)0x1C),
    BPS_20K((byte)0x18, (byte)0x1C),
    BPS_40K((byte)0x87, (byte)0xFF),
    BPS_50K((byte)0x09, (byte)0x1C),
    BPS_80K((byte)0x83, (byte)0xFF),
    BPS_100K((byte)0x04, (byte)0x1C),
    BPS_125K((byte)0x03, (byte)0x1C),
    BPS_200K((byte)0x81, (byte)0xFA),
    BPS_250K((byte)0x01, (byte)0x1C),
    BPS_400K((byte)0x80, (byte)0xFA),
    BPS_500K((byte)0x00, (byte)0x1C),
    BPS_666K((byte)0x80, (byte)0xB6),
    BPS_800K((byte)0x00, (byte)0x16),
    BPS_1000K((byte)0x00, (byte)0x14),
    BPS_33_33K((byte)0x09, (byte)0x6F),
    BPS_66_66K((byte)0x04, (byte)0x6F),
    BPS_83_33K((byte)0x03, (byte)0x6F);

    private final WinDef.BYTE timing0;
    private final WinDef.BYTE timing1;

    BaudRate(byte timer0, byte timer1) {
        this.timing0 = new WinDef.BYTE(timer0);
        this.timing1 = new WinDef.BYTE(timer1);
    }

    public WinDef.BYTE getTiming0() {
        return timing0;
    }

    public WinDef.BYTE getTiming1() {
        return timing1;
    }
}
