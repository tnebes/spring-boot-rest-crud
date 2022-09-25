package tnebes.hr.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tnebes.hr.crud.util.StartupUtil;

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		StartupUtil.init();
		SpringApplication.run(CrudApplication.class, args);
	}

}
