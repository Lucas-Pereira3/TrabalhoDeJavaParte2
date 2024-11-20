package pastajavafx.Controller.java;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pastajavafx.model.Cliente;
import pastajavafx.model.dao.ClienteDAOImpl;

public class FXMLTelaController implements Initializable {

    @FXML
    private Button adicionar;

    @FXML
    private Button excluir;

    @FXML
    private Button cancelar;
    
    @FXML
    private Button proximo;

    @FXML
    private Button salvar;

    @FXML
    private TextField produto;     
    @FXML
    private TextField cod;
    @FXML
    private TextField valorUnt;
    @FXML
    private TextField quantidade;
    @FXML
    private TextField estoque;
    @FXML
    private TextField fornecedor;
    @FXML
    private TextField descricao;

    private String estadoFormulario = "inicial";
    
    private Stage stage;
    
    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl(); // Correção aqui: inicializando o DAO
    private Cliente clienteAtual; // Cliente sendo editado ou adicionado

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarBotoes();
    }

    @FXML
    private void handleAdicionar() {
        estadoFormulario = "editando";
        clienteAtual = new Cliente("", "", "", "", "", "", "", "", "", 0); 
        atualizarBotoes();
        limparCampos();  
    }
    
@FXML
private void handleProximo() throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/petshop/FXMLTelaProduto.fxml"));
        stage = new Stage();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Cadastro de Cliente"); 
        stage.show();
}


    @FXML
    private void handleSalvar() {
        if (clienteAtual == null) {
            showAlert(AlertType.ERROR, "Erro", "Não há cliente para salvar.");
            return;
        }

        // Verificar se todos os campos obrigatórios estão preenchidos
        if (produto.getText().isEmpty() || cod.getText().isEmpty() || valorUnt.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erro", "Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        // Preencher clienteAtual com os dados dos campos
        clienteAtual.setProduto(produto.getText());
        clienteAtual.setCod(cod.getText());
        clienteAtual.setValorUnt(Double.parseDouble(valorUnt.getText()));
        clienteAtual.setQuantidade (Int.parseInt(quantidade.getText());
        clienteAtual.setEstoque(estoque.getText());
        clienteAtual.setFornecedor(fornecedor.getText());
        clienteAtual.setDescricao(descricao.getText());
        

        // Verificar se o campo 'Numero' tem valor válido
        try {
            int numero = Integer.parseInt(tfNumero.getText());
            clienteAtual.setNumero(numero);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erro", "Número inválido.");
            return;  // Impede salvar se o número for inválido
        }

        // Salvar cliente, se é um novo ou uma atualização
        clienteDAO.salvar(clienteAtual);

        estadoFormulario = "inicial";
        clienteAtual = null; // Limpa o cliente após salvar
        atualizarBotoes();
        showAlert(AlertType.INFORMATION, "Sucesso", "Cliente salvo com sucesso.");
        limparCampos();  // Limpa os campos após salvar
    }

    @FXML
    private void handleCancelar() {
        estadoFormulario = "inicial";
        clienteAtual = null; // Limpa o cliente
        atualizarBotoes();
        limparCampos();  // Limpa os campos
    }

    @FXML
    private void handleExcluir() {
        if (clienteAtual == null) {
            showAlert(AlertType.ERROR, "Erro", "Não há cliente para excluir.");
            return;
        }

        // Confirmar exclusão
        Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
        alertConfirmacao.setTitle("Confirmar Exclusão");
        alertConfirmacao.setHeaderText("Você tem certeza que deseja excluir este cliente?");
        alertConfirmacao.setContentText("Esta ação não pode ser desfeita.");
        
        // Espera a resposta do usuário
        if (alertConfirmacao.showAndWait().get() == ButtonType.OK) {
            // Excluir cliente usando CPF
            clienteDAO.excluir(clienteAtual.getCpf());
            
            estadoFormulario = "inicial";
            clienteAtual = null; // Limpa o cliente
            atualizarBotoes();
            showAlert(AlertType.INFORMATION, "Sucesso", "Cliente excluído com sucesso.");
            limparCampos();  // Limpa os campos após exclusão
        } else {
            showAlert(AlertType.INFORMATION, "Cancelado", "A exclusão foi cancelada.");
        }
    }

    private void atualizarBotoes() {
        switch (estadoFormulario) {
            case "inicial":
                adicionar.setDisable(false);
                salvar.setDisable(true);
                cancelar.setDisable(true);
                excluir.setDisable(false);
                proximo.setDisable(false);
                break;
            case "editando":
                adicionar.setDisable(true);
                salvar.setDisable(false);
                cancelar.setDisable(false);
                excluir.setDisable(true);
                proximo.setDisable(false);
                break;
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Limpa todos os campos de texto
    private void limparCampos() {
        produto.clear();
        tfNome.clear();
        tfEmail.clear();
        tfTelefone.clear();
        tfCelular.clear();
        tfRg.clear();
        tfCnpj.clear();
        tfCep.clear();
        tfRua.clear();
        tfNumero.clear();
    }
}





