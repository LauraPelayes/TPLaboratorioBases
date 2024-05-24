import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class H2jdbcCreateDemo {
    // driver JDBC y URL de la BD
    static final String JDBC_DRIVER = "org.h2.Driver";
    //static final String DB_URL = "jdbc:h2:~/testdb";
    //static final String DB_URL = "jdbc:h2:~/h2/testdb";
    //ESTO ES PARA CREAR LA BASE EN MEMORIA, SE BORRA CUANDO SE TERMINA DE EJECUTAR EL PROGRAMA

    //static final String DB_URL = "jdbc:h2:mem:test";
    //static final String DB_URL = "jdbc:h2:tcp://localhost/~/h2/test";

    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    //  Credenciales
    static final String USER = "sa";
    static final String PASS = "";

    static Connection conn = null;
    static Statement stmt = null;

    public static void crearTabla(){
        //Paso 3: Ejecutar una consulta
        try {
            System.out.println("Creando tabla en la base de datos...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE ALUMNOS1 " +
                    "(id INTEGER not NULL, " +
                    " nombre VARCHAR(255), " +
                    " apellido VARCHAR(255), " +
                    " edad INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            System.out.println("Se creó la tabla...");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void guardarDatos(){
        //Paso 3: Ejecutar una consulta
        try {
            System.out.println("Insertando datos en la base de datos...");
            stmt = conn.createStatement();
            String sql = "insert into ALUMNOS1 " +
                    "(id, nombre, apellido, edad) values (1,'Pepito','Honguito',20)";
            stmt.executeUpdate(sql);
            System.out.println("Se insertaron los datos...");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void borrarDatos(){
        //Paso 3: Ejecutar una consulta
        try {
            System.out.println("Borrando datos en la base de datos...");
            stmt = conn.createStatement();
            String sql = "delete from ALUMNOS1 (id, nombre, apellido, edad) where id=1";
            stmt.executeUpdate(sql);
            System.out.println("Se borraron los datos...");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void actualizarDatos(){
        //Paso 3: Ejecutar una consulta
        try {
            System.out.println("Actualizando datos en la base de datos...");
            stmt = conn.createStatement();
            String sql = "update ALUMNOS1 set (id = 5, nombre = 'Marcos', apellido = 'Perez', edad = 25) where id=1";
            stmt.executeUpdate(sql);
            System.out.println("Se actualizaron los datos...");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void leerDatos(){
        //Paso 3: Ejecutar una consulta
        try {
            ResultSet rs;
            System.out.println("Leyendo datos desde la base de datos...");
            stmt = conn.createStatement();
            String sql = "select * from ALUMNOS1";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {               // Position the cursor                 3
                System.out.println("Dato leido: " + rs.getString(2));
            }
            System.out.println("Se leyeron los datos...");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {

        try {
            // Paso 1: Registrar el driver jdbc
            Class.forName(JDBC_DRIVER);

            //Paso 2: Abrir una conexión
            System.out.println("Conectándose a la base de datos...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //Paso 3: Trabajar con la tabla
            // crearTabla();
            // guardarDatos();
            // leerDatos();

            //menú
            System.out.println("MENU DE OPCIONES:"+
                    "1. Crear una tabla.\n" +
                    "2. Insertar registros\n" +
                    "3. Mostrar registros insertados\n" +
                    "4. Borrar registros\n" +
                    "5. Actualizar registros\n"
                    );
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            switch(opcion){
                case 1:
                    crearTabla();
                    break;
                case 2:
                    guardarDatos();
                    break;
                case 3:
                    leerDatos();
                    break;
                case 4:
                    borrarDatos();
                    break;
                case 5:
                    actualizarDatos();
                    break;

            }

            // Paso 4: limpiar el ambiente
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //administrar errores para JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //administrar errores para Class.forName
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}