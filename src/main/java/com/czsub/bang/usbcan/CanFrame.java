package com.czsub.bang.usbcan;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class CanFrame extends Structure {
    /**
     * 帧ID
     */
    public int id;

    /**
     * 设备接收到某一帧的时间标识。时间标示从CAN卡上电开始计时，计时单位为0.1ms。
     */
    public int timeStamp;

    /**
     * 是否使用时间标识，为1时TimeStamp有效，TimeFlag和TimeStamp只在此帧为接收帧时
     * 有意义。
     */
    public byte timeFlag;

    /**
     * 发送帧类型。
     * =0时为正常发送（发送失败会自动重发，重发时间为4秒，4秒内没有发出则取消）；
     * =1时为单次发送（只发送一次，发送失败不会自动重发，总线只产生一帧数据）；
     * 其它值无效。
     */
    public byte sendType;

    /**
     * 是否是远程帧。
     * =0时为为数据帧，=1时为远程帧（数据段空）。
     */
    public byte remoteFlag;

    /**
     * 是否是扩展帧。
     * 是否是扩展帧。=0时为标准帧（11位ID），=1时为扩展帧（29位ID）。
     */
    public byte externFlag;

    /**
     * 数据长度 DLC (<=8)，即CAN帧Data有几个字节。约束了后面Data[8]中的有效字节。
     */
    public byte dataLength;

    /**
     * CAN帧的数据。由于CAN规定了最大是8个字节，所以这里预留了8个字节的空间，受
     * DataLen约束。如DataLen定义为3，即Data[0]、Data[1]、Data[2]是有效的。
     */
    public byte[] data = new byte[8];

    /**
     * 系统保留。
     */
    public byte[] reserved = new byte[3];

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "id",
                "timeStamp",
                "timeFlag",
                "sendType",
                "remoteFlag",
                "externFlag",
                "dataLength",
                "data",
                "reserved"
        );
    }
}
