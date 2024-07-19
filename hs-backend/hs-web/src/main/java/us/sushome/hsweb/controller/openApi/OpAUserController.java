package us.sushome.hsweb.controller.openApi;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String login(){

        return "list";
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody  String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String userName = jsonObject.get("userName").toString();
        String userPasswd = jsonObject.get("userPasswd").toString();
        Boolean isSuccess = iHsUserService.register(userName,userPasswd);
        return isSuccess;
    }
}
