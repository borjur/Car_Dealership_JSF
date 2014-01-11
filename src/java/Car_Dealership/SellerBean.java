package Car_Dealership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Boris Jurosevic
 */
@ManagedBean( name="sellerBean" )
public class SellerBean
{
    // Member Variables ////////////////////////////////////////////////////////////////////////////////////////////////
    private int sellerID;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String birthDate;
    private String email;
        
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;
    
    // Access Methods //////////////////////////////////////////////////////////////////////////////////////////////////
    
    public int getSellerID()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      sellerID = request.getParameter("sellerID");
      return sellerID;
   }
   public void setSellerID( int sellerID )
   {
      this.sellerID = sellerID;
   }
    
    public String getFirstName()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      firstName = request.getParameter("firstName");
      return firstName;
   }
   public void setFirstName( String firstName )
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      lastName = request.getParameter("lastName");
      return lastName;
   }
   public void setLastName( String lastName )
   {
      this.lastName = lastName;
   }

   public String getStreet()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      street = request.getParameter("street");
      return street;
   }
   public void setStreet( String street )
   {
      this.street = street;
   }

   public String getCity()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      city = request.getParameter("city");
      return city;
   }
   public void setCity( String city )
   {
      this.city = city;
   }

   public String getState()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      state = request.getParameter("state");
      return state;
   }
   public void setState( String state )
   {
      this.state = state;
   }

   public String getZipcode()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      zipcode = request.getParameter("zipcode");
      return zipcode;
   }
   public void setZipcode( String zipcode )
   {
      this.zipcode = zipcode;
   }
   
   public String getBirthDate()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      birthDate = request.getParameter("birthDate");
      return birthDate;
   }
   public void setBirthDate( String birthDate )
   {
      this.birthDate = birthDate;
   }
   
   public String getEmail()
   {
//      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//      email = request.getParameter("email");
      return email;
   }
   public void setEmail( String email )
   {
      this.email = email;
   }
   
   public String save() throws SQLException
   {
      if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );

      Connection connection = dataSource.getConnection();
     
      if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

      try
      {
         PreparedStatement addEntry = connection.prepareStatement( "INSERT INTO "
                 + "Sellers (FirstName,LastName,Street,City,State,Zip,BirthDate,Email) "
                 + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )" );

         // specify the PreparedStatement's arguments
         addEntry.setString( 1, getFirstName() );
         addEntry.setString( 2, getLastName() );
         addEntry.setString( 3, getStreet() );
         addEntry.setString( 4, getCity() );
         addEntry.setString( 5, getState() );
         addEntry.setString( 6, getZipcode() );
         addEntry.setString( 7, getBirthDate());
         addEntry.setString( 8, getEmail());

         addEntry.executeUpdate();
         
         FacesContext context = FacesContext.getCurrentInstance();           
         context.addMessage(null, new FacesMessage("Successful", "New Seller was created successfully"));
         
         return "sellerList"; // Go back to the Index Page
      }
      finally
      {
         connection.close();
      }
   }
   
   public String updateSeller(Connection conn) throws SQLException
   {
         PreparedStatement addEntry = conn.prepareStatement( 
                 "UPDATE Sellers "
                 + "SET "
                    + "FirstName=?, "
                    + "LastName=?, "
                    + "Street=?, "
                    + "City=?, "
                    + "State=?, "
                    + "Zip=?, "
                    + "BirthDate=?, "
                    + "Email=? "
                 + "WHERE SellerID=? " );

         // specify the PreparedStatement's arguments
         addEntry.setString( 1, getFirstName() );
         addEntry.setString( 2, getLastName() );
         addEntry.setString( 3, getStreet() );
         addEntry.setString( 4, getCity() );
         addEntry.setString( 5, getState() );
         addEntry.setString( 6, getZipcode() );
         addEntry.setString( 7, getBirthDate());
         addEntry.setString( 8, getEmail());
         addEntry.setInt(9, getSellerID());

         addEntry.executeUpdate();
         
         FacesContext context = FacesContext.getCurrentInstance();           
         context.addMessage(null, new FacesMessage("Successful", "Seller edited successfully"));
         
         return "sellerList";
   }
   
   public ResultSet getSellers() throws SQLException
   {
      if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );

      Connection connection = dataSource.getConnection();

      if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

      try
      {
         PreparedStatement getSellers = connection.prepareStatement(
            "SELECT SELLERID, FirstName, LastName, Street, City, State, Zip, BirthDate, Email " +
            "FROM Sellers ORDER BY SELLERID" );

         CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
         rowSet.populate( getSellers.executeQuery() );
         return rowSet; 
      }
      finally
      {
         connection.close();
      }
   }
   
   public List<SelectItem> getSellerList() throws SQLException
   {
        if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

        ArrayList<SelectItem> arrayList = new ArrayList<SelectItem>();
        
        try
        {
            Statement getSellerList = connection.createStatement();

            ResultSet resultSet = getSellerList.executeQuery("SELECT SELLERID, FIRSTNAME, LASTNAME FROM SELLERS" );
            
            while (resultSet.next()) 
            {  
                arrayList.add(new SelectItem(
                        resultSet.getString("SELLERID"),
                        resultSet.getString("FIRSTNAME") + " " + resultSet.getString("LASTNAME")
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
