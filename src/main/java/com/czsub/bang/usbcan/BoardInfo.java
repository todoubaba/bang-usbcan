package com.czsub.bang.usbcan;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

import java.util.Arrays;
import java.util.List;

public class BoardInfo extends Structure {
    public WinDef.USHORT hardwareVersion;
    public WinDef.USHORT firmwareVersion;
    public WinDef.USHORT driverVersion;
    public WinDef.USHORT interfaceVersion;
    public WinDef.USHORT irqNum;
    public WinDef.BYTE canNum;
    public WinDef.CHAR[] serialNum = new WinDef.CHAR[20];
    public WinDef.CHAR[] hardwareType = new WinDef.CHAR[40];
    public WinDef.USHORT[] reserved = new WinDef.USHORT[4];

    @Override
    public String toString() {
        return String.format(
                "硬件版本：%x\n" +
                        "软件版本：%x\n" +
                        "驱动版本：%x\n" +
                        "接口版本：%x\n" +
                        "CAN数量：%x\n" +
                        "序列号：%s\n" +
                        "硬件类型：%s\n",
                hardwareVersion.intValue(),
                firmwareVersion.intValue(),
                driverVersion.intValue(),
                interfaceVersion.intValue(),
                canNum.intValue(),
                charArrayToString(serialNum),
                charArrayToString(hardwareType)
        );
    }

    private static String charArrayToString(WinDef.CHAR[] array) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < array.length; i++) {
            int value = array[i].intValue();
            if (value > 0) {
                sb.append((char)value);
            }
        }
        return sb.toString();
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "hardwareVersion",
                "firmwareVersion",
                "driverVersion",
                "interfaceVersion",
                "irqNum",
                "canNum",
                "serialNum",
                "hardwareType",
                "reserved"
        );
    }
}
