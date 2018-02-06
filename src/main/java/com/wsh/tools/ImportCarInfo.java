package com.wsh.tools;

import com.wsh.tools.utils.ConnectionUtil;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImportCarInfo {

    private Map<String, String> carPlate2carIdMap = new HashMap<>();

    public ImportCarInfo() throws SQLException {
        Connection conn = ConnectionUtil.getInstance().getConnection();
        cacheCarInfo(conn);
        conn.close();
    }

    public void cacheCarInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, carPlate FROM carinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            String carPlate = resultSet.getString(2);
            String carId = resultSet.getString(1);
            carPlate2carIdMap.put(carPlate, carId);
        }
        resultSet.close();
        pst.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    }

}
