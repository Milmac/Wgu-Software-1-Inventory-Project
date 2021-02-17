package View_Controller;

import Models.Inventory;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static Models.Inventory.getPartInventory;

public class AddProductsController implements Initializable
{

    //Labels
    @FXML
    private Label addProductTitleLabel;
    @FXML
    private Label addProductProductIDLabel;
    @FXML
    private Label addProductProductNameLabel;
    @FXML
    private Label addProductInvLevelLabel;
    @FXML
    private Label addProductPriceLabel;
    @FXML
    private Label addProductMinLabel;
    @FXML
    private Label addProductMaxLabel;
    @FXML
    private Label addProductSearchPartLabel;
    @FXML
    private Label addProductAssociatedPartsLabel;

    //TextFields
    @FXML
    private TextField addProductIDTextField;
    @FXML
    private TextField addProductNameTextField;
    @FXML
    private TextField addProductInvLevelTextField;
    @FXML
    private TextField addProductPriceTextField;
    @FXML
    private TextField addProductMinTextField;
    @FXML
    private TextField addProductMaxTextField;
    @FXML
    private TextField addProductSearchPartTextField;

    //Buttons
    @FXML
    private Button addProductSearchPartButton;
    @FXML
    private Button addProductPartButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private Button deleteAssociatedPartButton;

    //Existing parts table
    @FXML
    private TableView<Part> addProductAddTableView;
    @FXML
    private TableColumn<Part, Integer> addProductPartIDCol;
    @FXML
    private TableColumn<Part, String> addProductPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addProductInvLevelCol;
    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    //Associated Parts Table
    @FXML
    private TableView<Part> addProductCurrentTableView;
    @FXML
    private TableColumn<Part, Integer> addProductCurrentPartIDCol;
    @FXML
    private TableColumn<Part, String> addProductCurrentPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addProductCurrentInvLevelCol;
    @FXML
    private TableColumn<Part, Double> addProductCurrentPriceCol;

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String exceptionMessage = new String();
    private int productID;

    public AddProductsController()
    {
    }

    //If user clicks search button
    public void AddProductSearchButtonPushed(ActionEvent event)
    {
        String searchPartString = addProductSearchPartTextField.getText();
        int partIndex = -1;
        //if it returns nothing
        if (Inventory.lookupPart(searchPartString) == -1)
        {
            PartUpdateTableView();
            addProductSearchPartTextField.setText("");

            System.out.println("Can't find part");

        } else //find the part
        {
            partIndex = Inventory.lookupPart(searchPartString);
            Part tempPart = Inventory.getPartInventory().get(partIndex);

            ObservableList<Part> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(tempPart);
            addProductAddTableView.setItems(tempProdList);
        }
    }


    public void AddProductAddButtonPushed(ActionEvent event)
    {
        Part part = addProductAddTableView.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        PartUpdateCurrentTableView();
    }


    public void AddProductDeleteButtonPushed(ActionEvent event)
    {
        Part part = addProductCurrentTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            System.out.println(part.getPartName() + " was deleted.");
            currentParts.remove(part);
        } else
        {
            System.out.println("User has selected cancel.");
        }
    }


    public void AddProductSaveButtonPushed(ActionEvent event) throws IOException
    {
        String productName = addProductNameTextField.getText();
        String productInventory = addProductInvLevelTextField.getText();
        String productPricePerUnit = addProductPriceTextField.getText();
        String productMin = addProductMinTextField.getText();
        String productMax = addProductMaxTextField.getText();

        try
        {
            exceptionMessage = Product.isProductValid(productName, Integer.parseInt(productMin),
                    Integer.parseInt(productMax), Integer.parseInt(productInventory),
                    Double.parseDouble(productPricePerUnit), currentParts, exceptionMessage);

            if (exceptionMessage.length() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Adding Product Error");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else
            {
                System.out.println("Product name: " + productName);
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                newProduct.setProductName(productName);
                newProduct.setProductInventoryLevel(Integer.parseInt(productInventory));
                newProduct.setProductPricePerUnit(Double.parseDouble(productPricePerUnit));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setProductParts(currentParts);
                Inventory.addProduct(newProduct);

                Parent addProductSaveParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(addProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
        }
    }


    public void AddProductCancelButtonPushed(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Please Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            Parent addProductCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(addProductCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else
        {
            System.out.println("User selected cancel.");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        addProductPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addProductPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addProductInvLevelCol.setCellValueFactory(cellData -> cellData.getValue().partInventoryLevelProperty().asObject());
        addProductPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPricePerUnitProperty().asObject());

        addProductCurrentPartIDCol.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addProductCurrentPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addProductCurrentInvLevelCol.setCellValueFactory(cellData -> cellData.getValue().partInventoryLevelProperty().asObject());
        addProductCurrentPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPricePerUnitProperty().asObject());

        //Update the table view
        PartUpdateTableView();
        PartUpdateCurrentTableView();

        productID = Inventory.getProductIdCount();
        addProductIDTextField.setText("Auto Gen - " + productID);
    }

    public void PartUpdateTableView()
    {
        addProductAddTableView.setItems(getPartInventory());
    }

    public void PartUpdateCurrentTableView()
    {
        addProductCurrentTableView.setItems(currentParts);
    }

}