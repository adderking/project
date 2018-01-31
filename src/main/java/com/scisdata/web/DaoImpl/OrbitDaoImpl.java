package com.scisdata.web.DaoImpl;

import com.scisdata.web.Dao.LoginDao;
import com.scisdata.web.Dao.OrbitDao;
import com.scisdata.web.basedao.BaseDaoSupport;
import com.scisdata.web.basedao.interfaces.BaseDao;
import com.scisdata.web.bean.MacTrace;
import org.springframework.stereotype.Repository;

/**
 * Created by fangshilei on 18/1/26.
 */
@Repository("orbitDao")
public class OrbitDaoImpl extends BaseDaoSupport<MacTrace> implements OrbitDao {
}
