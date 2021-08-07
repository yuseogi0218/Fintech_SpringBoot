package spring.finEdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄링 사용할 수 있도록 함
public class FinEduApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinEduApplication.class, args);
	}

}
