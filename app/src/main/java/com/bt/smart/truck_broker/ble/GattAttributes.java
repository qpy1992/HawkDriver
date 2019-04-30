package com.bt.smart.truck_broker.ble;

import java.util.UUID;

/**
 * Description:ble uuid <br />
 */
public class GattAttributes {

    //UUID服务
    public final  static UUID UUID_SERVICE=UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb7");
    public final  static UUID UUID_CHARACTERISTIC_WRITE=UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cba");
    public final  static UUID UUID_CHARACTERISTIC_READ=UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb8");
    /** for notify */
    public final static UUID UUID_NOTIFICATION_DESCRIPTOR=UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");


    public final  static UUID UUID_OBL2_SERVICE=UUID.fromString("6E400001-E6AC-A7E7-B1B3-E699BAE8D000");
    public final  static UUID UUID_OBL2_CHARACTERISTIC_WRITE=UUID.fromString("6E400002-E6AC-A7E7-B1B3-E699BAE8D000");
    public final  static UUID UUID_OBL2_CHARACTERISTIC_READ=UUID.fromString("6E400003-E6AC-A7E7-B1B3-E699BAE8D000");



}
