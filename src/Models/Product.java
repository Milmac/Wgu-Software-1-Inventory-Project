package Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product
{

    //Changed parts variable to a non static variable to fix the modify bug :)
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private final IntegerProperty productID, productInventoryLevel, productMin, productMax;
    private final StringProperty productName;
    private final DoubleProperty productPricePerUnit;

    //Constructor
    public Product()
    {
        productID = new SimpleIntegerProperty();
        productName = new SimpleStringProperty();
        productPricePerUnit = new SimpleDoubleProperty();
        productInventoryLevel = new SimpleIntegerProperty();
        productMin = new SimpleIntegerProperty();
        productMax = new SimpleIntegerProperty();
    }

    //Properties
    public IntegerProperty productIDProperty(){ return productID; }
    public StringProperty productNameProperty(){ return productName; }
    public DoubleProperty productPricePerUnitProperty(){ return productPricePerUnit; }
    public IntegerProperty productInventoryLevelProperty(){ return productInventoryLevel; }

    //#region Getters
    public int getProductID(){ return this.productID.get(); }
    public String getProductName(){ return this.productName.get(); }
    public double getProductPricePerUnit(){ return this.productPricePerUnit.get(); }
    public int getProductInventoryLevel(){ return this.productInventoryLevel.get(); }
    public int getProductMin(){ return this.productMin.get(); }
    public int getProductMax(){ return this.productMax.get(); }
    public ObservableList getProductParts() { return parts; }
    //#endregion

    //#region Setters
    public void setProductID(int productID){ this.productID.set(productID); }
    public void setProductName(String productName){ this.productName.set(productName); }
    public void setProductPricePerUnit(double productPricePerUnit){ this.productPricePerUnit.set(productPricePerUnit); }
    public void setProductInventoryLevel(int productInventoryLevel){ this.productInventoryLevel.set(productInventoryLevel); }
    public void setProductMin(int productMin){ this.productMin.set(productMin); }
    public void setProductMax(int productMax){ this.productMax.set(productMax); }
    public void setProductParts(ObservableList<Part> parts) { this.parts = parts; }
    //#endregion

    //Validation
    public static String isProductValid(String productName, int productMin, int partMax,
                                        int productInventory, double partPrice, ObservableList<Part> parts, String message)
    {
        double totalPrice = 0.00;
        //Get the cost of every part and add to the totalPrice
        for (int i = 0; i < parts.size(); i++) { totalPrice = totalPrice + parts.get(i).getPartPricePerUnit(); }

        if (productName == "") { message = message + ("Please enter Product Name."); }
        if (productMin < 0) { message = message + ("Please use a number greater than 0."); }
        if (partPrice < 0) { message = message + ("Please use a number greater than 0."); }
        if (productMin > partMax) { message = message + ("Please use a number less than the Max Value"); }
        if (productInventory < productMin || productInventory > partMax) { message = message + ("Please use a number inbetween the Min and Max value"); }
        if (parts.size() < 1) { message = message + ("Product must contain at least 1 part."); }
        if (totalPrice > partPrice) { message = message + ("Please use a number greater than the total price."); }

        return message;
    }
}