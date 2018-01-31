package com.scisdata.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.scisdata.web.bean.User;
import com.scisdata.web.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangshilei on 18/1/23.
 */
@Controller
@RequestMapping("/mvc")
public class LoginController extends BaseController{
    @Autowired
    private LoginService loginService;
    /**
     * 页面访问
     * @return
     */
    @RequestMapping(value = "/loginView")
    public String loginView(){
        return "view/login/login";
    }

    /**
     * 登录验证
     * @param userName 用户名
     * @param passWord 密码
     * @return
     */
    @RequestMapping(value = "/loginAction")
    @ResponseBody
    public  String loginAction( String userName, String passWord){
        //用户名和密码不为空,进行登录验证
        User user = this.loginService.loginByName(userName,passWord);
        Map map = new HashMap();
        if(user != null&&userName.equals(user.getUsername())){
            map.put("result","success");
        }else{
            map.put("result","fail");
        }
        return JSONObject.toJSONString(map);
    }
    @RequestMapping(value = "/home")
    public  String home(String userName,String passWord){
        //用户名和密码不为空,进行登录验证
        return "view/login/home";
    }
}
