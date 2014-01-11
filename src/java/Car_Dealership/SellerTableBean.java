package Car_Dealership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Boris Jurosevic
 */
@ManagedBean
public class SellerTableBean 
{
    private List<SellerBean> sellerList = new ArrayList<SellerBean>();    
    
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;
    
    public List<SellerBean> getSellersList() throws SQLException
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
            
//            sellerList.clear();

            while(rowSet.next())
              {
                  SellerBean seller = new SellerBean();
                  seller.setSellerID(rowSet.getInt("SELLERID"));
                  seller.setFirstName(rowSet.getString("FIRSTNAME"));
                  seller.setLastName(rowSet.getString("LASTNAME"));
                  seller.setStreet(rowSet.getString("STREET"));
                  seller.setCity(rowSet.getString("CITY"));
                  seller.setState(rowSet.getString("STATE"));
                  seller.setZipcode(rowSet.getString("ZIP"));
                  seller.setBirthDate(rowSet.getString("BIRTHDATE"));
                  seller.setEmail(rowSet.getString("EMAIL"));
                  sellerList.add(seller);
              }
            
           return sellerList; 
        }
        finally
        {
           connection.close();
        }
        
    }
    
    public void onEdit(RowEditEvent event) throws SQLException 
    {  
        if (dataSource == null) throw new SQLException("Unable to obtain DataSource");
        Connection connection = dataSource.getConnection();
        if (connection == null) throw new SQLException("Unable to connect to DataSource");
        
        SellerBean seller = (SellerBean) event.getObject();
        try
        { 
            seller.updateSeller(connection);
        }
        finally
        {
            connection.close();
        }
        
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Successful", "Seller was edited successfully"));   
    }  
      
    public void onCancel(RowEditEvent event) 
    {  
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Cancelled", "Seller edit was cancelled")); 
    }
    
}
