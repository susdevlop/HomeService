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
        System.out.println("  _   _  ___  __  __ _____   ____  _____ ______     _____ ____ _____ \n" +
                " | | | |/ _ \\|  \\/  | ____| / ___|| ____|  _ \\ \\   / /_ _/ ___| ____|\n" +
                " | |_| | | | | |\\/| |  _|   \\___ \\|  _| | |_) \\ \\ / / | | |   |  _|  \n" +
                " |  _  | |_| | |  | | |___   ___) | |___|  _ < \\ V /  | | |___| |___ \n" +
                " |_| |_|\\___/|_|  |_|_____| |____/|_____|_| \\_\\ \\_/  |___\\____|_____|\n" +
                " copy right 2024.                               power by TzuHsing Su");
    }
}
