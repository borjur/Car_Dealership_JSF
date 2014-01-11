package Car_Dealership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Boris Jurosevic
 */
@ManagedBean
public class SalesRecordBean 
{  
    // Member Variables ////////////////////////////////////////////////////////////////////////////////////////////////
    private int recordNumber;
    private int sellerID;
    private String sellerFirstName;
    private String sellerLastName;
    private int clientID;
    private String clientFirstName;
    private String clientLastName;
    private String carVIN;
    private String date;
    private int amount;
    
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;
    
    // Construction ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Creates a new instance of SalesRecordBean
     */
    public SalesRecordBean() {
        
    }
    
    // Access Methods //////////////////////////////////////////////////////////////////////////////////////////////////
    
    public int getRecordNumber()
    {
      return recordNumber;
    }
    public void setRecordNumber( int recordNumber )
    {
      this.recordNumber = recordNumber;
    }
    
    public int getSellerID()
    {
      return sellerID;
    }
    public void setSellerID( int sellerID )
    {
      this.sellerID = sellerID;
    }
    
    public int getClientID()
    {
      return clientID;
    }
    public void setClientID( int clientID )
    {
      this.clientID = clientID;
    }
    
    public String getSellerFirstName() {
        return sellerFirstName;
    }
    public void setSellerFirstName(String sellerFirstName) {
        this.sellerFirstName = sellerFirstName;
    }

    public String getSellerLastName() {
        return sellerLastName;
    }
    public void setSellerLastName(String sellerLastName) {
        this.sellerLastName = sellerLastName;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }
    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }
    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }
    
    public String getCarVIN()
    {
      return carVIN;
    }
    public void setCarVIN( String carVIN )
    {
      this.carVIN = carVIN;
    }
    
    public String getDate()
    {
      return date;
    }
    public void setDate( String date )
    {
      this.date = date;
    }
    
    public int getAmount()
    {
      return amount;
    }
    public void setAmount( int amount )
    {
      this.amount = amount;
    }
    
    public String save() throws SQLException
    {
      if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
      Connection connection = dataSource.getConnection();
      if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

      try
      {
         PreparedStatement addEntry = connection.prepareStatement(
                 "INSERT INTO RECORDS (SellerID,ClientID,CarVIN,Date,Amount) "
                 + "VALUES ( ?, ?, ?, ?, ? )" );

         // specify the PreparedStatement's arguments
         addEntry.setInt(1, getSellerID());
         addEntry.setInt(2, getClientID());
         addEntry.setString(3, getCarVIN());
         addEntry.setString(4, getDate());
         addEntry.setInt(5, getAmount());

         addEntry.executeUpdate();
         
         FacesContext context = FacesContext.getCurrentInstance();           
         context.addMessage(null, new FacesMessage("Successful", "New Sales Secord created successfully"));
         
         PreparedStatement updateEntry = connection.prepareStatement(
                 "UPDATE CARS "
                 + "SET SOLD=true, PURCHASERID=? "
                 + "WHERE VIN=?"
                 );
         
         updateEntry.setInt(1, getClientID());
         updateEntry.setString(2, getCarVIN());
         
         updateEntry.executeUpdate();
                    
         context.addMessage(null, new FacesMessage("Successful", "Car Sold successfully"));
         
         return "records_sales"; // Go back to the Index Page
      }
      finally
      {
         connection.close();
      }
    }
    
    public String updateSalesRecord(Connection conn) throws SQLException
    {
         PreparedStatement updateEntry = conn.prepareStatement(
                 "UPDATE Records "
                 + "SET "
                 + "SellerID=?, "
                 + "ClientID=?, "
                 + "CarVIN=?, "
                 + "Date=?, "
                 + "Amount=? "
                 + "WHERE recordnumber=? " );

         // specify the PreparedStatement's arguments
         updateEntry.setInt(1, getSellerID());
         updateEntry.setInt(2, getClientID());
         updateEntry.setString(3, getCarVIN());
         updateEntry.setString(4, getDate());
         updateEntry.setInt(5, getAmount());
         updateEntry.setInt(6, getRecordNumber());

         updateEntry.executeUpdate();
         
         FacesContext context = FacesContext.getCurrentInstance();
         context.addMessage(null, new FacesMessage("Successful", "Sales Record edited successfully"));
         
         return "records_sales";
    }

    public ResultSet getRecords() throws SQLException
    {
        if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

        try
        {
           PreparedStatement getSalesRecords = connection.prepareStatement(
              "SELECT " +
                "r.RecordNumber, " +
                "s.FIRSTNAME as seller_firstname, " +
                "s.LASTNAME as seller_lastname, " +
                "c.FIRSTNAME as client_firstname, " +
                "c.LASTNAME as client_lastname, " +
                "r.CarVIN, " +
                "r.Date, " +
                "r.Amount " +
                "FROM Records r " +
                "INNER JOIN Sellers s ON r.SellerID=s.SellerID " +
                "INNER JOIN Clients c ON r.ClientID=c.ClientID " +
                "ORDER BY r.RecordNumber " );          

           CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
           rowSet.populate( getSalesRecords.executeQuery() );
           return rowSet; 
        }
        finally
        {
           connection.close();
        }
    }
}
