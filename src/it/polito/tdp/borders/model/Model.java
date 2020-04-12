package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	BordersDAO dao;
	private Graph<Country,DefaultEdge> grafo;

	public Model() {
		dao= new BordersDAO();
	}
	
	public ArrayList<Country> generaGrafo(int anno) {
		this.grafo=new SimpleGraph<>(DefaultEdge.class);
		ArrayList<Country> stati= new ArrayList<Country>(dao.loadAllCountries());
		Graphs.addAllVertices(grafo, stati);
		for(Border b: dao.getCountryPairs(anno)) {
			grafo.addEdge(b.getPrimo(), b.getSecondo());
		}
		for(Country c: this.grafo.vertexSet()) {
			c.setGrado(grafo.degreeOf(c));
		}
		return stati;
	}

	public int getNumComponentiConnesse() {
		ConnectivityInspector conn= new ConnectivityInspector (grafo);
		return conn.connectedSets().size();
	}

}
