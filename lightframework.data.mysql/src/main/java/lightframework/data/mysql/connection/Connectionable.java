package lightframework.data.mysql.connection;

import java.sql.Connection;

/**
 *
 * @author Tom Deng <tom.deng@codebook.in>
 */
public interface Connectionable {

    Connection getConnection(String dbAlias);
}
