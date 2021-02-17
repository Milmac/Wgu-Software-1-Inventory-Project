package View_Controller;

import Models.InHousePart;
import Models.Inventory;
import static Models.Inventory.getPartInventory;
import Models.OutSourcePart;
import Models.Part;
import static View_Controller.MainScreenController.partModifyIndex;
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


public class EditPartsController implements Initializable
{
    //Label Variables
    @FXML
    private Label modifyPartLabel;
    @FXML
    private Label modifyPartIDLabel;
    @FXML
    private Label modifyPartNameLabel;
    @FXML
    private Label modifyPartInvLevelLabel;
    @FXML
    private Label modifyPartPriceLabel;
    @FXML
    private Label modifyPartMinLabel;
    @FXML
    private Label modifyPartMaxLabel;
    @FXML
    private Label modifyPartMachineCompanyLabel;

    //Radio Buttons
    @FXML
    private RadioButton modifyPartInhouseRadioButton;
    @FXML
    private RadioButton modifyPartOutsourcedRadioButton;

    //Update and Cancel Button
    @FXML
    private Button modifyPartUpdateButton;
    @FXML
    private Button modifyPartCancelButton;

    //Text Fields
    @FXML
    private TextField modifyPartIDTextField;
    @FXML
    private TextField modifyPartNameTextField;
    @FXML
    private TextField modifyPartInvTextField;
    @FXML
    private TextField modifyPartPriceTextField;
    @FXML
    private TextField modifyPartMinTextField;
    @FXML
    private TextField modifyPartMaxTextField;
    @FXML
    private TextField modifyPartMachineCompanyTextField;


    private String exceptionMessage = new String();
    private int partID;
    int partIndex = partModifyIndex();


    public void modifyPartInHouseRadio(ActionEvent event)
    {
        modifyPartOutsourcedRadioButton.setSelected(false);
        modifyPartInhouseRadioButton.setSelected(true);
        modifyPartMachineCompanyLabel.setText("Machine ID");
    }


    public void modifyPartOutsourcedRadio(ActionEvent event)
    {
        modifyPartOutsourcedRadioButton.setSelected(true);
        modifyPartInhouseRadioButton.setSelected(false);
        modifyPartMachineCompanyLabel.setText("Company Name");
    }


    public void modifyPartUpdateButtonPushed(ActionEvent event) throws IOException
    {
        String partName = modifyPartNameTextField.getText();
        String partInventory = modifyPartInvTextField.getText();
        String partPrice = modifyPartPriceTextField.getText();
        String partMin = modifyPartMinTextField.getText();
        String partMax = modifyPartMaxTextField.getText();
        String partMachComp = modifyPartMachineCompanyTextField.getText();

        try
        {

            exceptionMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInventory), Double.parseDouble(partPrice), exceptionMessage);

            System.out.println(partName + Integer.parseInt(partMin) + Integer.parseInt(partMax) + Integer.parseInt(partInventory));

            if (exceptionMessage.length() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";

                System.out.println("Error updating part");


            } else
            {
                if (modifyPartInhouseRadioButton.isSelected())
                {
                    System.out.println("Part name: " + partName + " 1 " + partMachComp);
                    InHousePart inPart = new InHousePart();
                    inPart.setPartID(partID);
                    inPart.setPartName(partName);
                    inPart.setPartInventoryLevel(Integer.parseInt(partInventory));
                    inPart.setPartPricePerUnit(Double.parseDouble(partPrice));
                    inPart.setPartMin(Integer.parseInt(partMin));
                    inPart.setPartMax(Integer.parseInt(partMax));
                    inPart.setMachineID(Integer.parseInt(partMachComp));
                    Inventory.updatePart(partIndex, inPart);

                    System.out.println("Successfully updated InHouse");

                }
                else if (modifyPartOutsourcedRadioButton.isSelected())
                {
                    System.out.println("Part name: " + partName + " 2 " + partMachComp);
                    OutSourcePart outPart = new OutSourcePart();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartInventoryLevel(Integer.parseInt(partInventory));
                    outPart.setPartPricePerUnit(Double.parseDouble(partPrice));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setCompanyName(partMachComp);
                    Inventory.updatePart(partIndex, outPart);

                    System.out.println("Successfully Updated Outsource");

                }

                Parent modifyProductSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Part");
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
        }
    }

    public void modifyPartCancelButtonPushed(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Please Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel modifying the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            Parent modifyPartCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(modifyPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else
        {
            System.out.println("You clicked cancel.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Part part = getPartInventory().get(partIndex);
        partID = getPartInventory().get(partIndex).getPartID();
        modifyPartIDTextField.setText("Auto Gen - " + partID);
        modifyPartNameTextField.setText(part.getPartName());

        modifyPartInvTextField.setText(Integer.toString(part.getPartInventoryLevel()));
        modifyPartPriceTextField.setText(Double.toString(part.getPartPricePerUnit()));
        modifyPartMinTextField.setText(Integer.toString(part.getPartMin()));
        modifyPartMaxTextField.setText(Integer.toString(part.getPartMax()));

        if (part instanceof InHousePart)
        {
            modifyPartMachineCompanyLabel.setText("Machine ID");
            modifyPartMachineCompanyTextField.setText(Integer.toString(((InHousePart) getPartInventory().get(partIndex)).getMachineID()));
            modifyPartInhouseRadioButton.setSelected(true);
        } else
        {
            modifyPartMachineCompanyLabel.setText("Company Name");
            modifyPartMachineCompanyTextField.setText(((OutSourcePart) getPartInventory().get(partIndex)).getCompanyName());
            modifyPartOutsourcedRadioButton.setSelected(true);
        }
    }
}