package projeto.com.br.form.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5500")
@SpringBootApplication
public class FormProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormProcessingApplication.class, args);
	}

}
