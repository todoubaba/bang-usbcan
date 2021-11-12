package com.czsub.bang.usbcan;

import com.sun.jna.FunctionMapper;
import com.sun.jna.NativeLibrary;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UsbcanFunctionMapper implements FunctionMapper {
    private static final Map<String, String> functionMap = new HashMap<>();

    static {
        functionMap.put("openDevice", "VCI_OpenDevice");
        functionMap.put("closeDevice", "VCI_CloseDevice");
        functionMap.put("resetDevice", "VCI_UsbDeviceReset");
        functionMap.put("initCAN", "VCI_InitCAN");
        functionMap.put("readBoardInfo", "VCI_ReadBoardInfo");
        functionMap.put("findAllDevice", "VCI_FindUsbDevice2");
        functionMap.put("getReceiveNum", "VCI_GetReceiveNum");
        functionMap.put("clearBuffer", "VCI_ClearBuffer");
        functionMap.put("startCAN", "VCI_StartCAN");
        functionMap.put("resetCAN", "VCI_ResetCAN");
        functionMap.put("transmit", "VCI_Transmit");
        functionMap.put("receive", "VCI_Receive");
    }

    @Override
    public String getFunctionName(NativeLibrary library, Method method) {
        return functionMap.get(method.getName());
    }
}
