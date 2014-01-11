package Car_Dealership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Agape
 */
@ManagedBean( name="carBean" )
public class CarBean
{
    // Member Variables ////////////////////////////////////////////////////////////////////////////////////////////////
    private String VIN;
    private String make;
    private String model;
    private String modelYear;
    private int mileage;
    private String color;
    private String motor;
    private String city;
    private boolean sold = false;
    private int purchaserID;
    private String purchaser_firstName;
    private String purchaser_lastName;
    
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;
    
    // Access Methods //////////////////////////////////////////////////////////////////////////////////////////////////
    
    public String getVIN()
    {
      return VIN;
    }
    public void setVIN( String VIN )
    {
      this.VIN = VIN;
    }
    
    public String getMake()
    {
      return make;
    }
    public void setMake( String make )
    {
      this.make = make;
    }
    
    public String getModel()
    {
      return model;
    }
    public void setModel( String model )
    {
      this.model = model;
    }
    
    public String getModelYear()
    {
      return modelYear;
    }
    public void setModelYear( String modelYear )
    {
      this.modelYear = modelYear;
    }
    
    public int getMileage()
    {
      return mileage;
    }
    public void setMileage( int mileage )
    {
      this.mileage = mileage;
    }
    
    public String getColor()
    {
      return color;
    }
    public void setColor( String color )
    {
      this.color = color;
    }
    
    public String getMotor()
    {
      return motor;
    }
    public void setMotor( String motor )
    {
      this.motor = motor;
    }
    
    public String getCity()
    {
      return city;
    }
    public void setCity( String city )
    {
      this.city = city;
    }
    
    public boolean getSold()
    {
      return sold;
    }
    public void setSold( boolean sold )
    {
      this.sold = sold;
    }
    
    public int getPurchaserID()
    {
      return purchaserID;
    }
    public void setPurchaserID( int purchaserID )
    {
      this.purchaserID = purchaserID;
    }
    
    public String getPurchaser_firstName()
    {
        return purchaser_firstName;
    }
    public void setPurchaser_firstName(String purchaser_firstName)
    {
        this.purchaser_firstName = purchaser_firstName;
    }

    public String getPurchaser_lastName()
    {
        return purchaser_lastName;
    }
    public void setPurchaser_lastName(String purchaser_lastName)
    {
        this.purchaser_lastName = purchaser_lastName;
    }
    
   public String save() throws SQLException
   {
      if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
      Connection connection = dataSource.getConnection();
      if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

      try
      {
         PreparedStatement addEntry = connection.prepareStatement(
                 "INSERT INTO Cars (VIN,Make,Model,ModelYear,Mileage,Color,Motor,City,Sold,PurchaserID) "
                 + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" );

         // specify the PreparedStatement's arguments
         addEntry.setString( 1, getVIN());
         addEntry.setString( 2, getMake());
         addEntry.setString( 3, getModel());
         addEntry.setString( 4, getModelYear());
         addEntry.setInt(5, getMileage());
         addEntry.setString( 6, getColor());
         addEntry.setString( 7, getMotor());
         addEntry.setString( 8, getCity());
         addEntry.setBoolean(9, getSold());
         if(getSold()) {
             addEntry.setInt(10, getPurchaserID());
         } else {
             addEntry.setNull(10, Types.INTEGER);
         }

         addEntry.executeUpdate();
         
         FacesContext context = FacesContext.getCurrentInstance();           
         context.addMessage(null, new FacesMessage("Successful", "New Car was created successfully"));
         
         return "carList"; // Go back to the Index Page
      }
      finally
      {
         connection.close();
      }
   }
   
   
   public String updateCar(Connection conn) throws SQLException
   {
        PreparedStatement addEntry = conn.prepareStatement(
                "UPDATE Cars "
                + "SET "
                    + "Make=?, "
                    + "Model=?, "
                    + "ModelYear=?, "
                    + "Mileage=?, "
                    + "Color=?, "
                    + "Motor=?, "
                    + "City=?, "
                    + "Sold=?, "
                    + "PurchaserID=? "
                + "WHERE VIN=? " );       
        
        // specify the PreparedStatement's arguments 
        addEntry.setString( 1, getMake());
        addEntry.setString( 2, getModel());
        addEntry.setString( 3, getModelYear());
        addEntry.setInt(4, getMileage());
        addEntry.setString( 5, getColor());
        addEntry.setString( 6, getMotor());
        addEntry.setString( 7, getCity());
        addEntry.setBoolean(8, getSold());
        if(getSold()) {
            addEntry.setInt(9, getPurchaserID());
        } else {
            addEntry.setNull(9, Types.INTEGER);
        }
        addEntry.setString( 10, getVIN());

        addEntry.executeUpdate();

        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Successful", "Car was updated successfully"));

        return "carList";
   }
   
   public ResultSet getCars() throws SQLException
   {
      if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
      Connection connection = dataSource.getConnection();
      if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

      try
      {
         PreparedStatement getCars = connection.prepareStatement(
            "SELECT "
                 + "c.VIN, "
                 + "c.Make, "
                 + "c.Model, "
                 + "c.ModelYear, "
                 + "c.Mileage, "
                 + "c.Color, "
                 + "c.Motor, "
                 + "c.City, "
                 + "c.Sold, "
                 + "cl.FirstName as client_firstname, "
                 + "cl.LastName as client_lastname "
            + "FROM Cars c "
            + "LEFT JOIN Clients cl ON c.PurchaserID=cl.ClientID "
            + "ORDER BY c.VIN " );

         CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
         rowSet.populate( getCars.executeQuery() );
         return rowSet; 
      }
      finally
      {
         connection.close();
      }
   }
   
   public List<SelectItem> getUnsoldCarList() throws SQLException
   {
        if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

        ArrayList<SelectItem> arrayList = new ArrayList<SelectItem>();
        
        try
        {
            Statement getCarList = connection.createStatement();

            ResultSet resultSet = getCarList.executeQuery(
                    "SELECT VIN, MAKE, MODEL, MODELYEAR "
                    + "FROM CARS "
                    + "WHERE SOLD<>true "   // <> is not equal to
                    + "ORDER BY MAKE" );
            
            while (resultSet.next()) 
            {  
                arrayList.add(new SelectItem(
                        resultSet.getString("VIN"),
                        resultSet.getString("MAKE") 
                        + " - " + resultSet.getString("MODEL")
                        + " - " + resultSet.getString("MODELYEAR")
                        + " - " + resultSet.getString("VIN")
                        )); 
            }
            
            return arrayList; 
        }
        finally
        {
            connection.close();
        }
    }
   
}
