package us.sushome.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import us.sushome.common.utils.JwtUtils;
import us.sushome.common.utils.StringUtils;
import us.sushome.db.model.HsUser;
import us.sushome.db.dao.HsUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * $!{table.comment} 服务类
 * </p>
 *
 * @author sushome
 * @since 2024-07-18
 */
@Service
public class IHsUserService extends ServiceImpl<HsUserMapper, HsUser> {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public List<HsUser> getAllUser(){
        return this.list();
    }
    public HsUser findByUserName(String userName) {
        QueryWrapper<HsUser> queryWrapper = new QueryWrapper<HsUser>();
        queryWrapper.eq("user_name", userName); // user_name 是数据库表中的列名
        return this.getOne(queryWrapper);
    }

    //openApi
    public String login(String userName,String userPasswd,Boolean isRememberMe){
        if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userPasswd)){
            HsUser user = this.findByUserName(userName);
            if(user != null){
                if (this.bCryptPasswordEncoder.matches(userPasswd, user.getUserPasswd())) {
                    // 生成 token
                    String token = JwtUtils.generateToken(userName, user.getUserRoleId(), isRememberMe);
                    // 认证成功后，设置认证信息到 Spring Security 上下文中
                    Authentication authentication = JwtUtils.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return token;
                }
            }
        }
        return "";
    }
    public String register(String userName,String userPasswd){
        HsUser user = this.findByUserName(userName);
        if(user != null){
            return "";
        }else{
            String cryptPassword = this.bCryptPasswordEncoder.encode(userPasswd);
            user = new HsUser();
            user.setUserName(userName);
            user.setUserPasswd(cryptPassword);
            user.setUserRoleId(1);
            this.save(user);
            return this.login(userName,userPasswd,false);
        }
    }
    public Boolean logout(){
        SecurityContextHolder.clearContext();
        return true;
    }
}