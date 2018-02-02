package com.wsh.tools;

import com.wsh.tools.utils.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ImportCarInfo {

    private Map<String, String> carPlate2carIdMap = new HashMap<>();

    public ImportCarInfo() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionHelper.createConnection();
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
}
