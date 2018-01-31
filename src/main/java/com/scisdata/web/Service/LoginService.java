package com.scisdata.web.Service;

import com.scisdata.web.bean.User;

/**
 * Created by fangshilei on 18/1/24.
 */
public interface LoginService {
    public User loginByName(String userName,String passWord);
}
