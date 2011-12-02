package lightframework.data.configuration;

import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public class INIConfiguration extends AbstractConfiguration implements Configurationable {

    private String fileName;
    private HashMap<String, Properties> sections = new HashMap<String, Properties>(5);
    private String currentSecion;
    private Properties current;
    private boolean isEnablePool = true;
    private HashMap<String, String> connectionUrls;
    private String configFile;
    private String poolType;

    public INIConfiguration(String fileName) {
        this.fileName = fileName;
        this.initialize();
    }

    @Override
    public Map<String, String> getConnectionUrls() {
        return this.connectionUrls;
    }

    @Override
    public String getConnectionPoolConfigFile() {
        if (this.configFile.indexOf(':') != -1) {
            return this.configFile;
        }
        return System.getProperty("user.dir").replace('\\', '/') + this.configFile;
    }

    @Override
    public boolean isEnableConnectionPool() {
        return this.isEnablePool;
    }

    @Override
    public String getConnectionPoolType() {
        return this.poolType;
    }

    @Override
    public void refresh() {
        this.initialize();
    }

    private void initialize() {
        this.IniReader(this.fileName);

        String dataSection = "lightframework.data";
        String dbalaisSection = "lightframework.data.connectionurls";

        this.isEnablePool = this.getBoolean(dataSection, "isEnableConnectionPool");
        this.poolType = this.getValue(dataSection, "connectionPoolType");
        this.configFile = this.getValue(dataSection, "connectionPoolConfigFile");
        this.connectionUrls = this.getUrls(dbalaisSection);
    }

    private HashMap<String, String> getUrls(String section) {
        HashMap<String, String> urls = new HashMap<String, String>(5);
        Properties properties = this.getProperties(section);
        for (Object key : properties.keySet()) {
            urls.put(key.toString(), properties.get(key).toString());
        }

        return urls;
    }

    private void IniReader(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            read(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }

    private void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]")) {
            currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
            current = new Properties();
            sections.put(currentSecion.trim(), current);
        } else if (line.matches(".*=.*")) {
            if (current != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                current.setProperty(name.trim(), value.trim());
            }
        }
    }

    private Properties getProperties(String section) {
        Properties p = (Properties) sections.get(section);
        return p;
    }

    private boolean getBoolean(String section, String name) {
        String value = getValue(section, name);
        return !(value == null || value.equals("0") || value.equals("no") || value.equals("false"));
    }

    private int getInt(String section, String name) {
        String value = getValue(section, name);
        if (value == null) {
            return 0;
        }
        int intValue = Integer.valueOf(value).intValue();
        return intValue;
    }

    private float getFloat(String section, String name) {
        String value = getValue(section, name);
        if (value == null) {
            return 0.0f;
        }
        float floatValue = Float.valueOf(value).floatValue();
        return floatValue;
    }

    private String getValue(String section, String name, String def) {
        Properties p = (Properties) sections.get(section);

        if (p == null) {
            return null;
        }

        String value = p.getProperty(name);
        if (value == null) {
            value = def;
        }
        return value;
    }

    private String getValue(String section, String name) {
        return getValue(section, name, "");
    }
}
