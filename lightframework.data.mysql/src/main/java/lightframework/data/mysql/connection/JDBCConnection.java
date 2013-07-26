package lightframework.data.mysql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public class JDBCConnection implements Connectionable {

    private Map<String, String> connectionStrings;
    private volatile static JDBCConnection instance = null;

    public JDBCConnection(Map<String, String> connectionStrings) {
        this.connectionStrings = connectionStrings;
    }

    @Override
    public Connection getConnection(String dbAlias) {
        String url = this.getUrl(dbAlias);
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static JDBCConnection getInstance(Map<String, String> connectionStrings) {
        if (instance == null) {
            synchronized (JDBCConnection.class) {
                if (instance == null) {
                    instance = new JDBCConnection(connectionStrings);
                }
            }
        }
        return instance;
    }

    private String getUrl(String dbAlias) {
        if (this.connectionStrings.containsKey(dbAlias)) {
            return this.connectionStrings.get(dbAlias);
        }
        return "";
    }
}
