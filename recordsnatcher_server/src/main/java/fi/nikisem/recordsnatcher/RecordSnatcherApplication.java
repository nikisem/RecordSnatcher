package fi.nikisem.recordsnatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.*;


@SpringBootApplication
public class RecordSnatcherApplication {


    public static void main(String[] args) {

        SpringApplication.run(RecordSnatcherApplication.class, args);

        Mailer mailer = new Mailer();
        mailer.runMailer();

    }

}