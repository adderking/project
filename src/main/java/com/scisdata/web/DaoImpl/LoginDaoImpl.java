package com.scisdata.web.DaoImpl;

import com.scisdata.web.Dao.LoginDao;
import com.scisdata.web.basedao.BaseDaoSupport;
import com.scisdata.web.bean.User;
import org.springframework.stereotype.Repository;

/**
 * Created by fangshilei on 18/1/24.
 */
@Repository("loginDaoImpl")
public class LoginDaoImpl extends BaseDaoSupport<User> implements LoginDao {

}
