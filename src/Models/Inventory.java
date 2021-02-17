package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory
{
    //Part Variables
    private static ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private static int partIDCount = 0;

    //Product Variables
    private static ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private static int productIDCount = 0;

    public Inventory()
    {
    }

    // Add, Update, Delete Part
    public static ObservableList<Part> getPartInventory(){ return partInventory; }
    public static void addPart(Part part){ partInventory.add(part); }
    public static void updatePart(int index, Part part){ partInventory.set(index, part); }
    public static void deletePart(Part part){ partInventory.remove(part); }

    // Add, Update, Delete Product
    public static ObservableList<Product> getProductInventory(){ return productInventory; }
    public static void addProduct(Product product){ productInventory.add(product); }
    public static void updateProduct(int index, Product product){ productInventory.set(index, product); }
    public static void deleteProduct(Product product){ productInventory.remove(product); }


    public static int getPartIdCount()
    {
        partIDCount++;
        return partIDCount;
    }
    public static int getProductIdCount()
    {
        productIDCount++;
        return productIDCount;
    }

    //Searching Methods
    public static int lookupPart(String searchTerm)
    {
        boolean isFound = false;
        int index = 0;

        if (isInteger(searchTerm))
        {
            for (int i = 0; i < partInventory.size(); i++)
            {
                if (Integer.parseInt(searchTerm) == partInventory.get(i).getPartID())
                {
                    index = i;
                    isFound = true;
                }
            }
        }
        else
        {
            for (int i = 0; i < partInventory.size(); i++)
            {
                if (searchTerm.equals(partInventory.get(i).getPartName()))
                {
                    index = i;
                    isFound = true;
                }
            }
        }

        if (isFound == true) { return index; }
        else
        {
            System.out.println("No Parts Found");
            return -1;
        }
    }



    public static int lookupProduct(String searchTerm)
    {
        boolean isFound = false;
        int index = 0;

        if(isInteger(searchTerm))
        {
            for(int i=0; i<productInventory.size(); i++)
            {
                if(Integer.parseInt(searchTerm) == productInventory.get(i).getProductID())
                {
                    index = i;
                    isFound = true;
                }
            }
        }
        else
        {
            for(int i=0; i<productInventory.size(); i++)
            {
                if(searchTerm.equals(productInventory.get(i).getProductName()))
                {
                    index = i;
                    isFound  = true;
                }
            }
        }

        if(isFound == true) { return index; }
        else
        {
            System.out.println("No products found.");
            return -1;
        }
    }


    //Validation Methods
    public static boolean partDeleteValidation(Part part)
    {
        boolean isFound = false;

        for (int i=0; i<productInventory.size(); i++)
        {
            if(productInventory.get(i).getProductParts().contains(part))
            {
                isFound = true;
            }
        }
        return isFound;
    }

    //Checks if a value is a integer
    public static boolean isInteger(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}