/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project440Package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Simant
 */
public class UpdateAfterReceiving extends javax.swing.JFrame {

    DefaultListModel IngredientListModel;
    Connection con;
    Statement stmt,stmtUpdate;
    ResultSet rs,rsIngredient,rsUpdate;
    DefaultListModel IngredientReceivedModel=new DefaultListModel();
    DefaultListModel IngredientQuantityReceivedModel=new DefaultListModel();
    /**
     * Creates new form UpdateAfterReceiving
     */
    public UpdateAfterReceiving() {
        initComponents();
        try
        {
            connectToDatabase();
        }
        
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
    }

    public void connectToDatabase() throws SQLException
    {
        System.out.println("Connecting to Database");
        String Uname="root";
        String Upass="engineering";
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        System.out.println("Connected to Database");
        
    }
    
    public void updateDatabase() throws SQLException
    {
        String Uname="root";
        String Upass="engineering";
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmtUpdate= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        boolean test=false;
        
        int size=IngredientReceivedModel.getSize();
        
        for(int i=0;i<size;i++)
        {
            String element=IngredientReceivedModel.getElementAt(i).toString();
            int quantity=Integer.parseInt(IngredientQuantityReceivedModel.getElementAt(i).toString());
            String SQL="SELECT INGREDIENTNAME,CURRENTLYQUANTITY FROM INGREDIENTHEADER WHERE INGREDIENTNAME='"+element+"'";
            rsUpdate=stmtUpdate.executeQuery(SQL);
            rsUpdate.next();
            int current=rsUpdate.getInt(2);
            System.out.println("Current Quantity:"+current);
            int newQuantity=current+quantity;
            String SQL2="UPDATE INGREDIENTHEADER SET CURRENTLYQUANTITY="+newQuantity+" WHERE INGREDIENTNAME='"+element+"'";
            test=stmt.execute(SQL2);
            System.out.println("Updating");
            System.out.println("New Quantity:"+newQuantity);
            
        }
        
        if(test==false) {
                JOptionPane.showMessageDialog(UpdateAfterReceiving.this, "Success");
                jButton1.setEnabled(false);
            }
            else {
                JOptionPane.showMessageDialog(UpdateAfterReceiving.this, "Failed");
            }
        
    }
    
        public void GetIngredientList() throws SQLException
    {
        String Uname="root";
        String Upass="engineering";
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String SQL2= "SELECT * FROM project440.ingredientheader ORDER BY IngredientID";
        rsIngredient=stmt.executeQuery(SQL2);
        IngredientListModel=new DefaultListModel();
        int countrows=0;
        rsIngredient.beforeFirst();
        while(rsIngredient.next())
        {
            countrows++;
        }
                
        rsIngredient.beforeFirst();
        
        String CurrentIngredientList[]=new String[countrows];
        int CurrentIngredientID[]=new int[countrows];
        
        int rowindex=0;
        
        while(rsIngredient.next())
        {
            CurrentIngredientList[rowindex]=rsIngredient.getString("IngredientName");
            CurrentIngredientID[rowindex]=Integer.parseInt(rsIngredient.getString("IngredientID"));
            System.out.println("ID:"+CurrentIngredientID[rowindex]);
            IngredientListModel.addElement(CurrentIngredientList[rowindex]);
            rowindex++;
        }
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
            GetIngredientList();
        }

        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        IngredientList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        IngredientReceived = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        IngredientQuantityList = new javax.swing.JList();
        QuantityLabel = new javax.swing.JLabel();
        QuantityTextField = new javax.swing.JTextField();
        addToList = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Update Received Data");

        IngredientList.setModel(IngredientListModel);
        jScrollPane1.setViewportView(IngredientList);

        jLabel1.setText("List of Ingredients");

        IngredientReceived.setModel(IngredientReceivedModel);
        jScrollPane2.setViewportView(IngredientReceived);

        IngredientQuantityList.setModel(IngredientQuantityReceivedModel);
        jScrollPane3.setViewportView(IngredientQuantityList);

        QuantityLabel.setText("Quantity Received");

        addToList.setText("<-- Add");
        addToList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToListActionPerformed(evt);
            }
        });

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingredients Received");

        jLabel3.setText("Quantity Received");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Update Received Ingredients");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addToList, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(QuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(QuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)))
                            .addGap(23, 23, 23)))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(addToList)
                        .addGap(37, 37, 37)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(QuantityLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(QuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addToListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToListActionPerformed

        String selectedRecipe=IngredientListModel.getElementAt(IngredientList.getSelectedIndex()).toString();
        String quantityString=QuantityTextField.getText();
        int quantity;
        try
        {
            quantity=Integer.parseInt(quantityString);
        }
        
        catch(NumberFormatException error)
        {
            JOptionPane.showMessageDialog(UpdateAfterReceiving.this, "Incorrect Number Format");
            System.out.println(error.getMessage());
            quantity=0;
        }
        
        System.out.println("Quantity:"+quantity);
        int size1=IngredientReceivedModel.getSize();
        int size2=IngredientQuantityReceivedModel.getSize();
        boolean flag1=false;
        
        for(int x=0;x<size1;x++)
        {
            if(selectedRecipe.equals(IngredientReceivedModel.getElementAt(x)))
            {
                JOptionPane.showMessageDialog(UpdateAfterReceiving.this, "Item already exists");
                flag1=true;
                break;
            }
        }
        if(flag1==false)
        {         
            if(quantityString.equals("Enter Quantity Here")||quantity==0)
            {
                JOptionPane.showMessageDialog(UpdateAfterReceiving.this, "Please enter quantity correctly");
            }
            
            else
            {
                System.out.println("Inside flag==false");
                IngredientReceivedModel.addElement(selectedRecipe);
                IngredientQuantityReceivedModel.addElement(quantity);
                IngredientQuantityList.ensureIndexIsVisible(size2);
                IngredientReceived.ensureIndexIsVisible(size1);
                IngredientQuantityList.setSelectedIndex(0);
                IngredientList.setSelectedIndex(0);
                System.out.println("Commands executed");
            }
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_addToListActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try
        {
            updateDatabase();
        }
        
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int selectedIndex=IngredientReceived.getSelectedIndex();
        IngredientReceivedModel.removeElementAt(selectedIndex);
        IngredientQuantityReceivedModel.removeElementAt(selectedIndex);
        int size1=IngredientReceivedModel.getSize();
        IngredientQuantityList.ensureIndexIsVisible(size1);
        IngredientReceived.ensureIndexIsVisible(size1);
        IngredientQuantityList.setSelectedIndex(0);
        IngredientReceived.setSelectedIndex(0);// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        this.dispose();
        new MainPage().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(UpdateAfterReceiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateAfterReceiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateAfterReceiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateAfterReceiving.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UpdateAfterReceiving().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList IngredientList;
    private javax.swing.JList IngredientQuantityList;
    private javax.swing.JList IngredientReceived;
    private javax.swing.JLabel QuantityLabel;
    private javax.swing.JTextField QuantityTextField;
    private javax.swing.JButton addToList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
