package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Cargo;
import model.entities.Desconto;
import model.entities.Funcionario;
import model.entities.Gasto;
import model.entities.HoraExtra;
import model.entities.Salario;
import model.services.FuncionarioService;

public class AreaFuncionarioController implements Initializable {
	
	private FuncionarioService service;	
	private Funcionario funcionario;
	
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
	private TableView<Cargo> tableViewCargo;
	
	@FXML
	private TableColumn<Cargo, String> tableColumnCargo;
	
	@FXML
	private TableView<Salario> tableViewSalario;
	
	@FXML
	private TableColumn<Salario, Double> tableColumnSalarioBase;
	
	@FXML
	private TableView<Desconto> tableViewDesconto;
	
	@FXML
	private TableColumn<Desconto, String> tableColumnDesconto;
	
	@FXML
	private TableColumn<Desconto, String> tableColumnValorDesconto;
	
	@FXML
	private TableView<HoraExtra> tableViewHoraExtra;
	
	@FXML
	private TableColumn<HoraExtra, Double> tableColumnTempoHoraExtra;
	
	@FXML
	private TableColumn<HoraExtra, Date> tableColumnDataHoraExtra;
	
	@FXML
	private TableColumn<HoraExtra, Double> tableColumnValorBaseHoraExtra;
	
	@FXML
	private TableView<Gasto> tableViewGasto;
	
	@FXML
	private TableColumn<Gasto, String> tableColumnNomeGasto;
	
	@FXML
	private TableView<Gasto> tableViewGastos;
	
	@FXML
	private TableColumn<Gasto, String> tableColumnNomesGastos;
	
	@FXML
	private TextField txtGasto1;
	
	@FXML
	private TextField txtGasto2;
	
	@FXML
	private TextField txtGasto3;
	
	@FXML
	private TextField txtGasto4;
	
	@FXML
	private Label labelValorGastoMensal;
	
	@FXML
	private Button btnCacularGastoMensal;
	
	private ObservableList<Funcionario> observableListFuncionario;	
	private ObservableList<Cargo> observableListCargo;	
	private ObservableList<Salario> observableListSalario;	
	private ObservableList<Desconto> observableListDesconto;
	private ObservableList<HoraExtra> observableListHoraExtra;	
	private ObservableList<Gasto> observableListGasto;
	
	public void setFuncionarioService(FuncionarioService service) {
		this.service = service;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

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
		tableColumnCargo.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnSalarioBase.setCellValueFactory(new PropertyValueFactory<>("valorBase"));
		tableColumnDesconto.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnValorDesconto.setCellValueFactory(new PropertyValueFactory<>("valor"));
		tableColumnTempoHoraExtra.setCellValueFactory(new PropertyValueFactory<>("tempoHoraExtra"));
		tableColumnDataHoraExtra.setCellValueFactory(new PropertyValueFactory<>("data"));
		Utils.formatarDataDaTabela(tableColumnDataHoraExtra, "dd/MM/yyyy");
		tableColumnValorBaseHoraExtra.setCellValueFactory(new PropertyValueFactory<>("valorBase"));
		tableColumnNomeGasto.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnNomesGastos.setCellValueFactory(new PropertyValueFactory<>("nome"));
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		List<Funcionario> list = new ArrayList<>();
		list.add(service.buscarFuncionario(funcionario.getCpf()));
		observableListFuncionario = FXCollections.observableArrayList(list);
		tableViewFuncionario.setItems(observableListFuncionario);
		
		List<Cargo> list2 = new ArrayList<>();
		list2.add(service.buscarCargo(funcionario.getIdCargo()));
		observableListCargo = FXCollections.observableArrayList(list2);
		tableViewCargo.setItems(observableListCargo);
		
		List<Salario> list3 = new ArrayList<>();
		Salario salario = service.buscarSalario(funcionario.getIdCargo());
		list3.add(salario);
		observableListSalario = FXCollections.observableArrayList(list3);
		tableViewSalario.setItems(observableListSalario);
		
		List<Desconto> list4 = service.buscarDescontos(salario.getId());
		observableListDesconto = FXCollections.observableArrayList(list4);
		tableViewDesconto.setItems(observableListDesconto);
		
		List<HoraExtra> list5 = service.buscarHorasExtras(funcionario.getCpf());
		observableListHoraExtra = FXCollections.observableArrayList(list5);
		tableViewHoraExtra.setItems(observableListHoraExtra);
		
		List<Gasto> list6 = service.buscarGastosDoFuncionario(funcionario.getCpf());
		observableListGasto = FXCollections.observableArrayList(list6);
		tableViewGasto.setItems(observableListGasto);
		
		List<Gasto> list7 = service.buscarTodosOsGastos();
		observableListGasto = FXCollections.observableArrayList(list7);
		tableViewGastos.setItems(observableListGasto);
	}
	
	public void onBtnCacularGastoMensalAction() {
		labelValorGastoMensal.setText(String.format("%.2f", cacularGastoMensal()));
	}

	private Double cacularGastoMensal() {
		Double gasto1 = 0.0, gasto2 = 0.0, gasto3 = 0.0, gasto4 = 0.0, soma;
		if(txtGasto1.getText() != null && txtGasto1.getText() != "") {
			gasto1 = Utils.parseDoubleComTryCatch(txtGasto1.getText());
		}
		if(txtGasto2.getText() != null && txtGasto2.getText() != "") {
			gasto2 = Utils.parseDoubleComTryCatch(txtGasto2.getText());
		}
		if(txtGasto3.getText() != null && txtGasto3.getText() != "") {
			gasto3 = Utils.parseDoubleComTryCatch(txtGasto3.getText());
		}
		if(txtGasto4.getText() != null && txtGasto4.getText() != "") {
			gasto4 = Utils.parseDoubleComTryCatch(txtGasto4.getText());
		}
		soma = gasto1 + gasto2 + gasto3 + gasto4;
		return soma;
	}
}
