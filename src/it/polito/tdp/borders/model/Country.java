package it.polito.tdp.borders.model;

public class Country {

	private int codice;
	private String abbStato;
	private String nomeStato;
	private int grado;

	public Country(int int1, String string, String string2) {
		this.codice=int1;
		this.abbStato=string;
		this.nomeStato=string2;
		this.grado=0;
	}

	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}

	@Override
	public String toString() {
		return nomeStato+ " "+this.grado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codice;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (codice != other.codice)
			return false;
		return true;
	}
	
	

}
