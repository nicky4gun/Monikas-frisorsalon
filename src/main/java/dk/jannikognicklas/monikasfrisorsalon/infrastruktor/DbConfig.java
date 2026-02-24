package dk.jannikognicklas.monikasfrisorsalon.infrastruktor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfig {
    private String url;
    private String username;
    private String password;

    public DbConfig() {
        loadProperties();
    }

    private void loadProperties() {
        Properties props = new Properties();

        try (InputStream input = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) throw new RuntimeException("db.properties file not found");

            props.load(input);

            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Unable to load database properties" + e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
