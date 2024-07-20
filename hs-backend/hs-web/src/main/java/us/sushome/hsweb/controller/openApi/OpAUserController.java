package us.sushome.hsweb.controller.openApi;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.sushome.common.controller.BaseController;
import us.sushome.db.model.HsUser;
import us.sushome.db.service.IHsUserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/openApi")
public class OpAUserController extends BaseController {
    @Autowired
    private IHsUserService iHsUserService;

    @PostMapping("/login")
    public String login(@RequestBody  String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String userName = jsonObject.get("userName").toString();
        String userPasswd = jsonObject.get("userPasswd").toString();
        Boolean isRememberMe = jsonObject.getBoolean("remember");
        if(isRememberMe == null){
            isRememberMe = false;
        }
        return iHsUserService.login(userName,userPasswd,isRememberMe);
    }

    @PostMapping("/register")
    public String register(@RequestBody String body){
        logger.info("body"+body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        String userName = jsonObject.get("userName").toString();
        String userPasswd = jsonObject.get("userPasswd").toString();
        String token = iHsUserService.register(userName,userPasswd);
        return token;
    }
}
