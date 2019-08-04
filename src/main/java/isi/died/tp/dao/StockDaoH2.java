package isi.died.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dominio.Stock;

public class StockDaoH2 implements StockDao{
	
	private static final String SQL_CREATE = 
			"CREATE TABLE IF NOT EXISTS Stock (ID IDENTITY NOT NULL PRIMARY KEY, CANTIDAD INTEGER, PUNTO_PEDIDO INTEGER, ID_INSUMO INTEGER, ID_PLANTA INTEGER)";
	
	private static final String SQL_INSERT =
			"INSERT INTO Stock (CANTIDAD, PUNTO_PEDIDO, ID_INSUMO, ID_PLANTA) VALUES (?,?,?,?)";
	
	private static final String SQL_UPDATE =
			"UPDATE Stock SET CANTIDAD =?, PUNTO_PEDIDO =? WHERE ID_INSUMO =? AND ID_PLANTA =?";
		
	private static final String SQL_SELECT =
			"SELECT ID, CANTIDAD, PUNTO_PEDIDO, ID_INSUMO FROM Stock";
		
	private static final String SQL_DELETE = "DELETE FROM Stock";
	
	private InsumoDao daoInsumo;
	
	public StockDaoH2() {
		daoInsumo = new InsumoDaoH2();
		try(Connection conn = DBConnection.get()){
			try(Statement st = conn.createStatement()){
				st.executeUpdate(SQL_CREATE);				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
		
	public Stock crear(Integer idPlanta, Stock s) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_INSERT,PreparedStatement.RETURN_GENERATED_KEYS)){
				pst.setInt(1, s.getCantidad());
				pst.setInt(2, s.getPuntoPedido());
				pst.setInt(3, s.getInsumo().getId());
				pst.setInt(4, idPlanta);
				pst.executeUpdate();
				try(ResultSet rs = pst.getGeneratedKeys()){
					if(rs.next()) {
						s.setId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	public Stock actualizar(Integer idPlanta, Stock s) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)){
				pst.setInt(1, s.getCantidad());
				pst.setInt(2, s.getPuntoPedido());
				pst.setInt(3, s.getInsumo().getId());
				pst.setInt(4, idPlanta);
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	public Stock buscar(Integer idPlanta, Integer idInsumo) {
		Stock resultado = null;
		String sqlById = SQL_SELECT + " WHERE ID_PLANTA = ? AND ID_INSUMO =?";
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, idPlanta);
				pst.setInt(2, idInsumo);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						resultado = new Stock();
						resultado.setId(rs.getInt("ID"));
						resultado.setCantidad(rs.getInt("CANTIDAD"));
						resultado.setPuntoPedido(rs.getInt("PUNTO_PEDIDO"));
						resultado.setInsumo(daoInsumo.buscar(idInsumo));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;
	}
	
	public List<Stock> buscarTodos(Integer idPlanta){
		List<Stock> resultado = new ArrayList<Stock>();
		String sqlById = SQL_SELECT + " WHERE ID_PLANTA = ?";
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, idPlanta);
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						Stock aux = new Stock();
						aux.setId(rs.getInt("ID"));
						aux.setCantidad(rs.getInt("CANTIDAD"));
						aux.setPuntoPedido(rs.getInt("PUNTO_PEDIDO"));
						aux.setInsumo(daoInsumo.buscar(rs.getInt("ID_INSUMO")));
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
	public void borrar(Integer idPlanta, Integer idInsumo) {
		try(Connection conn = DBConnection.get()){
			String sqlById;
			Integer idAuxiliar;
			if(idPlanta.equals(-1)) {
				sqlById = SQL_DELETE + " WHERE ID_INSUMO = ?";
				idAuxiliar = idInsumo;
			}
			else {
				sqlById = SQL_DELETE + " WHERE ID_PLANTA = ?";
				idAuxiliar = idPlanta;
			}
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, idAuxiliar);
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
