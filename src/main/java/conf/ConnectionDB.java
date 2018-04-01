package conf;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionDB {

    private Connection connection;
    final Logger logger = LoggerFactory.getLogger(ConnectionDB.class);

    final private String host = "ec2-54-225-249-161.compute-1.amazonaws.com:5432";
    final private String database = "d3r9gpjgge0lis";
    final private String user = "vfkbzvtnucjvds";
    final private String params = "ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    final private String password = "a554265a46a50fade912567a42bdc5c05bc60181a10fe879d2cfd45e3710ba5c";

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + database + "?" + params, user, password);
        } catch (SQLException e) {
            logger.error("Conexión error", e);
        }

        return connection;
    }
}
