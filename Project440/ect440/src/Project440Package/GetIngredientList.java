/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project440Package;

import com.mysql.jdbc.IterateBlock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Simant
 */
public class GetIngredientList {
    
    
    public ArrayList<String> getIngredientList() throws SQLException
    {
        
        
        Connection con;
        Statement stmt;
        ResultSet rsIngredient;
        //connection to database
        String Uname="root";
        String Upass="engineering";
        //connection string
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String SQL= "SELECT * FROM project440.INGREDIENTHEADER ORDER BY INGREDIENTID";
        rsIngredient = stmt.executeQuery(SQL);
        
        int countrows=0;
        rsIngredient.beforeFirst();
        while(rsIngredient.next())
        {
            countrows++;
        }
                
        rsIngredient.beforeFirst();
        
        String CurrentIngredientList[]=new String[countrows];
        
        int rowindex=0;
        ArrayList<String> IngredientList=new ArrayList<>();
        
        while(rsIngredient.next())
        {
            IngredientList.add(rsIngredient.getString("IngredientName"));
            CurrentIngredientList[rowindex]=rsIngredient.getString("IngredientName");
            //System.out.println("Name:"+CurrentIngredientList[rowindex]);
            rowindex++;
        }
        
        
        
        Iterator list=IngredientList.listIterator();
        
        while(list.hasNext())
        {
            System.out.println("List element:"+list.next());
        }
        return IngredientList;
    }
    
}
