package personal.personalblogreturn;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("personal.personalblogreturn.mapper")
@SpringBootApplication
public class PersonalBlogReturnApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogReturnApplication.class, args);
	}

}
