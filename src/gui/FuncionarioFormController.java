package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import dbConnection.DBException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Constraints;
import gui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Funcionario;
import model.exceptions.ValidationException;
import model.services.EmpresaService;
import model.services.FuncionarioService;

public class FuncionarioFormController implements Initializable {
	
	private Funcionario funcionario;	
	private FuncionarioService service;	
	private EmpresaService empresaService;
	
	private List<DataChangeListener> dataChanges = new ArrayList<>();
	
	private ObservableList<String> obsListOpcoes = FXCollections.observableArrayList();
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private DatePicker datePickerNascimento;
	
	@FXML
	private TextField txtSexo;
	
	@FXML 
	private TextField txtSenha;
	
	@FXML
	private ComboBox<Integer> comboBoxIdEmpresa;
	
	@FXML
	private ComboBox<Integer> comboBoxIdCargo;
	
	@FXML
	private ChoiceBox<String> choiceBoxAtivo;
	
	@FXML
	private ChoiceBox<String> choiceBoxGerenciamento;
	
	@FXML
	private Label labelErroCpf;
	
	@FXML 
	private Label labelErroNome;
	
	@FXML
	private Label labelErroNascimento;
	
	@FXML
	private Label labelErroSexo;
	
	@FXML
	private Label labelErroSenha;
	
	@FXML
	private Label labelErroIdEmpresa;
	
	@FXML
	private Label labelErroIdCargo;
	
	@FXML
	private Label labelErroAtivo;
	
	@FXML
	private Label labelErroGerenciamento;
	
	@FXML
	private Button btnSalvar;
	
	@FXML
	private Button btnCancelar;
	
	//private ObservableList<Funcionario> observableList;
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public void setFuncionarioService(FuncionarioService service, EmpresaService empresaService) {
		this.service = service;
		this.empresaService = empresaService;
	}
	
	public void dataChagesListener(DataChangeListener dcl) {
		dataChanges.add(dcl);
	}
	
	@FXML
	public void onBtnSalvarAction(ActionEvent actionEvent) {
		if(funcionario == null) {
			throw new IllegalStateException("Objeto nulo");
		}
		if(service == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		try {
			funcionario = montarObjFuncionario();
			service.insertOuUpdateFuncionario(funcionario);
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
	
	private Funcionario montarObjFuncionario() {
		Funcionario funcionario = new Funcionario();
		ValidationException exception = new ValidationException("Erro de validação");
		
		if(txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "O campo não pode ser nulo");
		}
		funcionario.setCpf(txtCpf.getText());
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "O campo não pode ser nulo");
		}
		funcionario.setNome(txtNome.getText());
		
		if(datePickerNascimento == null) {
			exception.addError("nascimento", "O campo não pode ser nulo");
		}
		else {
			Instant instant = Instant.from(datePickerNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
			funcionario.setNascimento(Date.from(instant));
		}
		
		if(txtSexo.getText() == null || txtSexo.getText().trim().equals("")) {
			exception.addError("sexo", "O campo não pode ser nulo");
		}
		funcionario.setSexo(txtSexo.getText());
		
		if(txtSenha.getText() == null || txtSenha.getText().trim().equals("")) {
			exception.addError("senha", "O campo não pode ser nulo");
		}
		funcionario.setSenha(txtSenha.getText());
		
		funcionario.setIdEmpresa(Integer.parseInt(comboBoxIdEmpresa.getValue().toString()));
		
		if(comboBoxIdCargo.getValue() == null) {
			exception.addError("idCargo", "O campo não pode ser nulo");
		}
		funcionario.setIdCargo(comboBoxIdCargo.getValue());
		
		if(choiceBoxAtivo.getValue() == null || choiceBoxGerenciamento.getValue().trim().equals("")) {
			exception.addError("ativo", "O campo não pode ser nulo");
		}
		else {
			if(choiceBoxAtivo.getValue().equals("Sim")) {
				funcionario.setAtivo(true);
			}
			else {
				funcionario.setAtivo(false);
			}
		}
				
		if(choiceBoxGerenciamento.getValue() == null || choiceBoxGerenciamento.getValue().trim().equals("")) {
			exception.addError("gerenciamento", "O campo não pode ser nulo");
		}
		else {
			if(choiceBoxGerenciamento.getValue().equals("Sim")) {
				funcionario.setGerenciamento(true);
			}
			else {
				funcionario.setGerenciamento(false);
			}
		}
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return funcionario;
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
		Constraints.setTextFieldMaxLength(txtCpf, 20);
		Constraints.setTextFieldMaxLength(txtNome, 45);
		Utils.formatarDatePicker(datePickerNascimento, "dd/MM/yyyy");
		Constraints.setTextFieldMaxLength(txtSexo, 1);
		Constraints.setTextFieldMaxLength(txtSenha, 70);
		initializeComboBoxIdEmpresa();
		carregarOpcoes();
	}
	
	public void updateFormData() {
		if(funcionario == null) {
			throw new IllegalStateException("Objeto funcionario nulo");
		}
		txtCpf.setText(funcionario.getCpf());
		txtNome.setText(funcionario.getNome());
		if(funcionario.getNascimento() != null) {
			datePickerNascimento.setValue(LocalDate.ofInstant(funcionario.getNascimento().toInstant(), ZoneId.systemDefault()));
		}
		txtSexo.setText(funcionario.getSexo());
		txtSenha.setText(funcionario.getSenha());
		if(funcionario.getIdEmpresa() == null) {
			comboBoxIdEmpresa.getSelectionModel().selectFirst();
		}
		else {
			comboBoxIdEmpresa.setValue(funcionario.getIdEmpresa());
		}
		
		comboBoxIdCargo.setValue(funcionario.getIdCargo());
	}

//	public void loadAssociatedObjects() {
//		if (service == null) {
//			throw new IllegalStateException("Objeto empresa nulo");
//		}
//		List<Funcionario> list = service.buscarFuncionariosDaEmpresa(1);
//		observableList = FXCollections.observableArrayList(list);
//		//comboBoxIdEmpresa.setItems(observableList);
//	}
	
	private void setMensagensDeErro(Map<String, String> errors) {
		Set<String> campos = errors.keySet();
		if(campos.contains("cpf")) {
			labelErroCpf.setText("cpf");
		}
		if(campos.contains("nome")) {
			labelErroNome.setText("nome");
		}
		if(campos.contains("nascimento")) {
			labelErroNascimento.setText("nascimento");
		}
		if(campos.contains("sexo")) {
			labelErroSexo.setText("sexo");
		}
		if(campos.contains("senha")) {
			labelErroSenha.setText("senha");
		}
		if(campos.contains("idEmpresa")) {
			labelErroIdEmpresa.setText("idEmpresa");
		}
		if(campos.contains("idCargo")) {
			labelErroIdCargo.setText("idCargo");
		}
		if(campos.contains("ativo")) {
			labelErroAtivo.setText("ativo");
		}
		if(campos.contains("gerenciamento")) {
			labelErroGerenciamento.setText("gerenciamento");
		}
	}
	
	private void initializeComboBoxIdEmpresa() {
		comboBoxIdEmpresa.setValue(1);
	}
	
	private void carregarOpcoes() {
		obsListOpcoes.removeAll(obsListOpcoes);
		String opcaoSim = "Sim";
		String opcaoNao = "Não";
		obsListOpcoes.addAll(opcaoSim, opcaoNao);
		choiceBoxAtivo.getItems().addAll(obsListOpcoes);
		choiceBoxGerenciamento.getItems().addAll(obsListOpcoes);
	}
}
