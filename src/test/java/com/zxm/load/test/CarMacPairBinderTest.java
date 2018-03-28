package com.zxm.load.test;

import com.zxm.load.CarMacPairBinder;
import org.junit.Test;

import java.text.ParseException;

public class CarMacPairBinderTest {

    /**
     * 绑定车辆与mac地址，并存放入Redis中
     * @throws ParseException
     */
    @Test
    public void bind() throws ParseException {
        CarMacPairBinder computer = new CarMacPairBinder();
        String startDate = "2018-01-31 15:38:35";
        computer.execute(startDate);
    }



}
