package lightframework.data.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public class PropertiesConfiguration implements Configurationable {

    private String fileName = "";

    public PropertiesConfiguration(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, String> getConnectionUrls() {
        return new HashMap<String, String>();
    }

    @Override
    public String getConnectionPoolConfigFile() {
        return System.getProperty("user.dir").replace('\\', '/') + "/config/proxool.xml";
    }

    @Override
    public boolean isEnableConnectionPool() {
        return true;
    }

    @Override
    public String getConnectionPoolType() {
        return "proxool";
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
