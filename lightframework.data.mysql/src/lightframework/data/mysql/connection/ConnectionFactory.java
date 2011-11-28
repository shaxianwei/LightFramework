package lightframework.data.mysql.connection;

import lightframework.data.configuration.Configurationable;
import java.sql.Connection;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public class ConnectionFactory {

    private ConnectionFactory() {
    }

    public static Connection creator(String dbAlias, Configurationable config) {
        if (config.isEnableConnectionPool()) {
            return getPoolConnection(dbAlias, config.getConnectionPoolType(),
                    config.getConnectionPoolConfigFile());
        }
        return JDBCConnection.getInstance(config.getConnectionUrls()).getConnection(dbAlias);
    }

    private static Connection getPoolConnection(String dbAlias, String type, String configFileName) {
        if (type.equals("proxool")) {
            return ProxoolConnection.getInstance(configFileName).getConnection(dbAlias);
        }
        return ProxoolConnection.getInstance(configFileName).getConnection(dbAlias);
    }
}
