package us.sushome.hsweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class HsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HsWebApplication.class, args);
    }

}
