package fi.nikisem.recordsnatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@SpringBootApplication
public class RecordSnatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordSnatcherApplication.class, args);

	}
}
