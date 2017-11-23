package info.alex.learn.learnavro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class LearnAvroApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearnAvroApplication.class, args);

		new App().main();

	}
}
