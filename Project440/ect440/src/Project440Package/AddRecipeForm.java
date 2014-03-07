package Project440Package;

import java.awt.Frame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simant
 */
public class AddRecipeForm extends javax.swing.JFrame {
    
    Connection con;
    Statement stmt,stmt1,stmt2,stmt3,stmt4;
    ResultSet rs,rsIngredient,rsRecipeDetails;
    DefaultListModel IngredientListModel;
    DefaultListModel IngredientInRecipeModel=new DefaultListModel();
    DefaultListModel RequiredQuantityForRecipe=new DefaultListModel();
    String CurrentIngredientList[];
    int CurrentIngredientID[];
    

    /**
     * Creates new form AddRecipeForm
     */
    public AddRecipeForm() {
        initComponents();
        try
        {
            ConnectToDatabase();
        }
        
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        
    }
    
    
    public final void ConnectToDatabase() throws SQLException
    {
        //connection to database
        String Uname="root";
        String Upass="engineering";
        //connection string
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt4 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        String SQL = "SELECT * FROM project440.RECIPEHEADER ORDER BY RECIPEID";
        String SQL2= "SELECT * FROM project440.INGREDIENTHEADER ORDER BY INGREDIENTID";
        String SQL3= "SELECT * FROM PROJECT440.RECIPEDETAILS ORDER BY RECIPEID";
        
        rs = stmt1.executeQuery( SQL );
        rsIngredient=stmt2.executeQuery(SQL2);
        rsRecipeDetails=stmt3.executeQuery(SQL3);
        
    }
    
    public void AddtoDatabase() throws SQLException
    {
        int RID;
        //Get the Recipename from text field
        String RName=RecipeTextField.getText();
        //Move to the end and get the last recipeID
        rs.last();
        RID=Integer.parseInt(rs.getString("RecipeID"));
        //move pointer befor the first entry
        rs.beforeFirst();
        
        //Increment to new ID
        RID++;
        
        //Insert the new data into RecipeHeader Table
        String query2="INSERT INTO PROJECT440.RECIPEHEADER (RECIPEID,RECIPENAME) VALUES('"+RID+"','"+RName+"')";
        boolean test=stmt.execute(query2);
        
        //Add the new recipe and ingredients details to the recipedetails table
        
        int listSize=IngredientInRecipeModel.getSize();
        
        //Insert
        for(int add=0;add<listSize;add++)
        {
            String element=IngredientInRecipeModel.getElementAt(add).toString();
            int elementQuantity=Integer.parseInt(RequiredQuantityForRecipe.getElementAt(add).toString());
            int elementID=getID(element);
            String query="INSERT INTO PROJECT440.RECIPEDETAILS (RECIPEID,INGREDIENTID,QUANTITYUSED) VALUES('"+RID+"','"+elementID+"','"+elementQuantity+"')"; 
            System.out.println("Adding Recipe Details:"+RID+" "+elementID+" "+elementQuantity);
            test=stmt4.execute(query);
            
        }
        
        //Test if the addition was successful
        if(test==false)
        {
            JOptionPane.showMessageDialog(AddRecipeForm.this, "Successfully added");
        }
        
        
    }
    
    //Function to retrive the ingredient ID from the Ingredients header table
    public int getID(String in) throws SQLException
    {
        String query= "SELECT * FROM project440.INGREDIENTHEADER WHERE INGREDIENTNAME='"+in+"'";
        ResultSet tempresult=stmt.executeQuery(query);
        tempresult.next();
        int value=tempresult.getInt("IngredientID");
        System.out.println("Retrived Ingredient ID:"+value);
        return value;
    }

    //Not being used
    public void GetIngredientList() throws SQLException
    {
        
        String Uname="root";
        String Upass="engineering";
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
        
        CurrentIngredientList=new String[countrows];
        CurrentIngredientID=new int[countrows];
        
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

    public boolean getRecipeList() throws SQLException
    {
        String Uname="root";
        String Upass="engineering";
        String host="jdbc:mysql://localhost:3306/project440?zeroDateTimeBehavior=convertToNull";
        con = DriverManager.getConnection( host,Uname,Upass );
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String SQL2= "SELECT * FROM project440.recipeheader ORDER BY recipeID";
        ResultSet rsRecipe=stmt.executeQuery(SQL2);
        boolean flag=false;
        while(rsRecipe.next())
        {
            String Name=rsRecipe.getString(2);
            String match=RecipeTextField.getText();
            if(Name.equals(match))
            {
                flag=true;
                break;
            }
            
            else
            {
                flag=false;
            }
            
        }
        
        return flag;
    }
    
    //Getting the list of all the available ingredients on the form
    public void getMe() throws SQLException
    {
        GetIngredientList getIt=new GetIngredientList();
        System.out.println("Executing Function");
        ArrayList<String> newList=getIt.getIngredientList();
        Iterator IngredientIterator=newList.listIterator();
        int i=0;
        //Object getArray=IngredientList.toArray();
        IngredientListModel=new DefaultListModel();
        while(IngredientIterator.hasNext())
        {
            IngredientListModel.addElement(IngredientIterator.next().toString());
            System.out.println("Element in list:"+IngredientListModel.getElementAt(i));
            i++;
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jSplitPane1 = new javax.swing.JSplitPane();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        try
        {
            //GetIngredientList();
            getMe();
        }

        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        CurrentList = new javax.swing.JList(IngredientListModel);
        CurrentList.setSelectedIndex(0);
        jScrollPane2 = new javax.swing.JScrollPane();
        IngredientsInRecipeList = new javax.swing.JList();
        addToList = new javax.swing.JButton();
        AddRecipeToDbButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        removeFromList = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        RequiredQuantityList = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        QuantityTextField = new javax.swing.JFormattedTextField();
        RecipeTextField = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        addingrButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Recipe Name");

        jScrollPane1.setViewportView(CurrentList);

        IngredientsInRecipeList.setModel(IngredientInRecipeModel);
        IngredientsInRecipeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        IngredientsInRecipeList.setSelectedIndex(RequiredQuantityList.getSelectedIndex());

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, RequiredQuantityList, org.jdesktop.beansbinding.ELProperty.create("${selectedIndices}"), IngredientsInRecipeList, org.jdesktop.beansbinding.BeanProperty.create("selectedIndices"));
        bindingGroup.addBinding(binding);

        IngredientsInRecipeList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                IngredientsInRecipeListFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(IngredientsInRecipeList);

        addToList.setText("<--Add");
        addToList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToListActionPerformed(evt);
            }
        });

        AddRecipeToDbButton.setText("Add Recipe to Database");
        AddRecipeToDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRecipeToDbButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingredients in Recipe");

        jLabel3.setText("List of available Ingredients");

        removeFromList.setText("Remove-->");
        removeFromList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFromListActionPerformed(evt);
            }
        });

        RequiredQuantityList.setModel(RequiredQuantityForRecipe);
        RequiredQuantityList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        RequiredQuantityList.setSelectedIndex(IngredientsInRecipeList.getSelectedIndex());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, IngredientsInRecipeList, org.jdesktop.beansbinding.ELProperty.create("${selectedIndices}"), RequiredQuantityList, org.jdesktop.beansbinding.BeanProperty.create("selectedIndices"));
        bindingGroup.addBinding(binding);

        jScrollPane3.setViewportView(RequiredQuantityList);

        jLabel4.setText("Corresponding Quantity");

        jLabel5.setText("Enter Quantity");

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        QuantityTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        QuantityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuantityTextFieldActionPerformed(evt);
            }
        });
        QuantityTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                QuantityTextFieldFocusGained(evt);
            }
        });
        QuantityTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                QuantityTextFieldKeyPressed(evt);
            }
        });

        RecipeTextField.setText("Enter Recipe Name");
        RecipeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecipeTextFieldActionPerformed(evt);
            }
        });
        RecipeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                RecipeTextFieldFocusGained(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Add a new Recipe");
        jLabel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        addingrButton.setText("Add a new Ingredient");
        addingrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addingrButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(53, 53, 53)
                                .addComponent(RecipeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(addToList, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(23, 23, 23)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(removeFromList, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                            .addComponent(addingrButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                                .addGap(19, 19, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(AddRecipeToDbButton, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(QuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(exitButton))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(RecipeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(addToList, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(addingrButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeFromList))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddRecipeToDbButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(QuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void AddRecipeToDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRecipeToDbButtonActionPerformed

        try
        {
            if(IngredientInRecipeModel.getSize()<=0)
            {
                JOptionPane.showMessageDialog(this, "No ingredient added to recipe");
            }
            
            else if(RecipeTextField.getText().isEmpty() || RecipeTextField.getText().equals("Enter Recipe Here"))
            {
                JOptionPane.showMessageDialog(this, "Recipe Name Invalid");
            }
            
            else if(getRecipeList())
            {
                JOptionPane.showMessageDialog(this, "Name Already Exists");
            }
            
            else 
            {
                AddtoDatabase();
            }
        }
        
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(this, error.getMessage().toString());
        }
        //new MainPage().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_AddRecipeToDbButtonActionPerformed

    private void addToListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToListActionPerformed

        
        String selectedRecipe=IngredientListModel.getElementAt(CurrentList.getSelectedIndex()).toString();
        String quantityString=QuantityTextField.getText();
        int quantity;
        try
        {
            quantity=Integer.parseInt(quantityString);
        }
        
        catch(NumberFormatException error)
        {
            JOptionPane.showMessageDialog(AddRecipeForm.this, "Incorrect Quantity");
            quantity=0;
        }
        
        System.out.println("Quantity:"+quantity);
        int size1=IngredientInRecipeModel.getSize();
        int size2=RequiredQuantityForRecipe.getSize();
        boolean flag1=false;
        
        for(int x=0;x<size1;x++)
        {
            if(selectedRecipe.equals(IngredientInRecipeModel.getElementAt(x)))
            {
                JOptionPane.showMessageDialog(AddRecipeForm.this, "Item already exists");
                flag1=true;
                break;
            }
        }
        if(flag1==false)
        {         
            if(quantityString.equals("null")||quantity==0)
            {
                JOptionPane.showMessageDialog(AddRecipeForm.this, "Please enter quantity correctly");
            }
            
            else
            {
                System.out.println("Inside flag==false");
                IngredientInRecipeModel.addElement(selectedRecipe);
                RequiredQuantityForRecipe.addElement(quantity);
                RequiredQuantityList.ensureIndexIsVisible(size2);
                IngredientsInRecipeList.ensureIndexIsVisible(size1);
                RequiredQuantityList.setSelectedIndex(0);
                IngredientsInRecipeList.setSelectedIndex(0);
                System.out.println("Commands executed");
            }
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_addToListActionPerformed

    private void removeFromListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFromListActionPerformed

        
        String selectedRecipe=IngredientListModel.getElementAt(IngredientsInRecipeList.getSelectedIndex()).toString();
        int selectedIndex=IngredientsInRecipeList.getSelectedIndex();
        IngredientInRecipeModel.removeElementAt(selectedIndex);
        RequiredQuantityForRecipe.removeElementAt(selectedIndex);
        int size1=IngredientInRecipeModel.getSize();
        RequiredQuantityList.ensureIndexIsVisible(size1);
        IngredientsInRecipeList.ensureIndexIsVisible(size1);
        RequiredQuantityList.setSelectedIndex(0);
        IngredientsInRecipeList.setSelectedIndex(0);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_removeFromListActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        
        this.setVisible(false);
        new MainPage().setVisible(true);
        
        try
        {
            con.close();
        }
        
        catch(SQLException error)
        {
            System.out.println(error.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_exitButtonActionPerformed

    private void QuantityTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_QuantityTextFieldFocusGained

        QuantityTextField.setText("0");
        // TODO add your handling code here:
    }//GEN-LAST:event_QuantityTextFieldFocusGained

    private void QuantityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuantityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QuantityTextFieldActionPerformed

    private void RecipeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecipeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RecipeTextFieldActionPerformed

    private void RecipeTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_RecipeTextFieldFocusGained

        RecipeTextField.setText(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_RecipeTextFieldFocusGained

    private void QuantityTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_QuantityTextFieldKeyPressed

        this.getRootPane().setDefaultButton(addToList);
        // TODO add your handling code here:
    }//GEN-LAST:event_QuantityTextFieldKeyPressed

    private void IngredientsInRecipeListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_IngredientsInRecipeListFocusGained

        this.getRootPane().setDefaultButton(removeFromList);
        // TODO add your handling code here:
    }//GEN-LAST:event_IngredientsInRecipeListFocusGained

    private void addingrButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addingrButtonActionPerformed

        this.setVisible(false);
        new AddIngredientForm().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_addingrButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AddRecipeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddRecipeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddRecipeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddRecipeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddRecipeForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddRecipeToDbButton;
    private javax.swing.JList CurrentList;
    private javax.swing.JList IngredientsInRecipeList;
    private javax.swing.JFormattedTextField QuantityTextField;
    private javax.swing.JFormattedTextField RecipeTextField;
    private javax.swing.JList RequiredQuantityList;
    private javax.swing.JButton addToList;
    private javax.swing.JButton addingrButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton removeFromList;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
