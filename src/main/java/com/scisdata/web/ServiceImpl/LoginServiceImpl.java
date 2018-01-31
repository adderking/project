package com.scisdata.web.ServiceImpl;

import com.scisdata.web.Dao.LoginDao;
import com.scisdata.web.bean.User;
import com.scisdata.web.Service.LoginService;
import com.scisdata.web.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by fangshilei on 18/1/24.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService{
    @Autowired
    private LoginDao loginDao;
    @Override
    public User loginByName(String userName, String passWord) {
        if(!StringUtils.isEmpty(userName)&&!StringUtils.isEmpty(passWord)){
            //对密码进行加密查询
            try {
                String password = MD5Util.getEncryption(passWord);
                String sql = "select * from User u where u.username ="+userName+" and u.password = "+password;
                User user = (User) this.loginDao.getMapBySql(sql);
                return user;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
