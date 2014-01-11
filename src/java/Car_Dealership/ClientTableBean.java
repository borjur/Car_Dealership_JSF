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
 * @author Christopher Nash
 */
@ManagedBean
public class ClientTableBean 
{
    private List<ClientBean> clientList = new ArrayList<ClientBean>();    
    
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;
    
    public List<ClientBean> getClientsList() throws SQLException
    {
        if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

        try
        {
            PreparedStatement getClients = connection.prepareStatement(
               "SELECT CLIENTID, FIRSTNAME, LASTNAME, STREET, CITY, STATE, ZIP, BIRTHDATE, EMAIL " +
               "FROM Clients ORDER BY ClientID" );

            CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( getClients.executeQuery() );
            
//            clientList.clear();

             while(rowSet.next())
               {
                   ClientBean client = new ClientBean();
                   client.setClientID(rowSet.getInt("CLIENTID"));
                   client.setFirstName(rowSet.getString("FIRSTNAME"));
                   client.setLastName(rowSet.getString("LASTNAME"));
                   client.setStreet(rowSet.getString("STREET"));
                   client.setCity(rowSet.getString("CITY"));
                   client.setState(rowSet.getString("STATE"));
                   client.setZipcode(rowSet.getString("ZIP"));
                   client.setBirthDate(rowSet.getString("BIRTHDATE"));
                   client.setEmail(rowSet.getString("EMAIL"));
                   clientList.add(client);
               }

            return clientList; 
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
        
        ClientBean client = (ClientBean) event.getObject();
        try
        { 
            client.updateClient(connection);
        }
        finally
        {
            connection.close();
        }
        
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Successful", "Client was edited successfully"));   
    }  
      
    public void onCancel(RowEditEvent event) 
    {
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Cancelled", "Client edit was cancelled")); 
    }
   
}
