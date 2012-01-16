package lightframework.data.configuration;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public abstract class AbstractConfiguration {

    protected String dbalias = "default";

    public String getDefaultDbAlias() {
        return this.dbalias;
    }

    public void setDefaultDbAlias(String alias) {
        this.dbalias = alias;
    }
}
