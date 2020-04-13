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
	/**
	 * Genera un grafo Di tutti gli stati e i relativi confini dall'anno selezionato fino al 1816
	 * @param anno
	 * @return ArrayList dei @Country da quell'anno fino al 1816
	 */
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
		ConnectivityInspector conn= new ConnectivityInspector (grafo); //crea una lista di set, ogni set e' una componente connessa
		return conn.connectedSets().size();
	}

	public ArrayList<Country> esplora(Country stato) {
		ArrayList<Country> visitati=new ArrayList<Country>();
		espandi(stato,visitati);
		return visitati;
	}

	private void espandi(Country stato, ArrayList<Country> visitati) {
		visitati.add(stato); //lo stato in cui sono entra nei visitati
		Iterator<DefaultEdge> a=grafo.edgesOf(stato).iterator();
		while(a.hasNext()) { //vedo uno per volta tutti i confinanti, visita in profondita'
			Country statotemp;
			DefaultEdge e=a.next();
			if(!grafo.getEdgeSource(e).equals(stato))
				statotemp=grafo.getEdgeSource(e);
			else
				statotemp=grafo.getEdgeTarget(e);
			if(!visitati.contains(statotemp)) { //lo salvo come statotemp perche' stato deve essere sempre l'oggetto su cui mi trovo
				//altrimenti quando poi faccio return sullo stesso livello, mi ritrovo su uno stato diverso
			
				espandi(statotemp,visitati);
			}
		}
		return;
		
		
	}

}
