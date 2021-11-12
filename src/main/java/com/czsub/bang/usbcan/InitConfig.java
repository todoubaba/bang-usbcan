package com.czsub.bang.usbcan;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

import java.util.Arrays;
import java.util.List;

/**
 * 初始化CAN的配置
 */
public class InitConfig extends Structure {
    public WinDef.DWORD accCode;
    public WinDef.DWORD accMask;
    public WinDef.DWORD reserved;
    public WinDef.BYTE filter;
    public WinDef.BYTE timing0;
    public WinDef.BYTE timing1;
    public WinDef.BYTE mode;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "accCode",
                "accMask",
                "reserved",
                "filter",
                "timing0",
                "timing1",
                "mode"
        );
    }
}
