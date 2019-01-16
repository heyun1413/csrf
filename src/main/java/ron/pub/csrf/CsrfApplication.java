package ron.pub.csrf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class CsrfApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication springApplication = new SpringApplication(CsrfApplication.class);
        Properties properties = new Properties();
        InputStream inputStream = CsrfApplication.class.getClassLoader().getResourceAsStream("csrf.properties");
        properties.load(inputStream);
        springApplication.setDefaultProperties(properties);
        springApplication.run(args);
    }

}

