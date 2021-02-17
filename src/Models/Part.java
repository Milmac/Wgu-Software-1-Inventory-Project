package Models;

import javafx.beans.property.*;

public abstract class Part
{
    private final StringProperty partName;
    private final IntegerProperty partID, partInventoryLevel, partMin, partMax;

    public Part(StringProperty partName, IntegerProperty partID, IntegerProperty partInventoryLevel, IntegerProperty partMin, IntegerProperty partMax, DoubleProperty partPricePerUnit)
    {
        this.partName = partName;
        this.partID = partID;
        this.partInventoryLevel = partInventoryLevel;
        this.partMin = partMin;
        this.partMax = partMax;
        this.partPricePerUnit = partPricePerUnit;
    }

    private final DoubleProperty partPricePerUnit;

    //Constructor
    public Part(){
        partID = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        partInventoryLevel = new SimpleIntegerProperty();
        partPricePerUnit = new SimpleDoubleProperty();
        partMin = new SimpleIntegerProperty();
        partMax = new SimpleIntegerProperty();
    }

    //Properties
    public IntegerProperty partIDProperty(){ return partID; }
    public StringProperty partNameProperty(){ return partName; }
    public DoubleProperty partPricePerUnitProperty(){ return partPricePerUnit; }
    public IntegerProperty partInventoryLevelProperty(){ return partInventoryLevel; }

    //#region Getters
    public String getPartName() { return partName.get(); }
    public int getPartID() { return partID.get(); }
    public int getPartInventoryLevel() { return partInventoryLevel.get(); }
    public int getPartMin() { return partMin.get();}
    public int getPartMax() { return partMax.get(); }
    public double getPartPricePerUnit() { return partPricePerUnit.get(); }
    //#endregion

    //#region Setters
    public void setPartName(String partName){ this.partName.set(partName); }
    public void setPartID(int partID){ this.partID.set(partID); }
    public void setPartInventoryLevel(int partInventoryLevel){ this.partInventoryLevel.set(partInventoryLevel); }
    public void setPartMin(int partMin){ this.partMin.set(partMin); }
    public void setPartMax(int partMax){ this.partMax.set(partMax); }
    public void setPartPricePerUnit(double partPricePerUnit){ this.partPricePerUnit.set(partPricePerUnit); }
    //#endregion


    //Checks if the part is valid
    public static String isPartValid(String partName, int partMin, int partMax, int partInventory, double partPrice, String errorMessage)
    {
        //If the part field is empty
        if (partName == "") { errorMessage = errorMessage + ("Please enter Part Name."); }
        //If the part inventory is a negative number
        if (partInventory < 0) { errorMessage = errorMessage + ("Please use a number greater than 0."); }
        //If the part price is a negative number
        if (partPrice < 0) { errorMessage = errorMessage + ("Please use a number greater than 0."); }
        //If the minimum number is greater than the maximum number
        if (partMin > partMax) { errorMessage = errorMessage + ("Please use a number less than the Max Value"); }
        //If the part inventory is not inbetweem min and max
        if (partInventory < partMin || partInventory > partMax) { errorMessage = errorMessage + ("Please use a number inbetween the Min and Max value"); }
        return errorMessage;
    }
}