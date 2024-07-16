package us.sushome.db.service;

import us.sushome.db.model.HsUser;
import us.sushome.db.dao.HsUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * $!{table.comment} 服务类
 * </p>
 *
 * @author sushome
 * @since 2024-07-16
 */
@Service
public class IHsUserService extends ServiceImpl<HsUserMapper, HsUser> {

    public List<HsUser> getAllUser(){
        return this.list();
    }
}