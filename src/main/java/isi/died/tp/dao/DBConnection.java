package isi.died.tp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
	/**
	 * En caso de habilitar el almacenamiento en archivos
	 * http://www.h2database.com/html/features.html#database_url 
	 */
	// private static final String url ="jdbc:h2:~/ejemplo;AUTO_SERVER=TRUE;FILE_LOCK=SOCKET";
	
	/**
	 * Necesidad del uso del parametro DB_CLOSE_DELAY
	 * http://www.h2database.com/html/features.html#in_memory_databases
	 */
    private static final String url ="jdbc:h2:mem:ejemplo;DB_CLOSE_DELAY=-1";

    private static final String user="died";
    private static final String pass="died";
    
    
    private DBConnection(){
        // no se pueden crear instancias de esta clase
    }
    
    public static Connection get(){
        Connection conn=null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
