/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
        try {
            PreparedStatement preStat = getPreparedStatement(connection, commandText, commandParameters);
            return preStat.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static int executeNonQuery(String url, String commandText, Object... commandParameters) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url);
            return executeNonQuery(connection, commandText, commandParameters);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
            connection = DriverManager.getConnection(url);
            return executeReader(connection, commandText, commandParameters);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ResultSet executeReader(Connection connection, String commandText, Object... commandParameters) {
        try {
            PreparedStatement preStat = getPreparedStatement(connection, commandText, commandParameters);
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

    public static Object executeScalar(String url, String commandText, boolean isReturnGeneratedKey) {
        return executeScalar(url, commandText, isReturnGeneratedKey, (Object[]) null);
    }

    public static Object executeScalar(String url, String commandText, boolean isGeneratedKey, Object... commandParameters) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url);
            return executeScalar(connection, commandText, isGeneratedKey, commandParameters);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object executeScalar(Connection connection, String commandText) {
        return executeScalar(connection, commandText, (Object[]) null);
    }

    public static Object executeScalar(Connection connection, String commandText, Object... commandParameters) {
        return executeScalar(connection, commandText, false, (Object[]) null);
    }

    public static Object executeScalar(Connection connection, String commandText, boolean isGeneratedKey) {
        return executeScalar(connection, commandText, isGeneratedKey, (Object[]) null);
    }

    public static Object executeScalar(Connection connection, String commandText, boolean isGeneratedKey, Object... commandParameters) {
        try {
            PreparedStatement preStat = getPreparedStatement(connection, commandText, isGeneratedKey, commandParameters);

            ResultSet rs = null;
            if (isGeneratedKey) {
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
        }
    }

    public boolean executeTransaction(String url, SqlExpression sqlExpression) {
        ArrayList<SqlExpression> sqlExpressions = new ArrayList<SqlExpression>(1);
        sqlExpressions.add(sqlExpression);

        return executeTransaction(url, sqlExpressions);
    }

    public boolean executeTransaction(Connection connection, SqlExpression sqlExpression) throws SQLException {
        ArrayList<SqlExpression> sqlExpressions = new ArrayList<SqlExpression>(1);
        sqlExpressions.add(sqlExpression);

        return executeTransaction(connection, sqlExpressions);
    }

    public boolean executeTransaction(String url, List<SqlExpression> sqlExpressions) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url);
            return executeTransaction(connection, sqlExpressions);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean executeTransaction(Connection connection, List<SqlExpression> sqlExpressions) throws SQLException {
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
        }

        return isSuccessfully;
    }

    private static PreparedStatement getPreparedStatement(Connection connection, String commandText,
            Object[] commandParameters) throws SQLException {
        return getPreparedStatement(connection, commandText, false, commandParameters);
    }

    private static PreparedStatement getPreparedStatement(Connection connection, String commandText,
            boolean isGeneratedKey, Object[] commandParameters) throws SQLException {
        PreparedStatement preStat = isGeneratedKey
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

    private static void release(Connection connection) throws RuntimeException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            connection = null;
        }
    }
}
