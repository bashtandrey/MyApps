package org.bashtan.MyApps.configs;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class DataSourceManager {

    private final DataSource dataSource;

    @PreDestroy
    public void closeDataSource() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
            System.out.println("HikariDataSource closed.");
        }
    }
}