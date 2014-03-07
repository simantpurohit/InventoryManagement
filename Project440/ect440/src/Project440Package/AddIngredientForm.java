/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project440Package;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author Simant
 */
public class AddIngredientForm extends javax.swing.JFrame {
    
    DefaultListModel newModel;
    DefaultComboBoxModel<String> vendorModel;

    public void getMe()
    {
        GetIngredientList getIt=new GetIngredientList();
    try
    {
        System.out.println("Executing Function");
        ArrayList<String> newList=getIt.getIngredientList();
        Iterator IngredientIterator=newList.listIterator();
        int i=0;
        //Object getArray=IngredientList.toArray();
        newModel=new DefaultListModel();
        while(IngredientIterator.hasNext())
        {
            newModel.addElement(IngredientIterator.next().toString());
            
            System.out.println("Element in list:"+newModel.getElementAt(i));
            i++;
            
        }
        
        IngredientIterator=newList.iterator();
    }
    
    catch(SQLException error)
    {
        System.out.println(error.getMessage());
    }
    }
    
    
    public void getVendor()
    {
        GetVendorList getIt=new GetVendorList();
    try
    {
        System.out.println("Executing Function");
        ArrayList<String> newList=getIt.getVendorList();
        Iterator VendorIterator=newList.listIterator();
        int i=0;
        //Object getArray=IngredientList.toArray();
        vendorModel=new DefaultComboBoxModel<>();
        while(VendorIterator.hasNext())
        {
            vendorModel.addElement(VendorIterator.next().toString());
            System.out.println("Element in list:"+vendorModel.getElementAt(i));
            i++;
        }
        
        //VendorIterator=newList.iterator();
    }
    
    catch(SQLException error)
    {
        System.out.println(error.getMessage());
    }
    }
    
    
    public void addToDatabase() throws SQLException, NumberFormatException
    {
        Connection con;
        Statement stmt;
        ResultSet rs;
        //connection to database
        String Uname="root";
        String Upass="engineering";
        //connection string
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String SQL= "SELECT * FROM project440.IngredientHeader ORDER BY IngredientID";
        rs=stmt.executeQuery(SQL);
        String Name=IngredientNameText.getText().toString();
        
        System.out.println("Name:"+Name+"End");
        
        int Quantity=Integer.parseInt(quantityTextField.getText());
        
        int threshold=Integer.parseInt(thresholdText.getText());
        
        String vendor=vendorComboBox.getSelectedItem().toString();
        
        System.out.println("Vendor:"+vendor);
        
        GetIngredientList getIt=new GetIngredientList();
        ArrayList<String> list=new ArrayList<>();
        list=getIt.getIngredientList();
        Iterator iterate=list.listIterator();
        boolean addFlag=false;
        while(iterate.hasNext())
        {
            String iterateName=iterate.next().toString();
            System.out.println("Comapring the name with:"+iterateName);
            if(Name.equalsIgnoreCase(iterateName))
            {
                JOptionPane.showMessageDialog(this, "Ingredient with the name already exists");
                addFlag=false;
            }
        }
        
        if(Name.equals(null) || Name.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Name Field Cannot Be Blank");
                addFlag=false;
            }
        else if(Quantity<=0)
        {
            JOptionPane.showMessageDialog(this, "Quantity Incorrectly Entered");
            addFlag=false;
        }
        else if(threshold<=0)
        {
            JOptionPane.showMessageDialog(this, "Threshold Incorrectly Entered");
            addFlag=false;
        }
        else
        {
            addFlag=true;
            System.out.println("Add Flag true");
        }
        
        if(addFlag)
        {
            rs.last();
            int IngrID=rs.getInt(1);
            IngrID++;
            int VendorID=getID(vendor);
            
            String adding="INSERT INTO PROJECT440.IngredientHeader (INGREDIENTID,INGREDIENTNAME,CURRENTLYQUANTITY,THRESHOLDLEVEL,AVAILABLEFROMVENDOR) VALUES('"+IngrID+"','"+Name+"','"+Quantity+"','"+threshold+"','"+VendorID+"')";
            boolean test=stmt.execute(adding);
            
            if(test==false)
            {
                JOptionPane.showMessageDialog(this, "Ingredient successfully added");
                getMe();
                IngredientListForm.setModel(newModel);
                IngredientNameText.setText("");
                ingredientValidation.setText("Name Invalid");
                quantityTextField.setText("0");
                thresholdText.setText("0");
                vendorComboBox.setSelectedIndex(1);
            }
        }
        
        con.close();
    }
    
    
    public int getID(String in) throws SQLException
    {
        String query= "SELECT * FROM project440.vendorheader WHERE vendorname='"+in+"'";
        Connection con;
        Statement stmt;
        ResultSet rs;
        //connection to database
        String Uname="root";
        String Upass="engineering";
        //connection string
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        ResultSet tempresult=stmt.executeQuery(query);
        
        tempresult.next();
        
        int value=tempresult.getInt("VendorID");
        
        System.out.println("Retrived Vendor ID:"+value);
        
        return value;
    }
    /**
     * Creates new form AddIngredientForm
     */
    public AddIngredientForm() {
        initComponents();
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
        getMe();
        IngredientListForm = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IngredientNameText = new javax.swing.JTextField();
        getVendor();
        vendorComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        submitButton = new javax.swing.JButton();
        quantityTextField = new javax.swing.JFormattedTextField();
        thresholdText = new javax.swing.JFormattedTextField();
        ingredientValidation = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        IngredientListForm.setModel(newModel);
        jScrollPane1.setViewportView(IngredientListForm);

        jLabel1.setText("Current List of Ingredients");

        jLabel2.setText("New Ingredient Name:");

        jLabel3.setText("Current Quantity:");

        jLabel4.setText("Specify Thresold Vaue");

        IngredientNameText.setText("Name");
        IngredientNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngredientNameTextActionPerformed(evt);
            }
        });
        IngredientNameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                IngredientNameTextFocusGained(evt);
            }
        });
        IngredientNameText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IngredientNameTextKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                IngredientNameTextKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                IngredientNameTextKeyTyped(evt);
            }
        });

        vendorComboBox.setModel(vendorModel);
        vendorComboBox.setSelectedIndex(0);

        jLabel5.setText("Select Vendor");

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        quantityTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        quantityTextField.setText("0");
        quantityTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                quantityTextFieldFocusGained(evt);
            }
        });

        thresholdText.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        thresholdText.setText("0");
        thresholdText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thresholdTextActionPerformed(evt);
            }
        });
        thresholdText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                thresholdTextFocusGained(evt);
            }
        });

        ingredientValidation.setForeground(new java.awt.Color(255, 0, 51));
        ingredientValidation.setText("Name Invalid");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Add a new ingredient");
        jLabel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel7.setText("jLabel6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vendorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IngredientNameText, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(quantityTextField)
                            .addComponent(thresholdText)
                            .addComponent(ingredientValidation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(44, 44, 44))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(IngredientNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addComponent(ingredientValidation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(quantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(thresholdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vendorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jButton1)))))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed

        try
        {
            addToDatabase();
            getMe();
            IngredientListForm.setModel(newModel);
        }
        
        catch(SQLException error)
        {
            JOptionPane.showMessageDialog(this, error.getMessage().toString());
        }
        
        catch(NumberFormatException error)
        {
            JOptionPane.showMessageDialog(this, "Error in one of the numeric fields "+error.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_submitButtonActionPerformed

    private void thresholdTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thresholdTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thresholdTextActionPerformed

    private void quantityTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityTextFieldFocusGained

        quantityTextField.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityTextFieldFocusGained

    private void thresholdTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_thresholdTextFocusGained

        thresholdText.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_thresholdTextFocusGained

    private void IngredientNameTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IngredientNameTextKeyTyped

        int size=newModel.getSize();
        for(int i=0;i<size;i++)
        {
            String name=newModel.getElementAt(i).toString();
            String match=IngredientNameText.getText();
            if(name.equals(match))
            {
                ingredientValidation.setText("Name Already Exists");
                ingredientValidation.setForeground(Color.red);
                break;
            }
            else if(match.isEmpty())
            {
                ingredientValidation.setText("Name Invalid");
                ingredientValidation.setForeground(Color.red);
            }

            else
            {
                ingredientValidation.setText("Name seems valid");
                ingredientValidation.setForeground(Color.blue);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_IngredientNameTextKeyTyped

    private void IngredientNameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IngredientNameTextFocusGained

        IngredientNameText.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_IngredientNameTextFocusGained

    private void IngredientNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngredientNameTextActionPerformed

        
        int size=newModel.getSize();
        for(int i=0;i<size;i++)
        {
            String name=newModel.getElementAt(i).toString();
            String match=IngredientNameText.getText();
            if(name.equals(match))
            {
                ingredientValidation.setText("Name Already Exists");
                ingredientValidation.setForeground(Color.red);
                break;
            }
            else if(match.isEmpty())
            {
                ingredientValidation.setText("Name Invalid");
                ingredientValidation.setForeground(Color.red);
            }

            else
            {
                ingredientValidation.setText("Name seems valid");
                ingredientValidation.setForeground(Color.blue);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_IngredientNameTextActionPerformed

    private void IngredientNameTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IngredientNameTextKeyPressed
 int size=newModel.getSize();
        for(int i=0;i<size;i++)
        {
            String name=newModel.getElementAt(i).toString();
            String match=IngredientNameText.getText();
            if(name.equals(match))
            {
                ingredientValidation.setText("Name Already Exists");
                ingredientValidation.setForeground(Color.red);
                break;
            }
            else if(match.isEmpty())
            {
                ingredientValidation.setText("Name Invalid");
                ingredientValidation.setForeground(Color.red);
            }

            else
            {
                ingredientValidation.setText("Name seems valid");
                ingredientValidation.setForeground(Color.blue);
            }
        }

       
        // TODO add your handling code here:
    }//GEN-LAST:event_IngredientNameTextKeyPressed

    private void IngredientNameTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IngredientNameTextKeyReleased
 int size=newModel.getSize();
        for(int i=0;i<size;i++)
        {
            String name=newModel.getElementAt(i).toString();
            String match=IngredientNameText.getText();
            if(name.equals(match))
            {
                ingredientValidation.setText("Name Already Exists");
                ingredientValidation.setForeground(Color.red);
                break;
            }
            else if(match.isEmpty())
            {
                ingredientValidation.setText("Name Invalid");
                ingredientValidation.setForeground(Color.red);
            }

            else
            {
                ingredientValidation.setText("Name seems valid");
                ingredientValidation.setForeground(Color.blue);
            }
        }

       
        // TODO add your handling code here:
    }//GEN-LAST:event_IngredientNameTextKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AddIngredientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddIngredientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddIngredientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddIngredientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddIngredientForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList IngredientListForm;
    private javax.swing.JTextField IngredientNameText;
    private javax.swing.JLabel ingredientValidation;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField quantityTextField;
    private javax.swing.JButton submitButton;
    private javax.swing.JFormattedTextField thresholdText;
    private javax.swing.JComboBox vendorComboBox;
    // End of variables declaration//GEN-END:variables
}
