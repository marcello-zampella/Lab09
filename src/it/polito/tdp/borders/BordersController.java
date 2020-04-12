/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.mysql.jdbc.StringUtils;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;
	

    @FXML
    private ComboBox<Country> Box;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		String temp=this.txtAnno.getText();
		if(!StringUtils.isStrictlyNumeric(temp)) {
			this.txtResult.setText("INSERISCI UN ANNO");
			return;
		}
		int anno=Integer.parseInt(temp);
		if(anno<1816 || anno>2016) {
			this.txtResult.setText("Inserisci un numero compreso tra 1816 - 2016");
			return;
		}
		ArrayList<Country> stati=this.model.generaGrafo(anno);
		Collections.sort(stati,new ComparatoreNomiStati());
		this.txtResult.clear();
		for(Country c: stati) {
			this.txtResult.appendText(c.getNomeStato()+" "+c.getGrado()+"\n");
		}
		this.txtResult.appendText("Numero componenti connesse: "+model.getNumComponentiConnesse());
		this.Box.getItems().setAll(stati);
	}
	
    @FXML
    void doSceltaStato(ActionEvent event) {

    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	ArrayList<Country> esplorazione=model.esplora(this.Box.getValue());
    	this.txtResult.clear();
    	for(Country c: esplorazione) {
    		this.txtResult.appendText(c.getNomeStato()+"\n");
    	}
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model model) {
		this.model=model;
		
	}
}
