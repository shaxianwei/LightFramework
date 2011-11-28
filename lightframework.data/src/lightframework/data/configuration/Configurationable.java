package lightframework.data.configuration;

import java.util.Map;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public interface Configurationable {

    Map<String, String> getConnectionUrls();

    boolean isEnableConnectionPool();

    String getConnectionPoolType();

    String getConnectionPoolConfigFile();
    
    void refresh();
}
