package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import model.entities.Empresa;
import model.services.EmpresaService;

public class EmpresaListController implements Initializable, DataChangeListener {
	
	private EmpresaService service;
	
	private Integer idEmpresa;
	
	@FXML
	private TableView<Empresa> tableViewEmpresa;
	
	@FXML
	private TableColumn<Empresa, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Empresa, String> tableColumnCnpj;
	
	@FXML
	private TableColumn<Empresa, String> tableColumnNome;
	
	@FXML 
	private TableColumn<Empresa, String> tableColumnTelefone;
	
	@FXML
	private TableColumn<Empresa, String> tableColumnEndereco;
	
	@FXML
	private TableColumn<Empresa, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Empresa, Empresa> tableColumnEditar;
	
	@FXML
	private TableColumn<Empresa, Empresa> tableColumnRemover;
	
	@FXML
	private Button btnAdicionarEmpresa;
	
	private ObservableList<Empresa> observableList;
	
	@FXML
	public void onBtnAdicionarEmpresaAction(ActionEvent actionEvent) {
		Stage stagePai = Utils.palcoAtual(actionEvent);
		Empresa empresa = new Empresa();
		createDialogForm(empresa, "/gui/EmpresaForm.fxml", stagePai);
	}
	
	public void setEmpresaService(EmpresaService service) {
		this.service = service;
	}
	
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();		
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewEmpresa.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		Empresa empresa = service.buscaEmpresa(idEmpresa); 
		List<Empresa> list = new ArrayList<>();
		list.add(empresa);
		observableList = FXCollections.observableArrayList(list);
		tableViewEmpresa.setItems(observableList);
		addButtonsEditar();
		addButtonsRemover();
	}

	private void createDialogForm(Empresa empresa, String caminhoView, Stage stagePai) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoView));
			Pane pane = loader.load();			
			EmpresaFormController controller = loader.getController();
			controller.setEmpresa(empresa);
			controller.setEmpresaService(new EmpresaService());
			controller.dataChagesListener(this);
			controller.updateFormData();			
			Stage stage = new Stage();
			stage.setTitle("Dados da empresa");
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
		tableColumnEditar.setCellFactory(param -> new TableCell<Empresa, Empresa>(){
			private final Button button = new Button("Editar");		
			@Override
			protected void updateItem(Empresa obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/EmpresaForm.fxml", Utils.palcoAtual(event)));
			}
		});
	}
	
	private void addButtonsRemover() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Empresa, Empresa>(){
			private final Button button = new Button("Remover");			
			@Override
			protected void updateItem(Empresa obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEmpresa(obj));
			}			
		});				
	}
	
	private void removeEmpresa(Empresa obj) {
		Optional<ButtonType> result = Alerts.janelaDeConfirmacao("Confirmação", "Deseja realmente deletar a empresa?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Serviço nulo");
			}
			try {
				service.removeEmpresa(obj);
				updateTableView();
			}
			catch(DBIntegrityException e) {
				Alerts.mostrarAlerta("Erro ao remover empresa", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}
