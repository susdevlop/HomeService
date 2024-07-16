package us.sushome.hsweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableCaching
@ComponentScan(basePackages = {"us.sushome"})
@MapperScan("us.sushome.db.dao")// 指定要扫描的Mapper类的包的路径
@SpringBootApplication()
@EnableAsync
public class HsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(HsWebApplication.class, args);
    }
}
