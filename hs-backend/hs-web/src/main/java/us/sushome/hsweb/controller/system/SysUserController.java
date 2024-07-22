package us.sushome.hsweb.controller.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.sushome.common.controller.BaseController;
import us.sushome.db.model.HsUser;
import us.sushome.db.service.IHsUserService;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SysUserController extends BaseController {
    @Autowired
    private IHsUserService iHsUserService;


    @PostMapping("/getAllUser")
    public List<HsUser> getAllUser(){
        List<HsUser> list = iHsUserService.getAllUser();
        logger.info("list"+list);
        return list;
    }

    @PreAuthorize("@Pm.check('system:getAllUser')")
    @PostMapping("/getAllUserByAdminRole")
    public List<HsUser> getAllUserByAdminRole(){
        List<HsUser> list = iHsUserService.getAllUser();
        logger.info("list"+list);
        return list;
    }
}
