package gui.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {
	
	public static void mostrarAlerta(String titulo, String cabecalho, String conteudo, AlertType tipoDeAlerta) {
		Alert alert = new Alert(tipoDeAlerta);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(conteudo);
		alert.show(); 
	}
	
	public static Optional<ButtonType> janelaDeConfirmacao(String titulo, String conteudo){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setContentText(conteudo);
		return alert.showAndWait();
	}

}
