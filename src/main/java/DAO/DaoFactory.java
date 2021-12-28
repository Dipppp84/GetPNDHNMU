package DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DaoFactory {
    private DaoFactory(){}

    private volatile static DataSource dataSource;

    public synchronized static DataSource getDataSource(){
        if (dataSource == null){
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:firebirdsql://localhost:3050/D:\\FireBirdTest\\BD_REST?charSet=utf-8");
            config.setUsername("SYSDBA");
            config.setPassword("masterkey");

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);

        }
        return dataSource;
    }
}
