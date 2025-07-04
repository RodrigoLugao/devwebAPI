package rodrigolugao.simasAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SimasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimasApiApplication.class, args);
	}

}
