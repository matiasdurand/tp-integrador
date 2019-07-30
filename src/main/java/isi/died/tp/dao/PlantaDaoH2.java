package isi.died.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;

public class PlantaDaoH2 implements PlantaDao {
	
	//Consultas corregidas
	
	private static final String SQL_CREATE = 
			"CREATE TABLE IF NOT EXISTS Stock (ID IDENTITY NOT NULL PRIMARY KEY, CANTIDAD INTEGER, PUNTO_PEDIDO INTEGER, ID_INSUMO INTEGER, ID_PLANTA INTEGER,"
			+ "CONSTRAINT FK_STOCK_INSUMO FOREIGN KEY (ID_INSUMO) REFERENCES Insumo (ID)),"
			+ "CONSTRAINT FK_PLANTA_STOCK FOREIGN KEY (ID_PLANTA) REFERENCES Planta (ID));"
			+ "CREATE TABLE IF NOT EXISTS Planta (ID IDENTITY NOT NULL PRIMARY KEY, NOMBRE VARCHAR(30));";
	
	private static final String SQL_INSERT_PLANTA =
			"INSERT INTO Planta (NOMBRE) VALUES (?)";
	
	private static final String SQL_INSERT_STOCK =
			"INSERT INTO Stock (CANTIDAD, PUNTO_PEDIDO, ID_INSUMO, ID_PLANTA) VALUES (?,?,?,?)";
	
	private static final String SQL_UPDATE_PLANTA =
			"UPDATE Planta SET NOMBRE =? WHERE ID = ?";
	
	private static final String SQL_UPDATE_STOCK =
			"UPDATE Stock SET CANTIDAD =?, PUNTO_PEDIDO =? WHERE ID =?";
	
	private static final String SQL_DELETE =
			"DELETE FROM Planta WHERE ID =?;"
			+ "DELETE FROM Stock WHERE ID_PLANTA =?";
	
	private static final String SQL_SELECT_PLANTA =
			"SELECT ID, NOMBRE FROM Planta";
	
	private static final String SQL_SELECT_STOCK =
			"SELECT ID, CANTIDAD, PUNTO_PEDIDO, ID_INSUMO, ID_PLANTA FORM Stock WHERE= ?";
	
	//
	
	//private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Planta (ID IDENTITY NOT NULL PRIMARY KEY, NOMBRE VARCHAR(30), LISTA_STOCK )";
	//private static final String SQL_INSERT = "INSERT INTO Planta (NOMBRE) VALUES (?)";
	//private static final String SQL_UPDATE = "UPDATE Planta SET NOMBRE =? WHERE ID = ?";
	//private static final String SQL_DELETE = "DELETE FROM Planta WHERE ID =?";
	//private static final String SQL_SELECT = "SELECT ID, NOMBRE FROM Planta";
	
	public PlantaDaoH2() {
		try(Connection conn = DBConnection.get()){
			try(Statement st = conn.createStatement()){
				st.executeUpdate(SQL_CREATE);				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Planta crear(Planta p) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_INSERT_PLANTA,PreparedStatement.RETURN_GENERATED_KEYS)){
				pst.setString(1, p.getNombre());
				pst.executeUpdate();
				try(ResultSet rs = pst.getGeneratedKeys()){
					if(rs.next()) {
						p.setId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return p;
	}

	@Override
	public Planta actualizar(Planta p) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_UPDATE_PLANTA)){
				pst.setString(1, p.getNombre());
				pst.setInt(2, p.getId());
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return p;
	}

	@Override
	public void borrar(Integer id) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_DELETE)){
				pst.setInt(1, id);
				pst.setInt(2, id);
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Planta buscar(Integer id) {
		Planta resultado = null;
		String sqlById = SQL_SELECT_PLANTA + " WHERE ID = ?";
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, id);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						resultado = new Planta();
						resultado.setId(rs.getInt("ID"));
						resultado.setNombre(rs.getString("NOMBRE"));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<Planta> buscarTodas() {
		List<Planta> resultado = new ArrayList<Planta>();
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_SELECT_PLANTA)){
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						Planta aux = new Planta();
						aux.setId(rs.getInt("ID"));
						aux.setNombre(rs.getString("NOMBRE"));
						resultado.add(aux);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;	
	}
	
	public List<Stock> buscarTodosStock(Integer id){
		List<Stock> resultado = new ArrayList<Stock>();
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_SELECT_STOCK)){
				pst.setInt(1,id);
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						Stock aux = new Stock();
						aux.setId(rs.getInt("ID"));
						aux.setCantidad(rs.getInt("CANTIDAD"));
						aux.setPuntoPedido(rs.getInt("PUNTO_PEDIDO"));
						
						resultado.add(aux);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;	
	}
	
	@Override
	public void cargarStock(Integer idPlanta, Integer idInsumo, Integer cantidad, Integer puntoPedido) {
		List<Stock> listaStock = new ArrayList<Stock>
	}
	
}
