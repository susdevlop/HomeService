package us.sushome.hsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class HsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HsBackendApplication.class, args);
	}

}
