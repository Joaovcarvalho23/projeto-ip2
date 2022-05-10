package br.ufrpe.habitact.gui;

import br.ufrpe.habitact.excecoes.ObjetoDuplicadoException;
import br.ufrpe.habitact.gui.modelos.ModeloPlanoAlimentarCliente;
import br.ufrpe.habitact.negocio.Fachada;
import br.ufrpe.habitact.negocio.beans.Alimento;
import br.ufrpe.habitact.negocio.beans.enums.ObjetivoAlimentar;
import br.ufrpe.habitact.negocio.beans.enums.Refeicao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TelaCadastroPlanoAlimentarController {

    @FXML private Button btnCancelarCadastrarPressed;
    @FXML private DatePicker dtFim;
    @FXML private DatePicker dtInicio;
    @FXML private TextField textOutro;
    @FXML private ComboBox<Refeicao> refeicao;
    @FXML private ComboBox<ObjetivoAlimentar> objetivo;
    @FXML private TableView<ModeloPlanoAlimentarCliente> tblAlimentos;
    @FXML private TableColumn<ModeloPlanoAlimentarCliente, Boolean> colunaCheck;
    @FXML private TableColumn<ModeloPlanoAlimentarCliente, Double> colQtdAlimento;
    @FXML private TableColumn<ModeloPlanoAlimentarCliente, Double> colCaloriasAlimento;
    @FXML private TableColumn<ModeloPlanoAlimentarCliente, String> colNomeAlimento;

    public void initialize() {
        //Adiciona os valores dos enuns ObjetivoAlimentar e Refeicao em cada ComboBox
        this.objetivo.getItems().addAll(ObjetivoAlimentar.values());
        this.refeicao.getItems().addAll(Refeicao.values());

        //Setando as colunas da TableView
        this.colunaCheck.setCellValueFactory(new PropertyValueFactory<>("check"));
        this.colunaCheck.setCellFactory(CheckBoxTableCell.forTableColumn(colunaCheck));
        this.colNomeAlimento.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.colQtdAlimento.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        this.colCaloriasAlimento.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        this.updateCatalogoAlimentos();

    }

    //Habilita o campo Outro para digitação
    @FXML
    void optOutrosSelecionado(ActionEvent event) {
        if(objetivo.getValue().getObjetivo().equalsIgnoreCase("")){
            this.textOutro.setDisable(false);
        }
    }

    //Direcionar para uma tela de diálogo que aparecerá sobre a tela atual
    @FXML
    void btnAddAlimento(ActionEvent event) {
        Stage dialog = new Stage();
        dialog.setScene(GerenciadorTelas.getInstance().getAddAlimentoScene());
        dialog.setResizable(false);
        dialog.setTitle("Cadastrar Alimentos");
        dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }

    @FXML
    void btnCadastrarPressed(ActionEvent event) {
        if (verificarCamposVazios()) {
            GerenciadorTelas.getInstance().alertaCamposVazios();
        } else {

            //TODO Criar atributo static para usuário ser cadastrado junto. Mas, precisa saber em qual tela...
            /*PlanoAlimentar p = new PlanoAlimentar(usuario, dtInicio.getValue(), dtFim.getValue(),
                  objetivo.getValue());

            try {
                Fachada.getInstance().cadastrarPlanoAlimentar(p);
            } catch (ObjetoDuplicadoException | MaisDeUmPlanoNoMesmoPeriodoException e) {
                e.getMessage();
            }*/

            this.limparCamposDeDados();
            ((Stage)this.btnCancelarCadastrarPressed.getScene().getWindow()).close();
        }
    }

    @FXML
    void btnVoltarTela(ActionEvent event) {
        //TODO está voltando para a tela de Login
        GerenciadorTelas.getInstance().trocarTela("telaPrincipalAdm");
    }

    private void updateCatalogoAlimentos() {
        //Instancia treinos para Controlador ALimento
        ArrayList<Alimento> listaAlimentos = new ArrayList<>();

        Alimento a1 = new Alimento("Pão Integral", 25, 137, Refeicao.CAFÉ_DA_MANHÃ, LocalDate.of(2022, 5, 6));
        Alimento a2 = new Alimento("Feijão", 100, 79, Refeicao.ALMOÇO, LocalDate.of(2022, 5, 8));
        Alimento a3 = new Alimento("Arroz", 100, 129, Refeicao.ALMOÇO, LocalDate.of(2022, 5, 8));
        Alimento a4 = new Alimento("Sorvete", 60, 95, Refeicao.LANCHE, LocalDate.of(2022, 5, 2));
        Alimento a5 = new Alimento("Ovo", 30, 74, Refeicao.JANTAR, LocalDate.of(2022, 5, 3));
        /*listaAlimentos.add(a1);
        listaAlimentos.add(a2);
        listaAlimentos.add(a3);
        listaAlimentos.add(a4);
        listaAlimentos.add(a5);*/

        try {
            /*for(Alimento alimento : listaAlimentos){
                if(refeicao.getValue().getRefeicao().equalsIgnoreCase("Café da manhã")){
                    Fachada.getInstance().adicionarAlimento(alimento);
                } else if (refeicao.getValue().getRefeicao().equalsIgnoreCase("Almoço")){
                    Fachada.getInstance().adicionarAlimento(alimento);
                } else if (refeicao.getValue().getRefeicao().equalsIgnoreCase("Lanche")){
                    Fachada.getInstance().adicionarAlimento(alimento);
                } else {
                    Fachada.getInstance().adicionarAlimento(alimento);
                }
            }*/
            Fachada.getInstance().adicionarAlimento(a1);
            Fachada.getInstance().adicionarAlimento(a2);
            Fachada.getInstance().adicionarAlimento(a3);
            Fachada.getInstance().adicionarAlimento(a4);
            Fachada.getInstance().adicionarAlimento(a5);
        } catch (ObjetoDuplicadoException e) {
            e.printStackTrace();
        }

        ObservableList<ModeloPlanoAlimentarCliente> result = FXCollections.observableArrayList();
        List<Alimento> listaDeAlimentos = Fachada.getInstance().listarAlimento();
        for (Alimento a : listaDeAlimentos) {
            result.add(new ModeloPlanoAlimentarCliente(a));
        }
        tblAlimentos.setItems(result);
    }

    private void limparCamposDeDados() {
        this.textOutro.setText("");
        this.dtInicio.setValue(null);
        this.dtFim.setValue(null);
        this.refeicao.getSelectionModel().clearSelection();
        this.objetivo.getSelectionModel().clearSelection();
        this.tblAlimentos.getSelectionModel().clearSelection();
    }

    private boolean verificarCamposVazios() {
        return dtInicio.getValue() == null || dtFim.getValue() == null ||
                refeicao.getValue() == null || objetivo.getValue() == null &&
                textOutro.getText().isBlank();
    }
}
