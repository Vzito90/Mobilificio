package com.example.demo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.ResultSetExtractor;
@Component
public class JdbcTemp {
	
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
		this.jdbcTemplateObject= jdbcTemplateObject;
	}
	
	
	public int insertMobile(String nome, String marca, double prezzo, String url) {
		String query = "INSERT INTO mobili (nome, marca, prezzo, url, quantita) VALUES (?, ?, ?, ?)";
		return jdbcTemplateObject.update(query, nome, marca, prezzo, url);
	}
	
	public int updateQuantita(String nome, int quantita) {
		String query = "UPDATE mobili SET quantita = quantita + (?) WHERE nome = (?)";
		return jdbcTemplateObject.update(query, quantita, nome);
	}
	
	public int updatePrezzo(String nome, double prezzo) {
		String query = "UPDATE mobili SET prezzo = (?) WHERE nome=(?)";
		return jdbcTemplateObject.update(query, prezzo, nome);
	}
	

	 public ArrayList<Mobili> getLista() {
	        String query = "SELECT * FROM mobili";

	        return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<Mobili>>() {
	        	@Override
	            public ArrayList<Mobili> extractData(ResultSet rs) throws SQLException, DataAccessException {
	                ArrayList<Mobili> listaM = new ArrayList<>();

	               
	                while (rs.next()) {
	                	Mobili m1 = new Mobili();
	                    m1.setNome(rs.getString("nome"));
	                    m1.setMarca(rs.getString("marca"));
	                    m1.setPrezzo(rs.getDouble("prezzo"));
	                    m1.setUrl(rs.getString("url"));
	                	
	 
	                    listaM.add(m1);
	                }

	                return listaM;
	            }
	        });
	    }
	 
	 public ArrayList<MobiliA> getListaA() {
	        String query = "SELECT * FROM mobili";

	        return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<MobiliA>>() {
	        	@Override
	            public ArrayList<MobiliA> extractData(ResultSet rs) throws SQLException, DataAccessException {
	                ArrayList<MobiliA> listaM = new ArrayList<>();

	               
	                while (rs.next()) {
	                	MobiliA m1 = new MobiliA();
	                    m1.setNome(rs.getString("nome"));
	                    m1.setMarca(rs.getString("marca"));
	                    m1.setPrezzo(rs.getDouble("prezzo"));
	                    m1.setUrl(rs.getString("url"));
	                    m1.setQuantita(rs.getInt("quantita"));	                	
	                    m1.setQuantitaV(rs.getInt("quantitaV"));
	                    listaM.add(m1);
	                }

	                return listaM;
	            }
	        });
	    }
	 
	 public int updateUrl(String nome, String url) {
		 String query = "UPDATE mobili SET url = ? WHERE nome= ?";
		 return jdbcTemplateObject.update(query, url, nome);
	 }
	 
	 public int change(String nome, int quantita) {
	    	String query= "UPDATE mobili SET quantita = quantita - (?) WHERE nome = (?)";
	    	jdbcTemplateObject.update(query, quantita, nome);
	    	String query1 = "UPDATE mobili SET quantitaV = quantitaV + (?) WHERE nome=(?)";
	    	return jdbcTemplateObject.update(query1, quantita, nome);
	    }
	 
	 public int delete(String nome) {
	        String query = "DELETE FROM mobili WHERE nome = ?";
	        return jdbcTemplateObject.update(query, nome);
	    }
	    
}
