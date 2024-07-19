package us.sushome.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import us.sushome.db.model.HsUser;
import us.sushome.db.dao.HsUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    public List<HsUser> getAllUser(){
        return this.list();
    }
    public HsUser findByUserName(String userName) {
        QueryWrapper<HsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName); // user_name 是数据库表中的列名

        return this.getOne(queryWrapper);
    }

    //openApi
    public Boolean register(String userName,String userPasswd){
        HsUser user = this.findByUserName(userName);
        if(user != null){
            return false;
        }else{
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String cryptPassword = bCryptPasswordEncoder.encode(userPasswd);
            user = new HsUser();
            user.setUserName(userName);
            user.setUserPasswd(cryptPassword);
            this.save(user);
            return true;
        }
    }
}