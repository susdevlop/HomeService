package us.sushome.hsweb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.sushome.common.controller.BaseController;
import us.sushome.db.model.HsUser;
import us.sushome.db.service.IHsUserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HsUserController extends BaseController {
    @Autowired
    private IHsUserService iHsUserService;

    @PostMapping("/getAllUser")
    public List<HsUser> getAllUser(){
        List<HsUser> list = iHsUserService.getAllUser();
        logger.info("list"+list);
        return list;
    }
}
