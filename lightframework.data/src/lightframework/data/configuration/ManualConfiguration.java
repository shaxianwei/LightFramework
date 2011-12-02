package lightframework.data.configuration;

import java.util.Map;

/**
 *
 * @author Tom Deng <tom.deng@hotmail.com>
 */
public class ManualConfiguration  extends AbstractConfiguration implements Configurationable{

    public ManualConfiguration(){
        
    }
    
    @Override
    public String getConnectionPoolConfigFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getConnectionPoolType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> getConnectionUrls() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEnableConnectionPool() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
