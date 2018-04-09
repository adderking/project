package com.zxm.load.test;

import com.zxm.load.CarMacPairBinder;
import org.junit.Test;

import java.text.ParseException;

public class CarMacPairBinderTest {

    /**
     * 绑定车辆与mac地址，并存放入Redis中
     * 需要修改数据库中几条数据，执行下面语句
     * update cartrace set startTime='2018-03-21 12:48:15' where primaryId='f3dbc209-586f-4a01-9b42-406e85034492';
     * update cartrace set startTime='2018-03-21 12:49:15' where primaryId='1c4a6322-0fae-448d-994e-f260d2907f8d';
     * update cartrace set startTime='2018-03-21 12:50:15' where primaryId='3a69a2f4-2412-425c-a3b4-bae49f8011b6';
     * update cartrace set startTime='2018-03-21 12:51:15' where primaryId='b53eba34-6b36-4378-836e-e0924d6da443';
     * @throws ParseException
     */
    @Test
    public void bind() throws ParseException {
        CarMacPairBinder computer = new CarMacPairBinder();
        String startDate = "2018-01-31 15:38:35";
        computer.execute(startDate);
    }



}
