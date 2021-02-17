package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutSourcePart extends Part
{

    private final StringProperty companyName;

    public OutSourcePart()
    {
        super();
        companyName = new SimpleStringProperty();
    }

    //Getter
    public String getCompanyName(){ return this.companyName.get(); }
    //Setter
    public void setCompanyName(String companyName){
        this.companyName.set(companyName);
    }
}