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
	
	private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Planta (ID IDENTITY NOT NULL PRIMARY KEY, NOMBRE VARCHAR(30))";
	private static final String SQL_INSERT = "INSERT INTO Planta (NOMBRE) VALUES (?)";
	private static final String SQL_UPDATE = "UPDATE Planta SET NOMBRE =? WHERE ID = ?";
	private static final String SQL_DELETE = "DELETE FROM Planta WHERE ID =?";
	private static final String SQL_SELECT = "SELECT ID, NOMBRE FROM Planta";
	
	private StockDao daoStock;
	
	public PlantaDaoH2() {
		daoStock = new StockDaoH2();
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
			try(PreparedStatement pst = conn.prepareStatement(SQL_INSERT,PreparedStatement.RETURN_GENERATED_KEYS)){
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
			try(PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)){
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
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Planta buscar(Integer id) {
		Planta resultado = null;
		String sqlById = SQL_SELECT + " WHERE ID = ?";
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, id);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						resultado = new Planta();
						resultado.setId(rs.getInt("ID"));
						resultado.setNombre(rs.getString("NOMBRE"));
						resultado.setListaStock(daoStock.buscarTodos(resultado.getId()));
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
			try(PreparedStatement pst = conn.prepareStatement(SQL_SELECT)){
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						Planta aux = new Planta();
						aux.setId(rs.getInt("ID"));
						aux.setNombre(rs.getString("NOMBRE"));
						List<Stock> listaStock = daoStock.buscarTodos(rs.getInt("ID"));
						aux.setListaStock(listaStock);
						resultado.add(aux);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;	
	}
	
}
