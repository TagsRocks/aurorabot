package ms.aurora.gui.account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ms.aurora.core.model.Account;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author rvbiljouw
 */
public class AccountOverview extends AnchorPane {

    private final ObservableList<AccountModel> accounts = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<AccountModel, String> colBankPin;

    @FXML
    private TableColumn<AccountModel, String> colPassword;

    @FXML
    private TableColumn<AccountModel, String> colUsername;

    @FXML
    private TableView<AccountModel> tblAccounts;

    public AccountOverview() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccountOverview.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void onCancel(ActionEvent event) {
        getScene().getWindow().hide();
    }

    @FXML
    void onNewAccount(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("New account");
        stage.setWidth(330);
        stage.setHeight(264);
        stage.initModality(Modality.APPLICATION_MODAL);
        NewAccount newDialog = new NewAccount();
        Scene scene = new Scene(newDialog);
        scene.getStylesheets().add("blue.css");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                initializeTable();
            }
        });
    }

    @FXML
    void onOk(ActionEvent event) {
        getScene().getWindow().hide();
    }

    @FXML
    void onRemoveSelected(ActionEvent event) {
        AccountModel selectedAccount = tblAccounts.getSelectionModel().getSelectedItem();
        if(selectedAccount != null) {
            selectedAccount.getAccount().remove();
            accounts.remove(selectedAccount);
        }
        initializeTable();
    }

    @FXML
    void onEditSelected(ActionEvent event) {
        AccountModel selectedAccount = tblAccounts.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            Stage stage = new Stage();
            stage.setTitle("Edit account");
            stage.setWidth(330);
            stage.setHeight(264);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditAccount editDialog = new EditAccount(selectedAccount);
            Scene scene = new Scene(editDialog);
            scene.getStylesheets().add("blue.css");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    initializeTable();
                }
            });
        }
    }

    @FXML
    void initialize() {
        assert colBankPin != null : "fx:id=\"colBankPin\" was not injected: check your FXML file 'AccountOverview.fxml'.";
        assert colPassword != null : "fx:id=\"colPassword\" was not injected: check your FXML file 'AccountOverview.fxml'.";
        assert colUsername != null : "fx:id=\"colUsername\" was not injected: check your FXML file 'AccountOverview.fxml'.";
        assert tblAccounts != null : "fx:id=\"tblAccounts\" was not injected: check your FXML file 'AccountOverview.fxml'.";

        colUsername.setCellValueFactory(new PropertyValueFactory<AccountModel, String>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<AccountModel, String>("password"));
        colBankPin.setCellValueFactory(new PropertyValueFactory<AccountModel, String>("bankPin"));

        initializeTable();
    }

    private void initializeTable() {
        accounts.clear();
        for (Account account : Account.getAll()) {
            AccountModel model = new AccountModel(account);
            accounts.add(model);
        }
        tblAccounts.setItems(accounts);
    }
}