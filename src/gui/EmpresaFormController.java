package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import dbConnection.DBException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Empresa;
import model.exceptions.ValidationException;
import model.services.EmpresaService;

public class EmpresaFormController implements Initializable {

	private Empresa empresa;
	
	private EmpresaService service;
	
	private List<DataChangeListener> dataChanges = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtCnpj;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtTelefone;
	
	@FXML 
	private TextField txtEndereco;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private Label labelErroCnpj;
	
	@FXML 
	private Label labelErroNome;
	
	@FXML
	private Label labelErroTelefone;
	
	@FXML
	private Label labelErroEndereco;
	
	@FXML
	private Label labelErroEmail;
	
	@FXML
	private Button btnSalvar;
	
	@FXML
	private Button btnCancelar;
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public void setEmpresaService(EmpresaService service) {
		this.service = service;
	}
	
	public void dataChagesListener(DataChangeListener dcl) {
		dataChanges.add(dcl);
	}
	
	@FXML
	public void onBtnSalvarAction(ActionEvent actionEvent) {
		if(empresa == null) {
			throw new IllegalStateException("Objeto nulo");
		}
		if(service == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		try {
			empresa = montarObjEmpresaComFormData();
			service.insertOuUpdateEmpresa(empresa);
			notificarDataChanges();
			Utils.palcoAtual(actionEvent).close();
		}
		catch(ValidationException e) {
			setMensagensDeErro(e.getErrors());
		}
		catch(DBException e) {
			Alerts.mostrarAlerta("Erro ao salvar dados", null, e.getMessage(), AlertType.ERROR);
		}
	}
		
	private void notificarDataChanges() {
		for(DataChangeListener dcl : dataChanges) {
			dcl.onChangesInData();
		}
	}
	
	private Empresa montarObjEmpresaComFormData() {
		Empresa empresa = new Empresa();
		ValidationException exception = new ValidationException("Erro de validação");
		empresa.setId(Utils.parseIntComTryCatch(txtId.getText()));
		if(txtCnpj.getText() == null || txtCnpj.getText().trim().equals("")) {
			exception.addError("cnpj", "O campo não pode ser nulo");
		}
		empresa.setCnpj(txtCnpj.getText());
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "O campo não pode ser nulo");
		}
		empresa.setNome(txtNome.getText());
		if(txtTelefone.getText() == null || txtTelefone.getText().trim().equals("")) {
			exception.addError("telefone", "O campo não pode ser nulo");
		}
		empresa.setTelefone(txtTelefone.getText());
		if(txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			exception.addError("endereco", "O campo não pode ser nulo");
		}
		empresa.setEndereco(txtEndereco.getText());
		if(txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "O campo não pode ser nulo");
		}
		empresa.setEmail(txtEmail.getText());
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return empresa;
	}
	
	@FXML
	public void onBtnCancelarAction(ActionEvent actionEvent) {
		Utils.palcoAtual(actionEvent).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtCnpj, 50);
		Constraints.setTextFieldMaxLength(txtNome, 50);
		Constraints.setTextFieldMaxLength(txtTelefone, 30);
		Constraints.setTextFieldMaxLength(txtEndereco, 100);
		Constraints.setTextFieldMaxLength(txtEmail, 50);	
	}
	
	public void updateFormData() {
		if(empresa == null) {
			throw new IllegalStateException("Objeto empresa nulo");
		}
		txtId.setText(String.valueOf(empresa.getId()));
		txtCnpj.setText(empresa.getCnpj());
		txtNome.setText(empresa.getNome());
		txtTelefone.setText(empresa.getTelefone());
		txtEndereco.setText(empresa.getEndereco());
		txtEmail.setText(empresa.getEmail());
	}

	private void setMensagensDeErro(Map<String, String> errors) {
		Set<String> campos = errors.keySet();
		if(campos.contains("cnpj")) {
			labelErroCnpj.setText("cnpj");
		}
		if(campos.contains("nome")) {
			labelErroNome.setText("nome");
		}
		if(campos.contains("telefone")) {
			labelErroTelefone.setText("telefone");
		}
		if(campos.contains("endereco")) {
			labelErroEndereco.setText("endereco");
		}
		if(campos.contains("email")) {
			labelErroEmail.setText("email");
		}		
	}	
}
