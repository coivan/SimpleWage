package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import dbConnection.DBIntegrityException;
import gui.listeners.DataChangeListener;
import gui.utils.Alerts;
import gui.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Funcionario;
import model.services.EmpresaService;
import model.services.FuncionarioService;

public class FuncionarioListController implements Initializable, DataChangeListener {

	private FuncionarioService service;
	private Integer idEmpresa;
	//private Funcionario funcLogado;
	
	@FXML
	private TableView<Funcionario> tableViewFuncionario;
	
	@FXML
	private TableColumn<Funcionario, String> tableColumnCpf;
	
	@FXML
	private TableColumn<Funcionario, String> tableColumnNome;
	
	@FXML
	private TableColumn<Funcionario, Date> tableColumnNascimento;
	
	@FXML
	private TableColumn<Funcionario, String> tableColumnSexo;
	
	@FXML
	private TableColumn<Funcionario, String> tableColumnSenha;
	
	@FXML
	private TableColumn<Funcionario, Integer> tableColumnIdEmpresa;
	
	@FXML
	private TableColumn<Funcionario, Integer> tableColumnIdCargo;
	
	@FXML
	private TableColumn<Funcionario, Boolean> tableColumnAtivo;
	
	@FXML
	private TableColumn<Funcionario, Boolean> tableColumnGerenciamento;
	
	@FXML
	private TableColumn<Funcionario, Funcionario> tableColumnEditar;
	
	@FXML
	private TableColumn<Funcionario, Funcionario> tableColumnRemover;
	
	@FXML
	private Button btnNovoFuncionario;
	
	private ObservableList<Funcionario> observableList;
	
	@FXML
	public void onBtnNovoFuncionarioAction(ActionEvent actionEvent) {
		Stage palcoPai = Utils.palcoAtual(actionEvent);
		Funcionario funcionario = new Funcionario();
		createDialogForm(funcionario, "/gui/FuncionarioForm.fxml", palcoPai);
	}
	
	public void setFuncionarioService(FuncionarioService service) {
		this.service = service;
	}
	
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
//	public void setFuncionarioLogado(Funcionario funcLogado) {
//		this.funcLogado = funcLogado;
//	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializarNodes();	
	}

	private void inicializarNodes() {
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
		Utils.formatarDataDaTabela(tableColumnNascimento, "dd/MM/yyyy");
		tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
		tableColumnSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
		tableColumnIdEmpresa.setCellValueFactory(new PropertyValueFactory<>("idEmpresa"));
		tableColumnIdCargo.setCellValueFactory(new PropertyValueFactory<>("idCargo"));
		tableColumnAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
		tableColumnGerenciamento.setCellValueFactory(new PropertyValueFactory<>("gerenciamento"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewFuncionario.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		List<Funcionario> list = service.buscarFuncionariosDaEmpresa(idEmpresa);
		observableList = FXCollections.observableArrayList(list);
		tableViewFuncionario.setItems(observableList);
		addButtonsEditar();
		addButtonsRemover();
	}

	private void createDialogForm(Funcionario funcionario, String caminhoView, Stage stagePai) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoView));
			Pane pane = loader.load();			
			FuncionarioFormController controller = loader.getController();
			controller.setFuncionario(funcionario);
			controller.setFuncionarioService(new FuncionarioService(), new EmpresaService());
			controller.dataChagesListener(this);
			controller.updateFormData();			
			Stage stage = new Stage();
			stage.setTitle("Dados do funcionario");
			stage.setScene(new Scene(pane));
			stage.setResizable(false);
			stage.initOwner(stagePai);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.showAndWait();			
		}
		catch (IOException e) {
			e.printStackTrace();
			Alerts.mostrarAlerta("Exceção de IO", "Erro ao carregar a janela", e.getMessage(), AlertType.ERROR);
		}		
	}
	
	@Override
	public void onChangesInData() {
		updateTableView();
	}
	
	private void addButtonsEditar(){
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Funcionario, Funcionario>(){
			private final Button button = new Button("Editar");		
			@Override
			protected void updateItem(Funcionario obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/FuncionarioForm.fxml", Utils.palcoAtual(event)));
			}
		});
	}
	
	private void addButtonsRemover() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Funcionario, Funcionario>(){
			private final Button button = new Button("Remover");			
			@Override
			protected void updateItem(Funcionario obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeFuncionario(obj));
			}			
		});				
	}
	
	private void removeFuncionario(Funcionario obj) {
		Optional<ButtonType> result = Alerts.janelaDeConfirmacao("Confirmação", "Deseja realmente deletar a funcionario?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Serviço nulo");
			}
			try {
				service.removeFuncionario(obj);
				updateTableView();
			}
			catch(DBIntegrityException e) {
				Alerts.mostrarAlerta("Erro ao remover funcionario", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}



