package it.polito.tdp.borders.model;

public class Border {
	private Country primo;
	private Country secondo;
	public Border(Country primo, Country secondo) {
		super();
		this.primo = primo;
		this.secondo = secondo;
	}
	public Country getPrimo() {
		return primo;
	}
	public Country getSecondo() {
		return secondo;
	}
	
	
}
