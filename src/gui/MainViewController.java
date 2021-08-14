package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.FuncionarioDao;
import model.entities.Funcionario;
import model.exceptions.DomainException;
import model.services.EmpresaService;
import model.services.FuncionarioService;

public class MainViewController implements Initializable {

	private Funcionario funcLogado;
	
	@FXML
	private MenuItem menuItemGerente;
	
	@FXML
	private MenuItem menuItemFuncionario;
	
	@FXML
	private MenuItem menuItemAjuda;
	
	@FXML
	private MenuItem menuItemGerenciarEmpresa;
	
	@FXML
	private MenuItem menuItemGerenciarFuncionario;
	
	@FXML
	private MenuItem menuItemAreaFuncionario;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private PasswordField passwordFieldSenha;
	
	@FXML
	private Label labelErroLogin;
	
	@FXML
	private Label labelLogin;
	
	@FXML
	public void onBtnLoginAction(ActionEvent actionEvent) {	
		if(txtCpf.getText().isBlank() == false && passwordFieldSenha.getText().isBlank() == false) {
			funcLogado = validarLogin(txtCpf.getText(), passwordFieldSenha.getText());
			if(funcLogado != null) {
				if(funcLogado.getGerenciamento() == true) {
					menuItemGerente.setDisable(false);
				}
				menuItemFuncionario.setDisable(false);
				menuItemAjuda.setDisable(false);
				labelLogin.setVisible(false);
				txtCpf.setVisible(false);
				passwordFieldSenha.setVisible(false);
				btnLogin.setVisible(false);
				btnCancelar.setVisible(false);
				labelErroLogin.setText("Logado como " + funcLogado.getNome());
			}
			else {
				labelErroLogin.setText("CPF ou Senha incorretos ou inexistentes");
			}
		}
		else {
			labelErroLogin.setText("Preencha os campos CPF e Senha");
		}
	}
	
	public void onBtnCancelarAction(ActionEvent actionEvent) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void onMenuItemGerenciarEmpresaAction() {
		carregarView("/gui/EmpresaList.fxml", (EmpresaListController controller) -> {
			controller.setEmpresaService(new EmpresaService());
			controller.setIdEmpresa(funcLogado.getIdEmpresa());
			controller.updateTableView(); 
		});
	}
	
	@FXML
	public void onMenuItemGerenciarFuncionarioAction() {
		carregarView("/gui/FuncionarioList.fxml", (FuncionarioListController controller) -> {
			controller.setIdEmpresa(funcLogado.getIdEmpresa());
			controller.setFuncionarioService(new FuncionarioService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAreaFuncionarioAction() {
		carregarView("/gui/AreaFuncionario.fxml", (AreaFuncionarioController controller) -> {
			controller.setFuncionarioService(new FuncionarioService());
			controller.setFuncionario(funcLogado);
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemSobreAction() {
		carregarView("/gui/Sobre.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	private synchronized <T> void carregarView(String caminhoDaView, Consumer<T> inicializar) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoDaView));
			VBox vbox = loader.load();
			Scene cenaPrincipal = Main.getMainScene();
			VBox vboxPrincipal = (VBox) ((ScrollPane) cenaPrincipal.getRoot()).getContent();
			Node menuPricipal = vboxPrincipal.getChildren().get(0);
			vboxPrincipal.getChildren().clear();
			vboxPrincipal.getChildren().add(menuPricipal);
			vboxPrincipal.getChildren().addAll(vbox.getChildren());
			T controller = loader.getController();
			inicializar.accept(controller);			
		}
		catch(IOException e) {
			Alerts.mostrarAlerta("Exceção de IO", "Erro ao carregar a janela", e.getMessage(), AlertType.ERROR);
		}
	}

	public MenuItem getMenuItemGerente() {
		return menuItemGerente;
	}

	public void setMenuItemGerente(MenuItem menuItemGerente) {
		this.menuItemGerente = menuItemGerente;
	}
	
	private Funcionario validarLogin(String cpf, String senha) {
    	FuncionarioDao funcionarioDao = DaoFactory.createFuncionarioDao();
    	List<Funcionario> list = new ArrayList<>();
    	Funcionario funcionario = new Funcionario();
    	String senhaSha256 = funcionario.gerarSHA256(senha.trim());    	
    	try {
    		list = funcionarioDao.readAll();
    		for(Funcionario f : list) {
    			if(f.getCpf().equals(cpf.trim()) && f.getSenha().equals(senhaSha256)) {
    	    		return f;
    	    	}
    		}
    		return null;
    	}
    	catch (Exception e) {
    		throw new DomainException("Erro no login: " + e.getMessage());
    	}	
	}
}
