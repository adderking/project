package com.zxm.load;

import com.zxm.load.utils.SystemConfig;
import com.zxm.load.utils.Constants;

public class DistanceRange {
    enum DrivingDirection {FROM, TO}
    private double distance;
    private int speed;
    private int error_time;
    private DrivingDirection dd;

    public DistanceRange(double distance, DrivingDirection dd) {
        this.distance = distance;
        this.speed = Integer.parseInt(SystemConfig.getString(Constants.RELATION_COMPUTER_CAR_SPEED));
        this.error_time = Integer.parseInt(SystemConfig.getString(Constants.RELATION_COMPUTER_ERROR_SECOND));
        this.dd = dd;
    }

    public int getX() {
        int timeGap = (int) (distance / speed);
        if(dd.equals(DrivingDirection.FROM)) {
            return -timeGap-error_time;
        } else {
            return timeGap-error_time;
        }

    }

    public int getY() {
        int timeGap = (int) (distance / speed);
        if(dd.equals(DrivingDirection.FROM)) {
            return -timeGap + error_time;
        } else {
            return timeGap + error_time;
        }
    }

    @Override
    public String toString() {
        return "DistanceRange{" +
                "distance=" + distance +
                ", speed=" + speed +
                ", error_time=" + error_time +
                ", dd=" + dd +
                '}';
    }
}
