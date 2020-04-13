package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		ArrayList<Country> visitati=new ArrayList<Country>();
		espandi(stato,visitati);
		return visitati;
	}

	private void espandi(Country stato, ArrayList<Country> visitati) {
		//for(DefaultEdge e:grafo.edgesOf(stato)) {
		visitati.add(stato);
		Iterator<DefaultEdge> a=grafo.edgesOf(stato).iterator();
		while(a.hasNext()) {
			Country statotemp;
			DefaultEdge e=a.next();
			if(!grafo.getEdgeSource(e).equals(stato))
				statotemp=grafo.getEdgeSource(e);
			else
				statotemp=grafo.getEdgeTarget(e);
			if(!visitati.contains(statotemp)) {
			
				espandi(statotemp,visitati);
			}
		}
		return;
		
		
	}

}
