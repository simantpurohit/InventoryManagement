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
public class GetVendorList {
    
    public ArrayList<String> getVendorList() throws SQLException
    { 
        Connection con;
        Statement stmt;
        ResultSet rsVendor;
        //connection to database
        String Uname="root";
        String Upass="engineering";
        //connection string
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String SQL= "SELECT * FROM project440.VENDORHEADER ORDER BY VENDORID";
        rsVendor = stmt.executeQuery(SQL);
        
        int countrows=0;
        rsVendor.beforeFirst();
        while(rsVendor.next())
        {
            countrows++;
        }
                
        rsVendor.beforeFirst();
        
        String CurrentVendorList[]=new String[countrows];
        
        int rowindex=0;
        ArrayList<String> VendorList=new ArrayList<>();
        
        while(rsVendor.next())
        {
            VendorList.add(rsVendor.getString("VendorName"));
            CurrentVendorList[rowindex]=rsVendor.getString("VendorName");
            //System.out.println("Name:"+CurrentVendorList[rowindex]);
            rowindex++;
        }
        
        
        
        Iterator list=VendorList.listIterator();
        
        while(list.hasNext())
        {
            System.out.println("List element:"+list.next());
        }
        return VendorList;
    }
    
}
