package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Country> countryConConfine(int anno) {

		String sql = "SELECT distinct co.CCode , co.StateAbb ,co.StateNme " + 
				"FROM contiguity c, country co " + 
				"WHERE c.conttype=1 AND YEAR<=? AND co.CCode=c.state1no ; ";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public ArrayList<Border> getCountryPairs(int anno) {

		String sql = "SELECT co.CCode AS codiceprimo, co.StateAbb AS abbprimo ,co.StateNme AS nomeprimo,co2.CCode AS codicesecondo,co2.StateAbb AS abbsecondo,co2.StateNme AS nomesecondo " + 
				"FROM contiguity c, country co , country co2 " + 
				"WHERE c.conttype=1 AND YEAR<=? AND co.CCode=c.state1no AND co2.CCode=c.state2no ; ";
		ArrayList<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Border(new Country(rs.getInt("codiceprimo"),rs.getString("abbprimo"),rs.getString("nomeprimo")),new Country(rs.getInt("codicesecondo"),rs.getString("abbsecondo"),rs.getString("nomesecondo"))));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
