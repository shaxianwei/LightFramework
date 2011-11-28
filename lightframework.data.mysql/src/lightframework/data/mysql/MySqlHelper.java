package lightframework.data.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.Statement;
import java.sql.Savepoint;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Tom Deng
 */
public class MySqlHelper {

    private MySqlHelper() {
    }

    public static int executeNonQuery(Connection connection, String commandText, Object... commandParameters) {
        PreparedStatement preStat = null;

        try {
            preStat = getPreparedStatement(connection, commandText, commandParameters);
            return preStat.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(preStat);
        }
    }

    public static int executeNonQuery(String url, String commandText, Object... commandParameters) {
        Connection connection = null;

        try {
            connection = getConnection(url);
            return executeNonQuery(connection, commandText, commandParameters);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(connection);
        }
    }

    public static ResultSet executeReader(String url, String commandText) {
        return executeReader(url, commandText, (Object[]) null);
    }

    public static ResultSet executeReader(Connection connection, String commandText) {
        return executeReader(connection, commandText, (Object[]) null);
    }

    public static ResultSet executeReader(String url, String commandText, Object... commandParameters) {
        Connection connection = null;

        try {
            connection = getConnection(url);
            return executeReader(connection, commandText, commandParameters);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ResultSet executeReader(Connection connection, String commandText, Object... commandParameters) {
        PreparedStatement preStat = null;

        try {
            preStat = getPreparedStatement(connection, commandText, commandParameters);
            return preStat.executeQuery();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object executeScalar(String url, String commandText) {
        return executeScalar(url, commandText, (Object[]) null);
    }

    public static Object executeScalar(String url, String commandText, Object... commandParameters) {
        return executeScalar(url, commandText, false, commandParameters);
    }

    public static Object executeScalar(String url, String commandText, boolean isGenerateKey) {
        return executeScalar(url, commandText, isGenerateKey, (Object[]) null);
    }

    public static Object executeScalar(String url, String commandText, boolean isGenerateKey, Object... commandParameters) {
        Connection connection = null;

        try {
            connection = getConnection(url);
            return executeScalar(connection, commandText, isGenerateKey, commandParameters);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(connection);
        }
    }

    public static Object executeScalar(Connection connection, String commandText) {
        return executeScalar(connection, commandText, (Object[]) null);
    }

    public static Object executeScalar(Connection connection, String commandText, Object... commandParameters) {
        return executeScalar(connection, commandText, false, (Object[]) null);
    }

    public static Object executeScalar(Connection connection, String commandText, boolean isGenerateKey) {
        return executeScalar(connection, commandText, isGenerateKey, (Object[]) null);
    }

    public static Object executeScalar(Connection connection, String commandText, boolean isGenerateKey, Object... commandParameters) {
        PreparedStatement preStat = null;
        ResultSet rs = null;

        try {
            preStat = getPreparedStatement(connection, commandText, isGenerateKey, commandParameters);
            if (isGenerateKey) {
                preStat.executeUpdate();
                rs = preStat.getGeneratedKeys();
            } else {
                rs = preStat.executeQuery();
            }

            if (rs.next()) {
                return rs.getObject(1);
            }
            return 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(preStat);
            close(rs);
            close(connection);
        }
    }

    public static boolean executeTransaction(String url, SqlExpression sqlExpression) {
        ArrayList<SqlExpression> sqlExpressions = new ArrayList<SqlExpression>(1);
        sqlExpressions.add(sqlExpression);

        return executeTransaction(url, sqlExpressions);
    }

    public static boolean executeTransaction(Connection connection, SqlExpression sqlExpression) throws SQLException {
        ArrayList<SqlExpression> sqlExpressions = new ArrayList<SqlExpression>(1);
        sqlExpressions.add(sqlExpression);

        return executeTransaction(connection, sqlExpressions);
    }

    public static boolean executeTransaction(String url, List<SqlExpression> sqlExpressions) {
        Connection connection = null;

        try {
            connection = getConnection(url);
            return executeTransaction(connection, sqlExpressions);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(connection);
        }
    }

    public static boolean executeTransaction(Connection connection, List<SqlExpression> sqlExpressions) throws SQLException {
        if (sqlExpressions == null || sqlExpressions.isEmpty()) {
            throw new NullPointerException("sqlExpressions");
        }

        Savepoint svpt = null;
        boolean isSuccessfully = false;

        try {
            connection.setAutoCommit(false);
            svpt = connection.setSavepoint();

            for (SqlExpression sqlExpr : sqlExpressions) {
                PreparedStatement pstm = getPreparedStatement(connection, sqlExpr.getCommandText(), sqlExpr.getParameters());
                pstm.addBatch();
                pstm.executeBatch();
            }
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback(svpt);
            isSuccessfully = false;
            throw new RuntimeException(ex);
        } finally {
            connection.releaseSavepoint(svpt);
            close(connection);
        }

        return isSuccessfully;
    }

    public static Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }

    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static PreparedStatement getPreparedStatement(Connection connection, String commandText,
            Object[] commandParameters) throws SQLException {
        return getPreparedStatement(connection, commandText, false, commandParameters);
    }

    private static PreparedStatement getPreparedStatement(Connection connection, String commandText,
            boolean isGenerateKey, Object[] commandParameters) throws SQLException {
        PreparedStatement preStat = isGenerateKey
                ? connection.prepareStatement(commandText, Statement.RETURN_GENERATED_KEYS)
                : connection.prepareStatement(commandText);
        preStat.clearParameters();
        attachParameters(preStat, commandParameters);
        return preStat;
    }

    private static void attachParameters(PreparedStatement preparedStatement, Object[] parameters) {
        if (preparedStatement == null) {
            throw new NullPointerException("preparedStatement");
        }
        if (parameters == null) {
            return;
        }

        try {
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];

                if (parameter == null) {
                    preparedStatement.setNull(i + 1, Types.JAVA_OBJECT);
                    continue;
                }

                preparedStatement.setObject(i + 1, parameter);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
