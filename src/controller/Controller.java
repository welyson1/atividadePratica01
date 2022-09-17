package controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ManipuladorArquivo;
import model.Recurso;

public class Controller implements Initializable{

    @FXML
    private Button btnCadastrarProjeto;
    @FXML
    private Button btnCadastrarRecurso;
    @FXML
    private Button btnCadastroLicencaNecessaria;
    @FXML
    private Button btnCadastroLicencaObtida;
    @FXML
    private Button btnConsultaLicencaNecessaria;
    @FXML
    private Button btnConsultaLicencaObtida;
    @FXML
    private Button btnConsultaProjetos;
    @FXML
    private Button btnConsultaRecursos;
    @FXML
    private TextField entradaEmail;
    @FXML
    private TextField entradaEmailRecursoLicencaObtida;
    @FXML
    private TextField entradaNome;
    @FXML
    private TextField entradaNomeLicencaNecessaria;
    @FXML
    private TextField entradaNomeLicencaObtida;
    @FXML
    private TextField entradaNomeProjeto;
    @FXML
    private TextField entradaProjeto;
    @FXML
    private TextField entradaTecnologia;
    @FXML
    private TextField entradaValor;
    @FXML
    private TextField entrataCategoriaLicencaNecessaria;
    @FXML
    private TextField entrataLevelLicencaNecessaria;
    @FXML
    private TextField entrataLinkLicencaNecessaria;
    @FXML
    private TextArea txtAreaConsulta;
    @FXML
    private TextArea txtAreaConsultaLicencaNecessaria;
    @FXML
    private TextArea txtAreaConsultaLicencaObtida;
    @FXML
    private TextArea txtAreaConsultaProjetos;

    @FXML
    private TableColumn<Recurso, String> tableViewColunaEmail;
    @FXML
    private TableColumn<Recurso, String> tableViewColunaNome;
    @FXML
    private TableView<Recurso> tableViewRecursos;
    @FXML
    private TableColumn<Recurso, String> tableviewColunaProjeto;

//-------------------------------------------------------------

    ManipuladorArquivo manipuladorArquivo = new ManipuladorArquivo();   
    
    String caminhoDataRecursos = "C:/Users/wcarlos/Documents/GitHub/atividadePratica01/dataBase/Recursos.txt";
    String caminhoDataLicencasObtidas = "C:/Users/wcarlos/Documents/GitHub/atividadePratica01/dataBase/LicencasObtidas.txt";
    String caminhoDataLicencasNecessarias = "C:/Users/wcarlos/Documents/GitHub/atividadePratica01/dataBase/LicencasNecessarias.txt";
    String caminhoDataProjetos = "C:/Users/wcarlos/Documents/GitHub/atividadePratica01/dataBase/Projetos.txt";
    
//-------------------------------------------------------------  

    //Declaração de arrayList
    private List<Recurso> listRecursos = new ArrayList<Recurso>();

    //Declaração do observador
    private ObservableList<Recurso> observableList;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        //Observador de seleção do item na lista
        tableViewRecursos.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> recursoSelecionado(newValue));        
        
    }

    @FXML
    void cadastrarRecurso(ActionEvent event) throws FileNotFoundException, IOException { 
        //Associação das variaveis da classe com as probliedades da tabela
        tableViewColunaNome.setCellValueFactory(new PropertyValueFactory<>("recursoNome"));
        tableViewColunaEmail.setCellValueFactory(new PropertyValueFactory<>("recursoEmail"));
        tableviewColunaProjeto.setCellValueFactory(new PropertyValueFactory<>("recursoProjeto"));

        //Instancia do recurso
        Recurso recurso = new Recurso(entradaNome.getText(), entradaEmail.getText(), entradaProjeto.getText());
 
        //Adição no arrayList
        listRecursos.add(recurso);

        //Ação do observador
        observableList = FXCollections.observableArrayList(listRecursos);
        tableViewRecursos.setItems(observableList);

        String entradaInfos = 
            recurso.getRecursoNome() + ";"
            + recurso.getRecursoEmail() + ";" 
            + recurso.getRecursoProjeto();

        manipuladorArquivo.manipuladorEscrita(entradaInfos, caminhoDataRecursos);

    }

    //Metodo de log para saber a seleção do cliente
    public void recursoSelecionado(Recurso recurso) {
        System.out.println("Seleção: " + recurso.getRecursoNome());
    }

    @FXML
    public void excluirRecurso() {
        //Passando o item selecionado para variavel
        Recurso recursoRemover = tableViewRecursos.getSelectionModel().getSelectedItem();
        //Log
        System.out.println("Recurso removido" + recursoRemover.getRecursoNome());
        //Remoção do recurso
        tableViewRecursos.getItems().remove(recursoRemover);
        //Remoção do ArrayList
        listRecursos.remove(recursoRemover);

    }
    
    @FXML
    void consultaRecursos(ActionEvent event) {
        //Declaração do array para guardar as informações
        String infoArrayExterno[] = null;  

        //Atribuição da saida do medodo manipuladorLeitura para o array
        infoArrayExterno = manipuladorArquivo.manipuladorLeitura(caminhoDataRecursos);

        /*
         * Corre o array pegando a linha e separando no delimitador
         * Exemplo: welyson;welyson@gmail.com;CALRO RAN
         * Para:
         *  welyson
         *  welyson@gmail.com
         *  CLARO RAN
         * Depois exibie no textArea
         */
        for (String item : infoArrayExterno) {

            StringTokenizer Linguicao = 
                new StringTokenizer(item, ";");
            while (Linguicao.hasMoreTokens()){ //Executa enquanto tiver Tokens
                txtAreaConsulta.appendText("\n" + Linguicao.nextToken()); //Acrescenta no textArea
            } 
            
            //Perfumaria
            txtAreaConsulta.appendText("\n");
            txtAreaConsulta.appendText("----------------");         
        }
    }
    
//-------------------------------------------------------------

    @FXML
    void cadastrarProjeto(ActionEvent event) throws FileNotFoundException, IOException {
        String entradaInfos = 
            entradaNomeProjeto.getText() + ";"
            + entradaTecnologia.getText() + ";" 
            + entradaValor.getText();
        //manipuladorArquivo.manipuladorEscrita(entradaInfos, caminhoDataProjetos);
    }
    @FXML
    void consultaProjetos(ActionEvent event) {
        String infoArrayExterno[] = null;    
        infoArrayExterno = manipuladorArquivo.manipuladorLeitura(caminhoDataProjetos);
        for (String item : infoArrayExterno) {            
            StringTokenizer Linguicao = 
                new StringTokenizer(item, ";");
            while (Linguicao.hasMoreTokens()){
                txtAreaConsultaProjetos.appendText("\n" + Linguicao.nextToken());
            }  
            txtAreaConsultaProjetos.appendText("\n");
            txtAreaConsultaProjetos.appendText("----------------");          
        }
    }

//-------------------------------------------------------------

    @FXML
    void cadastroLicencaNecessaria(ActionEvent event) throws FileNotFoundException, IOException {
        String entradaInfos = 
            entradaNomeLicencaNecessaria.getText() + ";" 
            + entrataLinkLicencaNecessaria.getText() + ";" 
            + entrataCategoriaLicencaNecessaria.getText() + ";" 
            + entrataLevelLicencaNecessaria.getText();
        //manipuladorArquivo.manipuladorEscrita(entradaInfos, caminhoDataLicencasNecessarias);
    }
    @FXML
    void consultaLicencaNecessaria(ActionEvent event) {
        String infoArrayExterno[] = null;    
        infoArrayExterno = manipuladorArquivo.manipuladorLeitura(caminhoDataLicencasNecessarias);
        for (String item : infoArrayExterno) {            
            StringTokenizer Linguicao = 
                new StringTokenizer(item, ";");
            while (Linguicao.hasMoreTokens()){
                txtAreaConsultaLicencaNecessaria.appendText("\n" + Linguicao.nextToken());
            }  
            txtAreaConsultaLicencaNecessaria.appendText("\n");
            txtAreaConsultaLicencaNecessaria.appendText("----------------");          
        }
    } 

//-------------------------------------------------------------

    @FXML
    void cadastroLicencaObtida(ActionEvent event) throws FileNotFoundException, IOException {
        String entradaInfos = 
            entradaEmailRecursoLicencaObtida.getText() + ";" 
            + entradaNomeLicencaObtida.getText();

        //manipuladorArquivo.manipuladorEscrita(entradaInfos, caminhoDataLicencasObtidas);
    }
    @FXML
    void consultaLicencaObtida(ActionEvent event) {
        String infoArrayExterno[] = null;    
        infoArrayExterno = manipuladorArquivo.manipuladorLeitura(caminhoDataLicencasObtidas);
        for (String item : infoArrayExterno) {            
            StringTokenizer Linguicao = 
                new StringTokenizer(item, ";");
            while (Linguicao.hasMoreTokens()){
                txtAreaConsultaLicencaObtida.appendText("\n" + Linguicao.nextToken());
            }  
            txtAreaConsultaLicencaObtida.appendText("\n");
            txtAreaConsultaLicencaObtida.appendText("----------------");          
        }
    } 
    

}