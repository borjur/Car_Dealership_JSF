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
@ManagedBean( name="carTableBean" )
public class CarTableBean 
{
    private List<CarBean> carList = new ArrayList<CarBean>();    
    
    @Resource(name="jdbc/carDealershipJavaDB")
    DataSource dataSource;
    
    public List<CarBean> getCarsList() throws SQLException
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
                   + "cl.ClientID, "
                   + "cl.FirstName as client_firstname, "
                   + "cl.LastName as client_lastname "
              + "FROM Cars c "
              + "LEFT JOIN Clients cl ON c.PurchaserID=cl.ClientID "
              + "ORDER BY c.VIN " );

           CachedRowSet rowSet = new com.sun.rowset.CachedRowSetImpl();
           rowSet.populate( getCars.executeQuery() );
           
//           carList.clear();
   
            while(rowSet.next())
            {
                CarBean car = new CarBean();
                car.setVIN(rowSet.getString("VIN"));
                car.setMake(rowSet.getString("Make"));
                car.setModel(rowSet.getString("Model"));
                car.setModelYear(rowSet.getString("ModelYear"));
                car.setMileage(rowSet.getInt("Mileage"));
                car.setColor(rowSet.getString("Color"));
                car.setMotor(rowSet.getString("Motor"));
                car.setCity(rowSet.getString("City"));
                car.setSold(rowSet.getBoolean("Sold"));
                car.setPurchaserID(rowSet.getInt("ClientID"));
                car.setPurchaser_firstName(rowSet.getString("client_firstname"));
                car.setPurchaser_lastName(rowSet.getString("client_lastname"));
                carList.add(car);
            }
           
           return carList; 
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

        CarBean car = (CarBean) event.getObject();
        try
        { 
            car.updateCar(connection); 
        }
        finally
        {
           connection.close();
        }   
        
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Successful", "Car was edited successfully"));   
    }  

    public void onCancel(RowEditEvent event) 
    {  
        FacesContext context = FacesContext.getCurrentInstance();           
        context.addMessage(null, new FacesMessage("Edit Cancelled", "Car was edited was cancelled")); 
    }
    
}
