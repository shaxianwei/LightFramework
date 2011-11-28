package lightframework.data.configuration;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public class XMLConfiguration implements Configurationable {

    private String fileName = "";

    public XMLConfiguration(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, String> getConnectionUrls() {
        return new HashMap<String, String>();
    }

    @Override
    public String getConnectionPoolConfigFile() {
        return System.getProperty("user.dir").replace('\\', '/') + "/proxool.xml";
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
