package com.rameshputalapattu.tstracker.config;

import com.rameshputalapattu.tstracker.dao.TimeSheetDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

@Configuration
@Slf4j
public class TSConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Value("${spring.datasource.url}")
    private String dataSourceURL;

    @Value("${spring.datasource.username:}")
    private String dataSourceUserName;


    @Value("${spring.datasource.password:}")
    private String dataSourcePassword;

    @Value("${jooq.dialect}")
    private String jooqDialect;

    @Bean
    public TimeSheetDAO getTimeSheetDAO() throws SQLException {

        return new TimeSheetDAO(dataSourceURL,
                dataSourceUserName,
                dataSourcePassword,
                jooqDialect
        );
    }

}
