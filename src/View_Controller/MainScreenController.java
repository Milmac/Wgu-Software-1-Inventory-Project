package View_Controller;

import Models.Inventory;
import static Models.Inventory.deletePart;
import static Models.Inventory.deleteProduct;
import static Models.Inventory.getPartInventory;
import static Models.Inventory.getProductInventory;
import static Models.Inventory.partDeleteValidation;
import Models.Part;
import Models.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wguMainAppSoftware1.mainApp;


public class MainScreenController implements Initializable
{
    //Main Screen Variables
    @FXML
    private Label mainScreenLabel;
    @FXML
    private Label partsLabel;
    @FXML
    private Label productsLabel;
    @FXML
    private Button partsSearchButton;
    @FXML
    private Button productSearchButton;
    @FXML
    private Button exitButton;
    @FXML
    private Pane partsPane;
    @FXML
    private Pane productPane;

    //Parts Objects
    @FXML
    private Button addPartButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private TableView<Part> mainPartsTableView;
    @FXML
    private TableColumn<Part, Integer> mainPartIDCol;
    @FXML
    private TableColumn<Part, String> mainPartNameCol;
    @FXML
    private TableColumn<Part, Integer> mainPartInventoryCol;
    @FXML
    private TableColumn<Part, Double> mainPartPriceCol;
    //Product Textfield
    @FXML
    private TextField mainPartsSearchField;

    private static Part modifyPart;
    private static int modifyPartIndex;

    //Products Variables
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private TableView<Product> mainProductTableView;
    @FXML
    private TableColumn<Product, Integer> mainProductIDCol;
    @FXML
    private TableColumn<Product, String> mainProductNameCol;
    @FXML
    private TableColumn<Product, Integer> mainProductInventoryCol;
    @FXML
    private TableColumn<Product, Double> mainProductPriceCol;
    //Product TextField
    @FXML
    private TextField mainProductSearchField;

    private static Product modifyProduct;
    private static int modifyProductIndex;

    public static int partModifyIndex()
    {
        return modifyPartIndex;
    }

    public static int productModifyIndex()
    {
        return modifyProductIndex;
    }

    public void MainScreenController()
    {
    }

    public void mainExitButtonPushed(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Please Confirm");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            System.exit(0);
        } else
        {
            System.out.println("Exit Aborted");
        }
    }

    public void addPartButtonPushed(ActionEvent event) throws IOException
    {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddParts.fxml"));
        Scene addPartScene = new Scene(addPartParent);


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    public void modifyPartButtonPushed(ActionEvent event) throws IOException
    {
        modifyPart = mainPartsTableView.getSelectionModel().getSelectedItem();
        modifyPartIndex = getPartInventory().indexOf(modifyPart);
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("EditParts.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(modifyPartScene);
        window.show();
    }

    public void mainPartsSearchButtonPushed(ActionEvent event) throws IOException
    {
        String searchPartString = mainPartsSearchField.getText();
        int partIndex = -1;
        //if there is nothing to search
        if (Inventory.lookupPart(searchPartString) == -1)
        {
            //show all parts
            updatePartTableView();
            mainPartsSearchField.setText("");

            System.out.println("Can't find part");

        } else
        {
            partIndex = Inventory.lookupPart(searchPartString);
            Part tempPart = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            mainPartsTableView.setItems(tempPartList);
        }
    }

    public void deletePartButtonPushed(ActionEvent event) throws IOException
    {
        Part part = mainPartsTableView.getSelectionModel().getSelectedItem();
        if (partDeleteValidation(part))
        {
            //Create alert window
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Delete Part!");
            alert.setContentText("Please remove any connected products to " + part.getPartName() + "!");
            alert.showAndWait();
        } else
        {
            //Create alert window
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Please Confirm Delete");
            alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK)
            {
                deletePart(part);
                updatePartTableView();
                System.out.println(part.getPartName() + " was permanently removed.");
            } else
            {
                System.out.println("Cancelled Action");
            }
        }
    }

    public void addProductButtonPushed(ActionEvent event) throws IOException
    {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProducts.fxml"));
        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }

    public void modifyProductButtonPushed(ActionEvent event) throws IOException
    {
        modifyProduct = mainProductTableView.getSelectionModel().getSelectedItem();
        modifyProductIndex = getProductInventory().indexOf(modifyProduct);
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("EditProducts.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(modifyProductScene);
        window.show();
    }

    public void mainProductSearchButtonPushed(ActionEvent event) throws IOException
    {
        String searchProductString = mainProductSearchField.getText();
        int productIndex = -1;
        if (Inventory.lookupProduct(searchProductString) == productIndex)
        {
            //if nothing shows up, then
            updateProductTableView();
            mainProductSearchField.setText("");

            System.out.println("Can't find product");

        } else
        {
            productIndex = Inventory.lookupProduct(searchProductString);
            Product tempProduct = Inventory.getProductInventory().get(productIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProduct);
            mainProductTableView.setItems(tempProductList);
        }
    }


    public void deleteProductButtonPushed(ActionEvent event) throws IOException
    {
        Product product = mainProductTableView.getSelectionModel().getSelectedItem();

        //Create alert window
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Please Confirm");
        alert.setContentText("Are you sure you want to permanently delete " + product.getProductName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            deleteProduct(product);
            updateProductTableView();
            System.out.println(product.getProductName() + " was removed.");
        } else
        {
            System.out.println(product.getProductName() + " was not removed");
        }
    }


    //When a user clicks on the table
    public void userClickedOnTable()
    {
        this.deletePartButton.setDisable(false);
        this.modifyPartButton.setDisable(false);
        this.addPartButton.setDisable(false);
        this.deleteProductButton.setDisable(false);
        this.modifyProductButton.setDisable(false);
        this.addProductButton.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mainPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        mainPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        mainPartInventoryCol.setCellValueFactory(cellData -> cellData.getValue().partInventoryLevelProperty().asObject());
        mainPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPricePerUnitProperty().asObject());

        mainProductIDCol.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        mainProductNameCol.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        mainProductInventoryCol.setCellValueFactory(cellData -> cellData.getValue().productInventoryLevelProperty().asObject());
        mainProductPriceCol.setCellValueFactory(cellData -> cellData.getValue().productPricePerUnitProperty().asObject());

        updatePartTableView();
        updateProductTableView();
    }

    public void updatePartTableView()
    {
        mainPartsTableView.setItems(getPartInventory());
    }
    public void updateProductTableView()
    {
        mainProductTableView.setItems(getProductInventory());
    }

    public void setMainApp(mainApp mainApp)
    {
        updatePartTableView();
        updateProductTableView();

    }

}