package Car_Dealership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * @author Christopher Nash
 */
@ManagedBean
public class SalesRecordTableBean 
{
    private List<SalesRecordBean> salesRecordsList = new ArrayList<SalesRecordBean>();
    private int sellersID = 1;
    
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;
    
    public int getSellersID()
    {
        return sellersID;
    }
    public void setSellersID(int id)
    {
        this.sellersID = id;
    }
    
    public List<SalesRecordBean> getSalesRecords() throws SQLException
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
            
            salesRecordsList.clear();

            while(rowSet.next())
            {
                SalesRecordBean record = new SalesRecordBean();
                record.setRecordNumber(rowSet.getInt("RecordNumber"));
                record.setSellerFirstName(rowSet.getString("seller_firstname"));
                record.setSellerLastName(rowSet.getString("seller_lastname"));
                record.setClientFirstName(rowSet.getString("client_firstname"));
                record.setClientLastName(rowSet.getString("client_lastname"));
                record.setCarVIN(rowSet.getString("CarVIN"));
                record.setDate(rowSet.getString("Date"));
                record.setAmount(rowSet.getInt("Amount"));
                salesRecordsList.add(record);
            } 
            return salesRecordsList; 
        }
        finally
        {
           connection.close();
        }
    }
    
    public List<SalesRecordBean> getSalesRecordsBySeller() throws SQLException
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
                "WHERE r.SellerID=? " +
                "ORDER BY r.RecordNumber " );          

           CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();           
           getSalesRecords.setInt(1, getSellersID());           
           rowSet.populate( getSalesRecords.executeQuery() );
           
           salesRecordsList.clear();
           
           while(rowSet.next())
            {
                SalesRecordBean record = new SalesRecordBean();
                record.setRecordNumber(rowSet.getInt("RecordNumber"));
                record.setSellerFirstName(rowSet.getString("seller_firstname"));
                record.setSellerLastName(rowSet.getString("seller_lastname"));
                record.setClientFirstName(rowSet.getString("client_firstname"));
                record.setClientLastName(rowSet.getString("client_lastname"));
                record.setCarVIN(rowSet.getString("CarVIN"));
                record.setDate(rowSet.getString("Date"));
                record.setAmount(rowSet.getInt("Amount"));
                salesRecordsList.add(record);
            } 
           
           return salesRecordsList; 
        }
        finally
        {
           connection.close();
        }
    }
    
    
    public void onEdit(RowEditEvent event) throws SQLException 
    {  
        if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

        SalesRecordBean record = (SalesRecordBean) event.getObject();
        try
        { 
            record.updateSalesRecord(connection); 
        }
        finally
        {
           connection.close();
        }   
        
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Successful", "Sales Record was edited successfully"));   
    }  
      
    public void onCancel(RowEditEvent event) 
    {  
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Cancelled", "Sales Record edit was cancelled")); 
    }
    
}
