package Car_Dealership;

/**
 *
 * @author Boris Jurosevic
 * @author Christopher Nash
 */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

@ManagedBean(name = "userData", eager = true)
//@SessionScoped
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private boolean active;
    
    private List systemUserList = new ArrayList();
    
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean getActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public String login() {
        return "result";
    }
   
    public List getSystemUsersList() throws SQLException
    {
        if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );
        
        try
        {
            PreparedStatement getSystemUserList = connection.prepareStatement(
               "SELECT " 
                + "USERNAME, " 
                + "PASSWORD, "
                + "ACTIVE " 
                + "FROM USERS "
                + "ORDER BY USERNAME" );          

            CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( getSystemUserList.executeQuery() );
            
            systemUserList.clear();

            while(rowSet.next())
            {
                UserData record = new UserData();
                record.setName(rowSet.getString("USERNAME"));
                record.setPassword(rowSet.getString("PASSWORD"));
                record.setActive(rowSet.getBoolean("ACTIVE"));
                systemUserList.add(record);
            } 
            return systemUserList; 
        }
        finally
        {
           connection.close();
        }
    }
    
    public String create() throws SQLException
    {
        if ( dataSource == null ) throw new SQLException( "Unable to obtain DataSource" );
        Connection connection = dataSource.getConnection();
        if ( connection == null ) throw new SQLException( "Unable to connect to DataSource" );

        try
        {
           PreparedStatement addEntry = connection.prepareStatement(
                   "INSERT INTO USERS (USERNAME,PASSWORD,ACTIVE) " 
                    + "VALUES (?,?,?)");

           // specify the PreparedStatement's arguments
           addEntry.setString( 1, getName());
           addEntry.setString( 2, getPassword());
           addEntry.setBoolean( 3, true); // Default all new users to active

           addEntry.executeUpdate();

           FacesContext context = FacesContext.getCurrentInstance();           
           context.addMessage(null, new FacesMessage("Successful", "New System User was created successfully"));

           return "userList";
        }
        finally
        {
           connection.close();
        }
    }
}
