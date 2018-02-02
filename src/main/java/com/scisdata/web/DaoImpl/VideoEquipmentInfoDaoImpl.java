package com.scisdata.web.DaoImpl;

import com.scisdata.web.Dao.VideoEquipmentInfoDao;
import com.scisdata.web.basedao.BaseDaoSupport;
import com.scisdata.web.bean.VideoEquipmentInfo;
import org.springframework.stereotype.Repository;

@Repository("videoEquipmentInfoDao")
public class VideoEquipmentInfoDaoImpl extends BaseDaoSupport<VideoEquipmentInfo> implements VideoEquipmentInfoDao {

}
