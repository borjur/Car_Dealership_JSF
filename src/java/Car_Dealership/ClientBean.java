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
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Boris Jurosevic
 */
@ManagedBean(name = "clientBean")
public class ClientBean
{
    // Member Variables ////////////////////////////////////////////////////////////////////////////////////////////////

    private int clientID;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String birthDate;
    private String email;
    
    @Resource(name = "jdbc/carDealershipJavaDB")
    DataSource dataSource;

    // Access Methods //////////////////////////////////////////////////////////////////////////////////////////////////
    public int getClientID()
    {
        return clientID;
    }

    public void setClientID(int clientID)
    {
        this.clientID = clientID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String save() throws SQLException
    {
        if (dataSource == null)
        {
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection connection = dataSource.getConnection();
        if (connection == null)
        {
            throw new SQLException("Unable to connect to DataSource");
        }

        try
        {
            PreparedStatement addEntry = connection.prepareStatement("INSERT INTO "
                    + "Clients (FirstName,LastName,Street,City,State,Zip,BirthDate,Email) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )");

            // specify the PreparedStatement's arguments
            addEntry.setString(1, getFirstName());
            addEntry.setString(2, getLastName());
            addEntry.setString(3, getStreet());
            addEntry.setString(4, getCity());
            addEntry.setString(5, getState());
            addEntry.setString(6, getZipcode());
            addEntry.setString(7, getBirthDate());
            addEntry.setString(8, getEmail());

            addEntry.executeUpdate();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "New Client was created successfully"));

            return "clientList";
        }
        finally
        {
            connection.close();
        }
    }
    
    public String updateClient(Connection conn) throws SQLException
    {
        PreparedStatement updateEntry = conn.prepareStatement(
                "UPDATE Clients "
                + "SET "
                    + "FirstName=?, "
                    + "LastName=?, "
                    + "Street=?, "
                    + "City=?, "
                    + "State=?, "
                    + "Zip=?, "
                    + "BirthDate=?, "
                    + "Email=? "
                + "WHERE CLIENTID=? ");

        // specify the PreparedStatement's arguments
        updateEntry.setString(1, getFirstName());
        updateEntry.setString(2, getLastName());
        updateEntry.setString(3, getStreet());
        updateEntry.setString(4, getCity());
        updateEntry.setString(5, getState());
        updateEntry.setString(6, getZipcode());
        updateEntry.setString(7, getBirthDate());
        updateEntry.setString(8, getEmail());
        updateEntry.setInt(9, getClientID());            

        updateEntry.executeUpdate();
        
        System.out.println(getClientID());

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Client was updated successfully"));

        return "clientList";
    }

    public ResultSet getClients() throws SQLException
    {
        if (dataSource == null)
        {
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection connection = dataSource.getConnection();
        if (connection == null)
        {
            throw new SQLException("Unable to connect to DataSource");
        }

        try
        {
            PreparedStatement getClients = connection.prepareStatement(
                    "SELECT CLIENTID, FIRSTNAME, LASTNAME, STREET, CITY, STATE, ZIP, BIRTHDATE, EMAIL "
                    + "FROM Clients ORDER BY ClientID");

            CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate(getClients.executeQuery());
            return rowSet;
        }
        finally
        {
            connection.close();
        }
    }

    public List<SelectItem> getClientList() throws SQLException
    {
        if (dataSource == null)
        {
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection connection = dataSource.getConnection();
        if (connection == null)
        {
            throw new SQLException("Unable to connect to DataSource");
        }

        ArrayList<SelectItem> arrayList = new ArrayList<SelectItem>();

        try
        {
            Statement getClientList = connection.createStatement();

            ResultSet resultSet = getClientList.executeQuery("SELECT CLIENTID, FIRSTNAME, LASTNAME FROM CLIENTS");

            while (resultSet.next())
            {
                arrayList.add(new SelectItem(
                        resultSet.getString("CLIENTID"),
                        resultSet.getString("FIRSTNAME") + " " + resultSet.getString("LASTNAME")));
            }

            return arrayList;
        }
        finally
        {
            connection.close();
        }
    }
    
    public String deleteClient() throws SQLException
    {
        if (dataSource == null)
        {
            throw new SQLException("Unable to obtain DataSource");
        }
        Connection connection = dataSource.getConnection();
        if (connection == null)
        {
            throw new SQLException("Unable to connect to DataSource");
        }

        try
        {
            Statement st = connection.createStatement();
            String sql = "DELETE FROM Clients WHERE ClientID = '1'";
            int delete = st.executeUpdate(sql);
            if (delete == 1)
            {
                System.out.println("Row is deleted.");
            }
            else
            {
                System.out.println("Row is not deleted.");
            }

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "New Client was deleted successfully"));

            return "clientList";
        }
        finally
        {
            connection.close();
        }
    }
}
