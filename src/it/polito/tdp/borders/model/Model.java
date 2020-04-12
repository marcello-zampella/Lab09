package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;


public class Model {
	BordersDAO dao;
	private Graph<Country,DefaultEdge> grafo;

	public Model() {
		dao= new BordersDAO();
	}
	
	public  List<Country> getAllCountry() {
		return dao.loadAllCountries();
	}
	
	public ArrayList<Country> generaGrafo(int anno) {
		this.grafo=new SimpleGraph<>(DefaultEdge.class);
		ArrayList<Country> stati= new ArrayList<Country>(dao.countryConConfine(anno));
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

	public ArrayList<Country> esplora(Country stato) {
		
		ArrayList<Country> result = new ArrayList<Country>();
		HashMap backVisit= new HashMap<>();
		GraphIterator<Country,DefaultEdge> it=new BreadthFirstIterator<>(this.grafo, stato); 
		
		it.addTraversalListener(new EdgeTraverseGraphListener(grafo, backVisit));
				
		backVisit.put(stato, null);
		while(it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

}
