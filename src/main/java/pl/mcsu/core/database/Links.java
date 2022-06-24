package pl.mcsu.core.database;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Links extends Settings {

    private Connection connection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private void close(java.sql.Connection connection) throws SQLException {
        connection.close();
    }

    public void execute(String sql, Object[] objects) throws SQLException {
        java.sql.Connection connection = connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = 1;
        for (Object object : objects) {
            preparedStatement.setObject(i, object);
            i++;
        }
        preparedStatement.executeUpdate();
        close(connection);
    }

    public CachedRowSet select(String sql, Object[] objects) throws SQLException {
        java.sql.Connection connection = connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = 1;
        for (Object object : objects) {
            preparedStatement.setObject(i, object);
            i++;
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        RowSetFactory rowSetFactory = RowSetProvider.newFactory();
        CachedRowSet cachedRowSet = rowSetFactory.createCachedRowSet();
        cachedRowSet.populate(resultSet);
        close(connection);
        return cachedRowSet;
    }

}
