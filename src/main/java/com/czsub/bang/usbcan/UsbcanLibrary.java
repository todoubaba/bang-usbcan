package com.czsub.bang.usbcan;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

import java.net.URL;
import java.util.HashMap;

public interface UsbcanLibrary extends Library {
    /**
     * 设备类型
     */
    int VCI_USBCAN2 = 4;

    /**
     * 返回成功
     */
    int RESULT_SUCCESS = 1;

    /**
     * 返回失败
     */
    int RESULT_FAILED = 0;

    /**
     * 设备不存在
     */
    int NOT_EXISTS = -1;

    int openDevice(int devType, int devIndex, int reserved);
    int closeDevice(int devType, int devIndex);
    int resetDevice(int devType, int devIndex);
    int initCAN(int devType, int devIndex, int canIndex, InitConfig config);
    int readBoardInfo(int devType, int devIndex, BoardInfo boardInfo);
    int findAllDevice(BoardInfo[] boardInfos);
    int getReceiveNum(int devType, int devIndex, int canIndex);
    int clearBuffer(int devType, int devIndex, int canIndex);
    int startCAN(int devType, int devIndex, int canIndex);
    int resetCAN(int devType, int devIndex, int canIndex);
    int transmit(int devType, int devIndex, int canIndex, CanFrame canFrame, int length);
    int receive(int devType, int devIndex, int canIndex, CanFrame[] canFrame, int length, int waitTime);

    static UsbcanLibrary loadLibrary() {
        URL url = UsbcanLibrary.class.getResource("/");
        assert url != null;
        System.setProperty("jna.library.path", url.toExternalForm());
        HashMap<String, Object> options = new HashMap<>();
        options.put(Library.OPTION_FUNCTION_MAPPER, new UsbcanFunctionMapper());
        return Native.load("ControlCAN", UsbcanLibrary.class, options);
    }
}
