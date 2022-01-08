package DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DaoFactory {
    private DaoFactory(){}

    private volatile static DataSource dataSource;

    public static String IP = "192.168.128.200"; // IP сервера белый или серый
    public synchronized static DataSource getDataSource(){
        if (dataSource == null){
            try {
                HikariConfig config = new HikariConfig();//localhost:3050/D:\FireBirdTest\BD_REST 192.168.128.200:3050/C:\MKR_Base\BD  ?authPlugins=Legacy_Aut
                config.setJdbcUrl("jdbc:firebirdsql://localhost:3050/C:\\MKR_Base\\BD?charSet=windows-1251");//?charSet=utf-8 ?charSet=windows-1251
                config.setUsername("DIP");
                config.setPassword("Dip1098712857");

                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

                dataSource = new HikariDataSource(config);
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }

        }
        return dataSource;
    }
}
