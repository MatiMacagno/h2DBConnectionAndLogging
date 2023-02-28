import org.apache.log4j.Logger;

import java.sql.*;

public class Test {

    private final static Logger logger = Logger.getLogger(Test.class);

    private static final String SQL_CREATE_TABLE = "DROP TABLE IF EXISTS EMPLEADO; CREATE TABLE EMPLEADO " +
            "("
            + " ID INT PRIMARY KEY,"
            + " NOMBRE varchar(100) NOT NULL, "
            + " TIPO varchar(100) NOT NULL, "
            + " EDAD int NOT NULL, "
            + " SEXO varchar(100) NOT NULL "
            + " )";

    private static final String SQL_INSERT =  "INSERT INTO EMPLEADO (ID, NOMBRE, TIPO, EDAD, SEXO) VALUES (1, 'Pancho', 'Contratado', 32, 'MASCULINO')";
    private static final String SQL_INSERT2 =  "INSERT INTO EMPLEADO(ID, NOMBRE, TIPO, EDAD, SEXO) VALUES (2, 'Pillo', 'Contratado', 19, 'MASCULINO' )";
    private static final String SQL_INSERT3 =  "INSERT INTO EMPLEADO (ID, NOMBRE, TIPO, EDAD, SEXO) VALUES (3, 'Cepillo', 'Pasante', 23, 'FEMENINO')";

    // Si descomentamos linea 23 y 57 se ejecuta la excepcion por ID igual al de otro empleado.
    // private static final String SQL_INSERT4 =  "INSERT INTO EMPLEADO (ID, NOMBRE, TIPO, EDAD, SEXO) VALUES (1, 'JULIETA', 'Pasante', 23, 'FEMENINO')";

    private static final String SQL_UPDATE =  "UPDATE EMPLEADO SET NOMBRE='MATIAS' WHERE ID=1 ";

    private static final String SQL_DELETE = "DELETE FROM EMPLEADO WHERE ID=3";

    public static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver").newInstance();
        return DriverManager.getConnection("jdbc:h2:~/test3", "sa","");

    }

    public static void verEmpleados(Connection connection) throws SQLException {
        String sql = "SELECT * FROM EMPLEADO";
        Statement sqlSmt = connection.createStatement();
        ResultSet rs = sqlSmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getInt(1) + rs.getString(2) + rs.getString(3) + rs.getInt(4) + rs.getString(5));
        }
    }

    public static void main(String[] args) throws Exception {

        Connection connection = null;

        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_CREATE_TABLE);
            statement.execute(SQL_INSERT);
            statement.execute(SQL_INSERT2);
            statement.execute(SQL_INSERT3);

            // Si descomentamos linea 23 y 57 se ejecuta la excepcion por ID igual al de otro empleado.
            //statement.execute(SQL_INSERT4);
            verEmpleados(connection);

            statement.execute(SQL_UPDATE);
            logger.debug(SQL_UPDATE);
            verEmpleados(connection);

            statement.execute(SQL_DELETE);
            logger.info(SQL_DELETE);
            verEmpleados(connection);


        } catch (Exception e){
            logger.error("Ha sucedido un error: ", e);
        } finally {
            connection.close();
        }
    }

}

