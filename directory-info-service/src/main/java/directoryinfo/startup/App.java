package directoryinfo.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("directoryinfo.controllers")
public class App {
	
	public static void main(String[] args)
	{
		System.out.println("App starting" );
		SpringApplication.run(App.class, args);
	}
}
