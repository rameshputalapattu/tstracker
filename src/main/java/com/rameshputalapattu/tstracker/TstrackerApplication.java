package com.rameshputalapattu.tstracker;

import com.rameshputalapattu.tstracker.model.TimeSheet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


@SpringBootApplication
public class TstrackerApplication {


	public static void main(String[] args) {

		SpringApplication.run(TstrackerApplication.class, args);


	}

}
