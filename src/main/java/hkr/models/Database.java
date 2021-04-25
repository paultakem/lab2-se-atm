package hkr.models;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Database {

    private final static String table = "`app_db`.`user`";

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    static {
        createTable();
    }

    public void addAccount(String email, String password) {
        execute(Type.UPDATE,
                "INSERT INTO " + table + " (email, password) " +
                        "VALUES (?, ?);",
                new Object[] { email, password },
                new int[] { Types.VARCHAR, Types.VARCHAR }
        );
    }

    public void updateBalance(int id, double value) {
        execute(Type.UPDATE, "UPDATE " + table + " SET balance = ? WHERE id = ? ORDER BY id DESC LIMIT 1;",
                new Object[] { value, id },
                new int[] { Types.DECIMAL, Types.VARCHAR }
        );
    }

    public User getUser(int id){
        Map<String, ArrayList<Object>> result = (Map<String, ArrayList<Object>>) execute(Type.READER,
                "SELECT * FROM " + table + " WHERE id = ?;",
                new Object[] { id },
                new int[] { Types.INTEGER }
        );
        return new User(
                (int)result.get("id").get(0),
                (String)result.get("email").get(0),
                (BigDecimal)result.get("balance").get(0)
        );
    }

    public int checkCredentials(String email, String password) {
        int id = -1;

        Map<String, ArrayList<Object>> users = (Map<String, ArrayList<Object>>) execute(Type.READER,
                "SELECT id FROM " + table + " WHERE email = ? AND password = ?;",
                new Object[] { email, password },
                new int[] { Types.VARCHAR, Types.VARCHAR }
        );
        ArrayList<Object> entries = users.get("id");
        if(entries.size() != 0)
            id = (int) entries.get(0);

        return id;
    }

    private static Object getObject(ResultSet set, int col, int type) throws SQLException {
        switch (type){
            case -7 :
            case -6 :
            case 5 :
            case 4 :
                return set.getInt(col);
            case -1 :
            case 1 :
            case 12 :
                return set.getString(col);
            case 91 :
                return set.getDate(col);
            case 2004 :
            case -2 :
            case -3 :
            case -4 :
                return set.getBlob(col);
            case 0:
                return null;
            default:
                return set.getObject(col);
        }
    }

    public static void createTable()
    {
        try {
            execute(Type.ONEWAY,
                    "CREATE TABLE IF NOT EXISTS " + table + " ("+
                            "`id` INT NOT NULL AUTO_INCREMENT," +
                            "`email` VARCHAR(150) NOT NULL," +
                            "`password` VARCHAR(150) NOT NULL," +
                            "`balance` DECIMAL(10,2) NOT NULL DEFAULT 0," +
                            "PRIMARY KEY (`id`));"
            );
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static Object execute(Type type, String query)
    {
        return execute(type, query, null, null);
    }

    public static Object execute(Type type, String query, Object[] parameters, int commonType){//java.sql.Types
        int[] types = new int[parameters.length];
        Arrays.fill(types, commonType);

        return execute(type, query, parameters, types);
    }

    public static Object execute(Type type, String query, Object[] parameters, int[] types)//java.sql.Types
    {
        if(parameters != null && parameters.length != types.length)
            return null;
        Object result = null;
        try(Connection connection = getConnection();
            PreparedStatement command = connection.prepareStatement(query)
        )
        {
            if(parameters != null)
                for(int i = 0; i < parameters.length; i++)
                    command.setObject(i + 1, parameters[i], types[i]);

            if(type == Type.READER){
                ResultSet set = command.executeQuery();
                ResultSetMetaData metaData = set.getMetaData();
                Map<String, ArrayList<Object>> results = new HashMap<>();
                int count = metaData.getColumnCount();
                for(int i = 1; i < count + 1; i++)
                    results.put(metaData.getColumnName(i), new ArrayList<>());
                while (set.next())
                    for(int i = 1; i < count + 1; i++)
                        results.get(metaData.getColumnName(i)).add(getObject(set, i, metaData.getColumnType(i)));

                result = results;
            }
            else if(type == Type.UPDATE){
                result = command.executeUpdate();
            }
            else
                command.execute();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    private static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/?user=javafx-app/app_db?&allowMultiQueries=true&serverTimezone=Europe/Stockholm&autoReconnect=true&useSSL=true"
                    , "javafx-app",
                    "123qwerty123"
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
    }

    public enum Type {
        UPDATE,
        READER,
        ONEWAY
    }
}
