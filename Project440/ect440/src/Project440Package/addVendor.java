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
import javax.swing.JOptionPane;

/**
 *
 * @author Simant
 */
public class addVendor extends javax.swing.JFrame {

    Connection con;
    Statement stmt,stmt1;
    ResultSet rs;
    /**
     * Creates new form addVendor
     */
    public addVendor() {
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
        String SQL="select * from vendorheader ORDER BY VendorID";
        rs=stmt.executeQuery(SQL);
        System.out.println("Connected to Database");
        
    }
    
    public void addToDatabase() throws SQLException
    {
        String VName=vendorName.getText();
        System.out.println("Reached Vname");
        int VID=getID();
        VID++;
        String VType=vendorType.getText();
        String VEmail=vendorEmail.getText();
        String VDetails=vendorDetails.getText();
        System.out.println("Got the details");
        
        String SQL="INSERT INTO PROJECT440.Vendorheader (VendorID,VendorName,VendorType,VendorDetails,VendorEmail) VALUES('"+VID+"','"+VName+"','"+VType+"','"+VDetails+"','"+VEmail+"')";
        boolean test=stmt.execute(SQL);
        if(test==false){
            JOptionPane.showMessageDialog(addVendor.this, "Added Successfully");
        }
    }
    
    
    public int getID() throws SQLException
    {
        String query= "SELECT * FROM project440.VENDORHEADER ORDER BY VENDORID";
        stmt1= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet tempresult=stmt1.executeQuery(query);
        tempresult.last();
        int value=tempresult.getInt("VendorID");
        System.out.println("Retrived Vendor ID:"+value);
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

        vendorName = new javax.swing.JTextField();
        vendorType = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        vendorDetails = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        vendorEmail = new javax.swing.JTextField();
        Email = new javax.swing.JLabel();
        addVendor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        vendorName.setText("Name");
        vendorName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vendorNameFocusGained(evt);
            }
        });

        vendorType.setText("Vendor Type");
        vendorType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendorTypeActionPerformed(evt);
            }
        });
        vendorType.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vendorTypeFocusGained(evt);
            }
        });

        jLabel1.setText("Name");

        jLabel2.setText("Type");

        vendorDetails.setColumns(20);
        vendorDetails.setRows(5);
        vendorDetails.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vendorDetailsFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(vendorDetails);

        jLabel3.setText("Details");

        vendorEmail.setText("email address");
        vendorEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vendorEmailFocusGained(evt);
            }
        });

        Email.setText("Email");

        addVendor.setText("Save");
        addVendor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVendorActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Add a new Vendor");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(vendorName))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(vendorEmail)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(vendorType))))
                .addGap(85, 85, 85))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(Email)
                .addGap(363, 363, 363))
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(vendorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(vendorType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Email)
                    .addComponent(vendorEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addVendor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void vendorTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendorTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vendorTypeActionPerformed

    private void addVendorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVendorActionPerformed

        try
        {
            String Uname="root";
            String Upass="engineering";
            String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
            con = DriverManager.getConnection( host,Uname,Upass );
            stmt= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String SQL="select * from vendorheader ORDER BY VendorID";
            rs=stmt.executeQuery(SQL);
            String Name=vendorName.getText();
            String VType=vendorType.getText();
            String VDetails=vendorDetails.getText();
            String VEmail=vendorEmail.getText();
            boolean duplicateFlag=false;
            while(rs.next())
            {
                if(Name.equals(rs.getString(2)))
                {
                    duplicateFlag=true;
                    break;
                }
            }
            if(Name.isEmpty() || VType.isEmpty() || VDetails.isEmpty() || VEmail.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "No field should be left empty");
            }
            
            else if(duplicateFlag)
            {
                JOptionPane.showMessageDialog(this, "Vendor Name Already Exists");
                
            }
            
            else
            {
            
                addToDatabase();
                
                vendorName.setText(null);
                vendorDetails.setText(null);
                vendorEmail.setText(null);
                vendorType.setText(null);
            }
        }
        
        catch(SQLException error)
        {
            JOptionPane.showMessageDialog(this,error.getMessage().toString());
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_addVendorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.dispose();
        new MainPage().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void vendorNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vendorNameFocusGained

        vendorName.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_vendorNameFocusGained

    private void vendorTypeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vendorTypeFocusGained

        vendorType.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_vendorTypeFocusGained

    private void vendorDetailsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vendorDetailsFocusGained

        vendorDetails.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_vendorDetailsFocusGained

    private void vendorEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vendorEmailFocusGained

        vendorEmail.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_vendorEmailFocusGained

    
    
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
            java.util.logging.Logger.getLogger(addVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addVendor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addVendor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Email;
    private javax.swing.JButton addVendor;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea vendorDetails;
    private javax.swing.JTextField vendorEmail;
    private javax.swing.JTextField vendorName;
    private javax.swing.JTextField vendorType;
    // End of variables declaration//GEN-END:variables
}