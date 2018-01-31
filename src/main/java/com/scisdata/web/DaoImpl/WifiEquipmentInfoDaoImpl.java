package com.scisdata.web.DaoImpl;

import com.scisdata.web.Dao.WifiEquipmentInfoDao;
import com.scisdata.web.basedao.BaseDaoSupport;
import com.scisdata.web.bean.WifiEquipmentInfo;
import org.springframework.stereotype.Repository;

@Repository("wifiEquipmentInfoDao")
public class WifiEquipmentInfoDaoImpl extends BaseDaoSupport<WifiEquipmentInfo> implements WifiEquipmentInfoDao {
}
