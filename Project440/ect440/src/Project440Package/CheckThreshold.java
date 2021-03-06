/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project440Package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
public class CheckThreshold extends javax.swing.JFrame {
    
    
    Connection con;
    Statement stmt;
    ResultSet rs;
    DefaultListModel thresholdModel=new DefaultListModel();
    DefaultListModel orderQuantityModel=new DefaultListModel();

    /**
     * Creates new form CheckThreshold
     */
    public CheckThreshold() {
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
    
    
    public void getIngredient() throws SQLException
    {
        String SQL="Select IngredientID,IngredientName from IngredientHeader where CurrentlyQuantity<=ThresholdLevel";
        rs=stmt.executeQuery(SQL);
        while(rs.next())
        {
            String element=rs.getString(2);
            System.out.println("Getting Ingredient:"+element);
            thresholdModel.addElement(element);
            thresholdList.ensureIndexIsVisible(thresholdModel.getSize());
        }
        
        createOrderButton.setEnabled(true);
        
    }
    
    
    public void createOrder() throws SQLException
    {
        checkThresholdButton.setEnabled(false);
        orderQuantityModel.clear();
        int size=thresholdModel.getSize();
        int value[]=new int[size];
        for(int i=0;i<size;i++)
        {
            String element=thresholdModel.getElementAt(i).toString();
            String input="Enter Order Value for ingredient:"+element;
            value[i]=Integer.parseInt(JOptionPane.showInputDialog(CheckThreshold.this, input, value[i]));
            while(value[i]<=0)
            {
                value[i]=Integer.parseInt(JOptionPane.showInputDialog(CheckThreshold.this, input, value));   
            }
            System.out.println("Value:"+value[i]);
            orderQuantityModel.addElement(value[i]);
            orderQuantityList.ensureIndexIsVisible(i+1);
            
        }
        
        createOrderButton.setText("Change Order");
        processOrderButton.setEnabled(true);
    }
    
    
    public void processOrder() throws IOException
    {
        try
        {
            FileWriter writer = new FileWriter("Orders.txt");
            try (BufferedWriter out = new BufferedWriter(writer)) {
                out.write("Order Details");
                out.newLine();
                out.newLine();
                int size=orderQuantityModel.getSize();
                for(int i=0;i<size;i++)
                {
                    String element=thresholdModel.getElementAt(i).toString();
                    String quantity=orderQuantityModel.getElementAt(i).toString();
                    out.write("Ingredient Name:"+element);
                    out.newLine();
                    out.write("Order Quantity:"+quantity);
                    out.newLine();
                    out.newLine();
                
                }
            }
        }
        
        catch(IOException error)
        {
            System.out.println(error.getMessage());
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

        checkThresholdButton = new javax.swing.JButton();
        createOrderButton = new javax.swing.JButton();
        processOrderButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        thresholdList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderQuantityList = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        checkThresholdButton.setText("Check Threshold");
        checkThresholdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkThresholdButtonActionPerformed(evt);
            }
        });

        createOrderButton.setText("Create Order");
        createOrderButton.setEnabled(false);
        createOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createOrderButtonActionPerformed(evt);
            }
        });

        processOrderButton.setText("Process Order");
        processOrderButton.setEnabled(false);
        processOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processOrderButtonActionPerformed(evt);
            }
        });

        thresholdList.setModel(thresholdModel);
        jScrollPane1.setViewportView(thresholdList);

        orderQuantityList.setModel(orderQuantityModel);
        jScrollPane2.setViewportView(orderQuantityList);

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(checkThresholdButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(createOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(processOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(processOrderButton)
                        .addGap(31, 31, 31)
                        .addComponent(jButton1)
                        .addGap(99, 99, 99)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkThresholdButton)
                    .addComponent(createOrderButton))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkThresholdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkThresholdButtonActionPerformed

        try
        {
            getIngredient();
        }
        
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_checkThresholdButtonActionPerformed

    private void createOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOrderButtonActionPerformed

        try
        {
            createOrder();
        }
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_createOrderButtonActionPerformed

    private void processOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processOrderButtonActionPerformed

        try
        {
            processOrder();
        }
        
        catch(IOException error)
        {
            System.out.println(error.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_processOrderButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.dispose();
        new MainPage().setVisible(true);
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
            java.util.logging.Logger.getLogger(CheckThreshold.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckThreshold.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckThreshold.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckThreshold.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CheckThreshold().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkThresholdButton;
    private javax.swing.JButton createOrderButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList orderQuantityList;
    private javax.swing.JButton processOrderButton;
    private javax.swing.JList thresholdList;
    // End of variables declaration//GEN-END:variables
}
