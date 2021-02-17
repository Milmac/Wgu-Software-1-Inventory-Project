package View_Controller;

import Models.InHousePart;
import Models.Inventory;
import Models.OutSourcePart;
import Models.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddPartsController implements Initializable
{

    //Part Labels
    @FXML
    private Label addPartLabel;
    @FXML
    private Label addPartIDLabel;
    @FXML
    private Label addPartNameLabel;
    @FXML
    private Label addPartInvLevelLabel;
    @FXML
    private Label addPartPriceLabel;
    @FXML
    private Label addPartMinLabel;
    @FXML
    private Label addPartMaxLabel;
    @FXML
    private Label addPartMachineCompanyLabel;

    //Radio Buttons
    @FXML
    private RadioButton addPartInhouseRadioButton;
    @FXML
    private RadioButton addPartOutsourcedRadioButton;

    //Save and Cancel Buttons
    @FXML
    private Button addPartSaveButton;
    @FXML
    private Button addPartCancelButton;

    //Text Fields
    @FXML
    private TextField addPartIDTextField;
    @FXML
    private TextField addPartNameTextField;
    @FXML
    private TextField addPartInvTextField;
    @FXML
    private TextField addPartPriceTextField;
    @FXML
    private TextField addPartMinTextField;
    @FXML
    private TextField addPartMaxTextField;
    @FXML
    private TextField addPartMachineCompanyTextField;


    private String exceptionMessage = new String();
    private int partID;


    //if the radio button is pressed
    public void addPartInHouseRadioPushed(ActionEvent event)
    {
        addPartMachineCompanyLabel.setText("Machine ID");
        addPartMachineCompanyTextField.setPromptText("Machine ID");
        addPartInhouseRadioButton.setSelected(true);
        addPartOutsourcedRadioButton.setSelected(false);
    }
    //if the radio button is pressed
    public void addPartOutsourcedRadioPushed(ActionEvent event)
    {
        addPartMachineCompanyLabel.setText("Company Name");
        addPartMachineCompanyTextField.setPromptText("Company Name");
        addPartOutsourcedRadioButton.setSelected(true);
        addPartInhouseRadioButton.setSelected(false);
    }

    //if the save button is pressed
    public void addPartSaveButtonPushed(ActionEvent event) throws IOException
    {
        String partName = addPartNameTextField.getText();
        String partInventory = addPartInvTextField.getText();
        String partPrice = addPartPriceTextField.getText();
        String partMin = addPartMinTextField.getText();
        String partMax = addPartMaxTextField.getText();
        String partMachComp = addPartMachineCompanyTextField.getText();

        try
        {
            exceptionMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInventory), Double.parseDouble(partPrice), exceptionMessage);
            if (exceptionMessage.length() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Cannot add Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            } else
            {
                //if the radio button was selected
                if (addPartInhouseRadioButton.isSelected())
                {
                    InHousePart inPart = new InHousePart();
                    inPart.setPartID(partID);
                    inPart.setPartName(partName);
                    inPart.setPartInventoryLevel(Integer.parseInt(partInventory));
                    inPart.setPartPricePerUnit(Double.parseDouble(partPrice));
                    inPart.setPartMin(Integer.parseInt(partMin));
                    inPart.setPartMax(Integer.parseInt(partMax));
                    inPart.setMachineID(Integer.parseInt(partMachComp));
                    Inventory.addPart(inPart);
                    System.out.println("Part name: " + partName);

                } else if (addPartOutsourcedRadioButton.isSelected())
                {
                    OutSourcePart outPart = new OutSourcePart();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartInventoryLevel(Integer.parseInt(partInventory));
                    outPart.setPartPricePerUnit(Double.parseDouble(partPrice));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setCompanyName(partMachComp);
                    Inventory.addPart(outPart);
                    System.out.println("Part name: " + partName);

                }

                //switch to main screen
                Parent addPartSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(addPartSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        //If we encounter an error
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
        }
    }

    //If the cancel button is pushed
    public void addPartCancelButtonPushed(ActionEvent event) throws IOException
    {
        //show alert warning
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Please Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        //if user presses ok
        if (result.get() == ButtonType.OK)
        {
            Parent addPartCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else
        {
            System.out.println("User has canceled");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        partID = Inventory.getPartIdCount();
        addPartIDTextField.setText("Auto Gen - " + partID);
    }

}