package com.scisdata.web.DaoImpl;

import com.scisdata.web.Dao.ExecuteDao;
import com.scisdata.web.Dao.OrbitDao;
import com.scisdata.web.basedao.BaseDaoSupport;
import com.scisdata.web.bean.MacTrace;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * Created by fangshilei on 18/1/31.
 */
@Repository("executeDao")
public class ExecuteDaoImpl extends BaseDaoSupport<HashMap> implements ExecuteDao {
}
