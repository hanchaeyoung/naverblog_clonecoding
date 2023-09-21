package spring.naverblog_clonecoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NaverblogClonecodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverblogClonecodingApplication.class, args);
		System.out.println("실행 완료");
	}

}
