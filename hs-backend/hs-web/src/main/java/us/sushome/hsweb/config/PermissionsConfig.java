package us.sushome.hsweb.config;

import org.springframework.stereotype.Service;

@Service(value = "Pm")
public class PermissionsConfig {
    public boolean check(String permissions) {
        System.out.println("PermissionsConfig permission:"+permissions);
        // 实现权限检查逻辑
        
        return true;
    }
}
