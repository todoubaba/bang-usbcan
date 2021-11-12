package com.czsub.bang.usbcan;

import com.sun.jna.platform.win32.WinDef;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsbcanTest {
    static final int DEVICE_TYPE = UsbcanLibrary.VCI_USBCAN2;
    static final int DEVICE_INDEX = 0;
    static final int CAN_INDEX_0 = 0;
    static final int CAN_INDEX_1 = 1;

    static UsbcanLibrary usbcan;

    @BeforeAll
    public static void beforeAll() {
        usbcan = UsbcanLibrary.loadLibrary();
        Assertions.assertNotNull(usbcan, "Error load library");
    }

    @BeforeEach
    public void testOpenAndClose() {
        int result = usbcan.openDevice(DEVICE_TYPE, DEVICE_INDEX, 0);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
    }

    @AfterEach
    public void closeDevice() {
        int result = usbcan.closeDevice(DEVICE_TYPE, DEVICE_INDEX);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
    }

    @Test
    @Order(1)
    public void testInitConfig() {
        InitConfig config = new InitConfig();
        config.accCode = new WinDef.DWORD(0x80000000L);
        config.accMask = new WinDef.DWORD(0xFFFFFFFF);
        config.reserved = new WinDef.DWORD(0);
        config.filter = IdFilterMode.ALL.getValue();
        config.timing0 = BaudRate.BPS_500K.getTiming0();
        config.timing1 = BaudRate.BPS_500K.getTiming1();
        config.mode = WorkingMode.NORMAL.getValue();

        int result = usbcan.initCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0, config);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
    }

    @Test
    @Order(2)
    public void testReadBoardInfo() {
        BoardInfo boardInfo = new BoardInfo();
        int result = usbcan.readBoardInfo(DEVICE_TYPE, DEVICE_INDEX, boardInfo);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
        System.out.println(boardInfo);
    }

    @Test
    @Order(3)
    void testGetReceiveNum() {
        int result = usbcan.getReceiveNum(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0);
        Assertions.assertNotEquals(UsbcanLibrary.NOT_EXISTS, result);
        System.out.println("Received num: " + result);
    }

    @Test
    @Order(5)
    void testStartAndReset() throws InterruptedException {
        int result = usbcan.startCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0);
        Assertions.assertNotEquals(UsbcanLibrary.NOT_EXISTS, result);
        Thread.sleep(100);
        result = usbcan.resetCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0);
        Assertions.assertNotEquals(UsbcanLibrary.NOT_EXISTS, result);
    }

    @Test
    @Order(6)
    void testTransmitAndReceive() throws InterruptedException {
        InitConfig config = new InitConfig();
        config.accCode = new WinDef.DWORD(0x80000000L);
        config.accMask = new WinDef.DWORD(0xFFFFFFFF);
        config.reserved = new WinDef.DWORD(0);
        config.filter = IdFilterMode.ALL.getValue();
        config.timing0 = BaudRate.BPS_500K.getTiming0();
        config.timing1 = BaudRate.BPS_500K.getTiming1();
        config.mode = WorkingMode.NORMAL.getValue();

        // Init can 0 & 1
        int result = usbcan.initCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0, config);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
        result = usbcan.initCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_1, config);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
        result = usbcan.startCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0);
        Assertions.assertNotEquals(UsbcanLibrary.NOT_EXISTS, result);
        result = usbcan.startCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_1);
        Assertions.assertNotEquals(UsbcanLibrary.NOT_EXISTS, result);

        // Send frame
        for (int i = 0; i < 10; i++) {
            CanFrame canFrame = new CanFrame();
            canFrame.id = 0x01;
            canFrame.sendType = 0;
            canFrame.dataLength = 8;
            for (byte j = 0; j < 8; j++) {
                canFrame.data[j] = (byte) (i * 10 + j);
            }
            result = usbcan.transmit(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0, canFrame, 1);
            Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
        }

        // Receive frame
        int count = 0;
        while (count < 10) {
            CanFrame[] received = new CanFrame[10];
            result = usbcan.receive(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_1, received, received.length, 0);
            Assertions.assertNotEquals(UsbcanLibrary.NOT_EXISTS, result);
            System.out.println("Received: " + result);
            for (int i = 0; i < result; i++) {
                System.out.print("Package " + i + ":");
                for (int j = 0; j < received[i].dataLength; j++) {
                    System.out.print(received[i].data[j]);
                    System.out.print(" ");
                }
                System.out.println();
            }
            Thread.sleep(100);
            count++;
        }
    }

    @Test
    @Order(7)
    void testClearBuffer() {
        int result = usbcan.clearBuffer(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0);
        Assertions.assertNotEquals(UsbcanLibrary.NOT_EXISTS, result);
    }

    @Test
    @Order(8)
    void testResetDevice() throws InterruptedException {
        int result = usbcan.resetDevice(DEVICE_TYPE, DEVICE_INDEX);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);

        result = usbcan.startCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0);
        Assertions.assertEquals(UsbcanLibrary.RESULT_FAILED, result);

        Thread.sleep(1000); // 需要只够长的时间

        result = usbcan.openDevice(DEVICE_TYPE, DEVICE_INDEX, 0);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);

        result = usbcan.startCAN(DEVICE_TYPE, DEVICE_INDEX, CAN_INDEX_0);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);
    }

    @Test
    @Order(9)
    void testFindAllDevice() {
        BoardInfo[] boardInfos = new BoardInfo[50];
        int result = usbcan.findAllDevice(boardInfos);
        Assertions.assertEquals(UsbcanLibrary.RESULT_SUCCESS, result);

        for (int i = 0; i < result; i++) {
            System.out.println(boardInfos[i]);
        }
    }
}
