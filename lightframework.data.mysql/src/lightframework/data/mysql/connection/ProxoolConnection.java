package lightframework.data.mysql.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public class ProxoolConnection implements Connectionable {

    private static String filename;
    private volatile static ProxoolConnection instance = null;

    public ProxoolConnection(String configFileName) {
        filename = configFileName;
    }

    @Override
    public Connection getConnection(String dbAlias) {
        try {
            return DriverManager.getConnection("proxool." + dbAlias);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ProxoolConnection getInstance(String configFileName) {
        if (instance == null) {
            synchronized (ProxoolConnection.class) {
                if (instance == null) {
                    instance = new ProxoolConnection(configFileName);
                    loadConfig();
                }
            }
        }
        return instance;
    }

    private static void loadConfig() {
        try {
            if (filename.endsWith("xml")) {
                JAXPConfigurator.configure(filename, false);
            } else {
                PropertyConfigurator.configure(filename);
            }
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}