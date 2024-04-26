import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@ComponentScan(basePackages = "hany.tech")
public class LogbackAppenderApp {

    private static final Logger logger = LogManager.getLogger(LogbackAppenderApp.class);

    public static void main(String[] args) {
        SpringApplication.run(LogbackAppenderApp.class, args).close();
    }

    @org.springframework.context.annotation.Bean
    public CommandLineRunner run() {
        return args -> {
            logger.info("Application started!");
            logger.warn("This is a warning message");
            logger.error("This is an error message");
            logger.debug("Debug message"); // This might not appear if the root logger level is higher than DEBUG

            System.out.println("CommandLineRunner executed"); // Console output for confirmation
        };
    }
}
