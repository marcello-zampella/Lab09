package it.polito.tdp.borders;

import java.util.Comparator;

import it.polito.tdp.borders.model.Country;

public class ComparatoreNomiStati implements Comparator  {
	public int compare (Object o1, Object o2) {
		Country a1=(Country) o1;
		Country a2 =(Country) o2;
		return a1.getNomeStato().compareTo(a2.getNomeStato());
		
	}

}
