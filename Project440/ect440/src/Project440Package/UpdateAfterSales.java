/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project440Package;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Simant
 */
public class UpdateAfterSales extends javax.swing.JFrame {
    
    Connection con;
    Statement stmt,stmtRecipe,stmtDeduct;
    ResultSet rs,rsRecipe;
    DefaultListModel RecipeListModel;
    String CurrentRecipeList[];
    int CurrentRecipeID[];
    DefaultListModel SoldRecipeModel=new DefaultListModel();
    DefaultListModel SoldRecipeQuantityModel=new DefaultListModel();
    

    /**
     * Creates new form UpdateAfterSales
     */
    public UpdateAfterSales() {
        initComponents();
        
    }
    
    public void GetRecipeList() throws SQLException
    {
        
        String Uname="root";
        String Upass="engineering";
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmtRecipe = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmtDeduct=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        //String SQL = "SELECT * FROM project440.RecipeHeader ORDER BY RECIPEID";
        String SQL2= "SELECT * FROM project440.recipeheader ORDER BY RecipeID";
        //rs = stmt.executeQuery( SQL );
        rsRecipe=stmtRecipe.executeQuery(SQL2);
        
        RecipeListModel=new DefaultListModel();
        
        int countrows=0;
        
        rsRecipe.beforeFirst();
        
        while(rsRecipe.next())
        {
            countrows++;
        }
        
        System.out.println("No of rows:"+countrows);
        
        rsRecipe.beforeFirst();
        
        CurrentRecipeList=new String[countrows];
        CurrentRecipeID=new int[countrows];
        
        int rowindex=0;
        
        while(rsRecipe.next())
        {
            CurrentRecipeList[rowindex]=rsRecipe.getString("RecipeName");
            CurrentRecipeID[rowindex]=Integer.parseInt(rsRecipe.getString("RecipeID"));
            System.out.println("ID:"+CurrentRecipeID[rowindex]);
            RecipeListModel.addElement(CurrentRecipeList[rowindex]);
            rowindex++;
        }
    }
    
    public void addRecipeToList()
    {
        String selectedRecipe=RecipeListModel.getElementAt(RecipeListBox.getSelectedIndex()).toString();
        String quantityString=quantityField.getText();
        int quantity;
        try
        {
            if(quantityString.length()>3)
            {
                NumberFormatException error=new NumberFormatException();
                throw error;
            }
            else {
                quantity=Integer.parseInt(quantityString);
            }
        }
        
        catch(NumberFormatException error)
        {
            JOptionPane.showMessageDialog(UpdateAfterSales.this, "Incorrect Number Format");
            quantity=0;
        }
        
        System.out.println("Quantity:"+quantity);
        int size1=SoldRecipeModel.getSize();
        int size2=SoldRecipeQuantityModel.getSize();
        boolean flag1=false;
        
        for(int x=0;x<size1;x++)
        {
            if(selectedRecipe.equals(SoldRecipeModel.getElementAt(x)))
            {
                JOptionPane.showMessageDialog(UpdateAfterSales.this, "Item already exists");
                flag1=true;
                break;
            }
        }
        if(flag1==false)
        {         
            if(quantityString.equals("Enter Quantity Here")||quantity==0)
            {
                JOptionPane.showMessageDialog(UpdateAfterSales.this, "Please enter quantity correctly");
            }
            
            else
            {
                System.out.println("Inside flag==false");
                SoldRecipeModel.addElement(selectedRecipe);
                SoldRecipeQuantityModel.addElement(quantity);
                SoldRecipeQuantityList.ensureIndexIsVisible(size2);
                SoldRecipeName.ensureIndexIsVisible(size1);
                SoldRecipeQuantityList.setSelectedIndex(0);
                SoldRecipeName.setSelectedIndex(0);
                System.out.println("Commands executed");
            }
            
        }
        // TODO add your handling code here:
    
    }
    
    public void reduceResources() throws SQLException
    {
        int size=SoldRecipeModel.getSize();
        
        for(int i=0;i<size;i++)
        {
            String RName=SoldRecipeModel.getElementAt(i).toString();
            System.out.println("Retrived Element:"+RName);
            int ID=getID(RName);
            int SoldQuantity=Integer.parseInt(SoldRecipeQuantityModel.getElementAt(i).toString());
            System.out.println("SoldQuantity:"+SoldQuantity);
            String SQL="SELECT IngredientID,QuantityUsed FROM recipedetails where RecipeID="+ID;
            ResultSet tempresult=stmt.executeQuery(SQL);
            //int QuantityBeforeUpdate=tempresult.getInt(2);
            
            while(tempresult.next())
            {
                System.out.println("The Recipe ID being updated:"+ ID);
                int IngredientID=tempresult.getInt(1);
                int Quantity=tempresult.getInt(2);
                Quantity=Quantity*SoldQuantity;
                System.out.println("Ingr ID and Quantity:"+IngredientID+"  "+Quantity);
                ResultSet tempresult2;
                String SQL2="SELECT CurrentlyQuantity FROM IngredientHeader where IngredientID="+IngredientID;
                tempresult2=stmtDeduct.executeQuery(SQL2);
                tempresult2.next();
                int Current=tempresult2.getInt(1);
                System.out.println("Current Quantity:"+ Current);
                int difference=Current-Quantity;
                String updateQuery="UPDATE project440.IngredientHeader SET CurrentlyQuantity="+difference+" WHERE IngredientID="+IngredientID;
                stmtDeduct.execute(updateQuery);
                System.out.println("Update value to:"+difference);
                
            }
        }
        JOptionPane.showMessageDialog(this, "Success");
        updateResources.setEnabled(false);
        
        
    }

    public int getID(String in) throws SQLException
    {
        String query= "SELECT RecipeID FROM project440.RECIPEHEADER WHERE RecipeName='"+in+"'";
        ResultSet tempresult=stmt.executeQuery(query);
        tempresult.next();
        int value=tempresult.getInt(1);
        System.out.println("Retrived Recipe ID:"+value);
        return value;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        try
        {
            GetRecipeList();
        }

        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        RecipeListBox = new javax.swing.JList(RecipeListModel);
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SoldRecipeName = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        SoldRecipeQuantityList = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        addToList = new javax.swing.JButton();
        removeFromList = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        updateResources = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        quantityField = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Sales for the Day");

        jScrollPane1.setViewportView(RecipeListBox);

        jLabel1.setText("Recipe List");

        SoldRecipeName.setModel(SoldRecipeModel);
        jScrollPane2.setViewportView(SoldRecipeName);

        jLabel2.setText("Recipe Sold");

        SoldRecipeQuantityList.setModel(SoldRecipeQuantityModel);
        jScrollPane3.setViewportView(SoldRecipeQuantityList);

        jLabel3.setText("Quantity sold");

        addToList.setText("<-- Add");
        addToList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToListActionPerformed(evt);
            }
        });

        removeFromList.setText("Remove");
        removeFromList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFromListActionPerformed(evt);
            }
        });

        jLabel4.setText("Quantity");

        updateResources.setText("Update");
        updateResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateResourcesActionPerformed(evt);
            }
        });

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        quantityField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####"))));
        quantityField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(removeFromList)
                                            .addComponent(addToList))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(30, 30, 30))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(updateResources, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(66, 66, 66))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addToList, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeFromList, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(updateResources, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addToListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToListActionPerformed

        addRecipeToList();
        // TODO add your handling code here:
    }//GEN-LAST:event_addToListActionPerformed

    private void updateResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateResourcesActionPerformed

        try
        {
            reduceResources();
        }
        
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_updateResourcesActionPerformed

    private void removeFromListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFromListActionPerformed

        int selectedIndex=SoldRecipeName.getSelectedIndex();
        SoldRecipeModel.removeElementAt(selectedIndex);
        SoldRecipeQuantityModel.removeElementAt(selectedIndex);
        int size1=SoldRecipeModel.getSize();
        SoldRecipeQuantityList.ensureIndexIsVisible(size1);
        SoldRecipeName.ensureIndexIsVisible(size1);
        SoldRecipeQuantityList.setSelectedIndex(0);
        SoldRecipeName.setSelectedIndex(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_removeFromListActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.dispose();
        new MainPage().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void quantityFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateAfterSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateAfterSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateAfterSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateAfterSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateAfterSales().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList RecipeListBox;
    private javax.swing.JList SoldRecipeName;
    private javax.swing.JList SoldRecipeQuantityList;
    private javax.swing.JButton addToList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JFormattedTextField quantityField;
    private javax.swing.JButton removeFromList;
    private javax.swing.JButton updateResources;
    // End of variables declaration//GEN-END:variables
}
