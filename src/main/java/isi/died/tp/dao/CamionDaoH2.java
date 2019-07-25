package isi.died.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.DBConnection;
import isi.died.tp.dominio.Camion;

public class CamionDaoH2 implements CamionDao {
	
	private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Camion (ID IDENTITY NOT NULL PRIMARY KEY, MARCA VARCHAR(30), MODELO VARCHAR(30), AÑO INTEGER, DOMINIO VARCHAR(7), CAPACIDAD INTEGER, COSTO_POR_KM DOUBLE, APTO_PARA_LIQUIDOS BOOLEAN)";
	private static final String SQL_INSERT = "INSERT INTO Camion (MARCA,MODELO,AÑO,DOMINIO,CAPACIDAD,COSTO_POR_KM,APTO_PARA_LIQUIDOS) VALUES (?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE Camion SET MARCA =?, MODELO=?, AÑO=?, DOMINIO=?, CAPACIDAD=?, COSTO_POR_KM=?, APTO_PARA_LIQUIDOS=? WHERE ID = ?";
	private static final String SQL_DELETE = "DELETE FROM Camion WHERE ID =?";
	private static final String SQL_SELECT = "SELECT ID, MARCA, MODELO, AÑO, DOMINIO, CAPACIDAD, COSTO_POR_KM, APTO_PARA_LIQUIDOS FROM Camion";
	
	public CamionDaoH2() {
		try(Connection conn = DBConnection.get()){
			try(Statement st = conn.createStatement()){
				st.executeUpdate(SQL_CREATE);				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Camion crear(Camion c) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_INSERT,PreparedStatement.RETURN_GENERATED_KEYS)){
				pst.setString(1, c.getMarca());
				pst.setString(2, c.getModelo());
				pst.setInt(3, c.getAño());
				pst.setString(4, c.getDominio());
				pst.setInt(5, c.getCapacidad());
				pst.setDouble(6, c.getCostoPorKm());
				pst.setBoolean(7, c.getAptoParaLiquidos());
				pst.executeUpdate();
				try(ResultSet rs = pst.getGeneratedKeys()){
					if(rs.next()) {
						c.setId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return c;
	}

	@Override
	public Camion actualizar(Camion c) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)){
				pst.setString(1, c.getMarca());
				pst.setString(2, c.getModelo());
				pst.setInt(3, c.getAño());
				pst.setString(4, c.getDominio());
				pst.setInt(5, c.getCapacidad());
				pst.setDouble(6, c.getCostoPorKm());
				pst.setBoolean(7, c.getAptoParaLiquidos());
				pst.setInt(8, c.getId());
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return c;
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
	public Camion buscar(Integer id) {
		Camion resultado = null;
		String sqlById = SQL_SELECT + " WHERE ID = ?";
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, id);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						resultado = new Camion();
						resultado.setId(rs.getInt("ID"));
						resultado.setMarca(rs.getString("MARCA"));
						resultado.setModelo(rs.getString("MODELO"));
						resultado.setAño(rs.getInt("AÑO"));
						resultado.setDominio(rs.getString("DOMINIO"));
						resultado.setCapacidad(rs.getInt("CAPACIDAD"));
						resultado.setCostoPorKm(rs.getDouble("COSTO_POR_KM"));
						resultado.setAptoParaLiquidos(rs.getBoolean("APTO_PARA_LIQUIDOS"));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<Camion> buscarTodos() {
		List<Camion> resultado = new ArrayList<Camion>();
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_SELECT)){
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						Camion aux = new Camion();
						aux.setId(rs.getInt("ID"));
						aux.setMarca(rs.getString("MARCA"));
						aux.setModelo(rs.getString("MODELO"));
						aux.setAño(rs.getInt("AÑO"));
						aux.setDominio(rs.getString("DOMINIO"));
						aux.setCapacidad(rs.getInt("CAPACIDAD"));
						aux.setCostoPorKm(rs.getDouble("COSTO_POR_KM"));
						aux.setAptoParaLiquidos(rs.getBoolean("APTO_PARA_LIQUIDOS"));
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
