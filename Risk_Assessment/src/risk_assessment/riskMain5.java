/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package risk_assessment;
import java.awt.CardLayout;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import org.apache.commons.lang3.Validate;


import java.awt.Color;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author sdhil
 */
public class riskMain5 extends javax.swing.JFrame  {
    
private static ArrayList<String> assetNames=new ArrayList<>();
private static ArrayList<String> assetDes=new ArrayList<>();

private static ArrayList<String> assetNamesRisk=new ArrayList<>();
private static ArrayList<String> risk_vuln=new ArrayList<>();
private static ArrayList<String> likelihood=new ArrayList<>();
private static ArrayList<String> Impact=new ArrayList<>();
private static ArrayList<String> riskScore=new ArrayList<>();

private static ArrayList<String> Threat =new ArrayList<>();
private static ArrayList<String> Threat_Des=new ArrayList<>();

private static ArrayList<String> date_time=new ArrayList<>();
private static ArrayList<String> status=new ArrayList<>();
    /**
     * Creates new form riskMain
     */
    public riskMain5() {
        initComponents();
        jTabbedPane1.setSelectedIndex(3);
        
 
        
     //   initAssetTable();
     //   fillAssetList();
        //setExtendedState(mainPage.MAXIMIZED_BOTH);
        initTables();
        riskMatrix();
           
        //test
        slidertest();
        riskMatrixTable();
        
        hideTabsandText();
    }
    
    private String calcLikelihood;
    private String calcImpact;
    
   private String likelihoodCalcuation;
    private String impactCalcuation;
    
    public void initTables(){
        //Asset Management table
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        Object rowData[] = new Object[3];
        for (int i =0; i < assetNames.size(); i++){
            rowData[1] = assetNames.get(i);
            rowData[2] = assetDes.get(i);
            model.addRow(rowData);
            //delete, testing for navigation with jtable
            model.addRow(rowData);
            model.addRow(rowData);
            list1.add(assetNames.get(i));
        }
        
        //Threat Management Table
        threat_table.getColumnModel().getColumn(0).setPreferredWidth(10);
        DefaultTableModel threat_tableModel = (DefaultTableModel)threat_table.getModel();
        Object ThreatRowData[] = new Object[3];
        System.out.println(Threat.size());
        for (int i =0; i < Threat.size(); i++){
            ThreatRowData[1] = Threat.get(i);
            ThreatRowData[2] = Threat_Des.get(i);
            threat_tableModel.addRow(ThreatRowData);
        }        
        
        //Risk score table
        DefaultTableModel RiskScoreModel = (DefaultTableModel)RiskScoreTable.getModel();
        Object riskRowData[] = new Object[6];
        for (int i = 0; i < assetNamesRisk.size(); i++){
         riskRowData[0] = assetNamesRisk.get(i); 
         riskRowData[1] = risk_vuln.get(i);
         riskRowData[2] = likelihood.get(i);
         riskRowData[3] = Impact.get(i);
         riskRowData[4] = riskScore.get(i);
         likelihoodCalcuation = likelihood.get(i);
         impactCalcuation = Impact.get(i);
         calculateRiskScore();
         RiskScoreModel.addRow(riskRowData);
        }
        
        //homePageTabel
         DefaultTableModel homePage = (DefaultTableModel)homePageTable.getModel();
         Object homePageRowData[] = new Object[2];
         for(int i =0; i < status.size(); i++){
             homePageRowData[0] = date_time.get(i);
             homePageRowData[1]= status.get(i);
             homePage.addRow(homePageRowData);
         }
        
    }
    
    public void updateRiskTable(){
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");
      
        //Check connection 
        if (con != null) {
    	  System.out.println("Success RiskUpdate");
      }
        
        //Get number of rows
        int rowCount = 0;
        String sqlNumofRows = "\"SELECT COUNT(*) FROM risk_score";
        Statement rows = con.createStatement();
        ResultSet setRow = rows.executeQuery(sqlNumofRows);
        if (setRow.next()){
            rowCount = setRow.getInt(1);
            System.out.println("Number of rows:" + rowCount);
    }
        
        //Check if RiskScore is already in table
        
        
        
        
        } catch (Exception e) {
     System.out.println(e);
              }
        
    }
    
    
    
    private int assignedValue; //delete maybe
    private String textValue;

    @Override
    public void layout() {
        super.layout(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    public void calculateRiskScore(){
        
        switch (likelihoodCalcuation) {
            case "Low":
                switch (impactCalcuation) {
                    case "Low":
                        assignedValue = 1;
                        textValue = "Low";
                        break;
                    case "Medium":
                        textValue = "Low";
                        assignedValue = 2;
                        break;
                    default: // High Impact
                        textValue = "Medium";
                        assignedValue = 4;
                        break;
                }
                break;
            case "Medium":
                switch (impactCalcuation) {
                    case "Low":
                        textValue = "Low";
                        assignedValue = 3;
                        break;
                    case "Medium":
                        textValue = "Medium";
                        assignedValue = 5;
                        break;
                    default: // High Impact
                        textValue = "High";
                        assignedValue = 7;
                        break;
                }
                break;
            default: // High Likelihood CHECK THIS
                switch (impactCalcuation) {
                    case "Low":
                        textValue = "Medium";
                        assignedValue = 6;
                        break;
                    case "Medium":
                        textValue = "High";
                        assignedValue = 8;
                        break;
                    default: // High Impact
                        textValue = "Critical";
                        assignedValue = 9;
                        break;
                }
                break;
        }
        System.out.println(assignedValue + " calculateRiskScore() " + textValue);
    
    }
//    
//    public void fillAssetList(){
//        assetSel.removeAllItems();
//        assetSel.addItem("Select");   
//        for(int i = 0; i < assetNames.size(); i++){
//            assetSel.addItem(assetNames.get(i));
//        }
//    } 
    public void hideTabsandText(){
    //jTabbedPane2.setTabLayoutPolicy(jTabbedPane2.SCROLL_TAB_LAYOUT);
    jTabbedPane2.setLayout(new CardLayout()); 
    jLabel30.setText(" ");
    AssetTextOut.setText(" ");
    jLabel36.setText("");
    jLabel37.setText("");
    jLabel96.setText("");
    LikelihoodTextOut.setText("");
    ImpactTextOut.setText("");
    

    
//   jTable3.setRowSelectionAllowed(false);
    
//    jLabel30.setVisible(false);
//    jLabel33.setVisible(false);
//    jLabel31.setVisible(false);
//    jLabel34.setVisible(false);
    //jTabbedPane2.removeTabAt(0);
    }
    
    public void riskMatrix(){
//       jTable2.getModel().getValueAt(2, 2);
    }
    
    
    
    /// Delete
    public void slidertest(){
//     int value = likeSlider.getValue();
//     likeSlideOut.setText(Integer.toString(value));   
     
     //extra waffle
     
    list2.add("Malware");
    list2.add("SQL attack");
    list2.add("Denial of Service attacks");
    
 
    }
    
    public void riskMatrixTable(){
    // jTable2.removeColumn(jTable2.getColumnModel().getColumn(0));
        
        
    }
    public void CalculateFirstFormula(){
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel50 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jLabel101 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jButton6 = new javax.swing.JButton();
        topBar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        One = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Two = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        AssetPanel = new javax.swing.JPanel();
        AssetPanelText = new javax.swing.JLabel();
        Four = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Two1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        Three3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        Three5 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        Three6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        Four1 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        assetJpanel = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        risk_Score = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        RiskScoreTable = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        Filter_Severity = new javax.swing.JButton();
        riskCalcTab = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        AssetTextOut = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        LikelihoodTextOut = new javax.swing.JLabel();
        ImpactTextOut = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        list1 = new java.awt.List();
        jLabel100 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        list2 = new java.awt.List();
        jLabel35 = new javax.swing.JLabel();
        calcRiskPanel = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        attackVectorBox = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        confidentialityBox = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        financialBox = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        reputationBox = new javax.swing.JComboBox<>();
        nonComplianceBox = new javax.swing.JComboBox<>();
        disruptionBox = new javax.swing.JComboBox<>();
        attackComplexityBox = new javax.swing.JComboBox<>();
        privilegesRequiredBox = new javax.swing.JComboBox<>();
        scopeBox = new javax.swing.JComboBox<>();
        integrityBox = new javax.swing.JComboBox<>();
        availabilityBox = new javax.swing.JComboBox<>();
        tracabilityBox = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        riskScoreTextOut = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        Next = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        homePageTable = new javax.swing.JTable();
        ThreatJPanel = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        threat_table = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextPane5 = new javax.swing.JTextPane();
        jLabel8 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jFrame1.setAlwaysOnTop(true);
        jFrame1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jFrame1PropertyChange(evt);
            }
        });
        jFrame1.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                jFrame1WindowActivated(evt);
            }
        });
        jFrame1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFrame1KeyPressed(evt);
            }
        });

        jLabel92.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel92.setText("Asset Registry:");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel93.setText("Name:");

        jScrollPane6.setViewportView(jTextPane2);

        jLabel101.setText("Description:");

        jScrollPane7.setViewportView(jTextPane3);

        jButton6.setText("Save");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(jButton6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1680, 880));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topBar.setBackground(new java.awt.Color(255, 255, 255));
        topBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/risk_assessment/assets/MainLogo.png"))); // NOI18N

        javax.swing.GroupLayout topBarLayout = new javax.swing.GroupLayout(topBar);
        topBar.setLayout(topBarLayout);
        topBarLayout.setHorizontalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topBarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1630, Short.MAX_VALUE))
        );
        topBarLayout.setVerticalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topBarLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(topBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 60));

        jPanel1.setBackground(new java.awt.Color(44, 195, 112));

        One.setBackground(new java.awt.Color(44, 195, 112));
        One.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        One.setMinimumSize(new java.awt.Dimension(200, 111));
        One.setPreferredSize(new java.awt.Dimension(200, 111));
        One.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                OneMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                OneMouseExited(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/risk_assessment/assets/Home.png"))); // NOI18N

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Cambria Math", 0, 18)); // NOI18N
        jLabel1.setText("My Dashboard");

        javax.swing.GroupLayout OneLayout = new javax.swing.GroupLayout(One);
        One.setLayout(OneLayout);
        OneLayout.setHorizontalGroup(
            OneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OneLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel3)
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        OneLayout.setVerticalGroup(
            OneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Two.setBackground(new java.awt.Color(44, 195, 112));
        Two.setMinimumSize(new java.awt.Dimension(200, 111));
        Two.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TwoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TwoMouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Management");

        javax.swing.GroupLayout TwoLayout = new javax.swing.GroupLayout(Two);
        Two.setLayout(TwoLayout);
        TwoLayout.setHorizontalGroup(
            TwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TwoLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TwoLayout.setVerticalGroup(
            TwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TwoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AssetPanel.setBackground(new java.awt.Color(44, 195, 112));
        AssetPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AssetPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        AssetPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AssetPanelMouseClicked(evt);
            }
        });
        AssetPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AssetPanelKeyPressed(evt);
            }
        });

        AssetPanelText.setText("Assets");
        AssetPanelText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AssetPanelTextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout AssetPanelLayout = new javax.swing.GroupLayout(AssetPanel);
        AssetPanel.setLayout(AssetPanelLayout);
        AssetPanelLayout.setHorizontalGroup(
            AssetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssetPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(AssetPanelText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AssetPanelLayout.setVerticalGroup(
            AssetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssetPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(AssetPanelText)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        Four.setBackground(new java.awt.Color(44, 195, 112));
        Four.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Four.setMinimumSize(new java.awt.Dimension(200, 111));
        Four.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FourMouseClicked(evt);
            }
        });

        jLabel6.setText("Threat");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout FourLayout = new javax.swing.GroupLayout(Four);
        Four.setLayout(FourLayout);
        FourLayout.setHorizontalGroup(
            FourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FourLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel6)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        FourLayout.setVerticalGroup(
            FourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FourLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Two1.setBackground(new java.awt.Color(44, 195, 112));
        Two1.setMinimumSize(new java.awt.Dimension(200, 111));
        Two1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Two1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Two1MouseExited(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("Features");

        javax.swing.GroupLayout Two1Layout = new javax.swing.GroupLayout(Two1);
        Two1.setLayout(Two1Layout);
        Two1Layout.setHorizontalGroup(
            Two1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Two1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(64, 64, 64))
        );
        Two1Layout.setVerticalGroup(
            Two1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Two1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Three3.setBackground(new java.awt.Color(44, 195, 112));
        Three3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Three3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Three3MouseClicked(evt);
            }
        });

        jLabel18.setText("Risk Calculator");

        javax.swing.GroupLayout Three3Layout = new javax.swing.GroupLayout(Three3);
        Three3.setLayout(Three3Layout);
        Three3Layout.setHorizontalGroup(
            Three3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Three3Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Three3Layout.setVerticalGroup(
            Three3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Three3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        Three5.setBackground(new java.awt.Color(44, 195, 112));
        Three5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setText("Audit");

        javax.swing.GroupLayout Three5Layout = new javax.swing.GroupLayout(Three5);
        Three5.setLayout(Three5Layout);
        Three5Layout.setHorizontalGroup(
            Three5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Three5Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Three5Layout.setVerticalGroup(
            Three5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Three5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        Three6.setBackground(new java.awt.Color(44, 195, 112));
        Three6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setText("Help");

        javax.swing.GroupLayout Three6Layout = new javax.swing.GroupLayout(Three6);
        Three6.setLayout(Three6Layout);
        Three6Layout.setHorizontalGroup(
            Three6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Three6Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Three6Layout.setVerticalGroup(
            Three6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Three6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        Four1.setBackground(new java.awt.Color(44, 195, 112));
        Four1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Four1.setMinimumSize(new java.awt.Dimension(200, 111));
        Four1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Four1MouseClicked(evt);
            }
        });

        jLabel31.setText("Risk Score");
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Four1Layout = new javax.swing.GroupLayout(Four1);
        Four1.setLayout(Four1Layout);
        Four1Layout.setHorizontalGroup(
            Four1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Four1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Four1Layout.setVerticalGroup(
            Four1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Four1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(One, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(Two, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Four, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AssetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Two1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Three3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Three5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Three6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Four1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(One, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Two, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(AssetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Four, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Four1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Two1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Three3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Three5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Three6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(416, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 200, 960));

        jLabel97.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel97.setText("Asset Management:");

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tick", "Assets", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setRowHeight(60);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(30);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(2).setHeaderValue("Likelihood");
        }

        jTextPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jScrollPane5.setViewportView(jTextPane1);

        jLabel7.setText("Search:");

        jButton4.setText("Go");

        jLabel32.setText("Showing: 42 Assets");

        jButton5.setText("Create_New");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout assetJpanelLayout = new javax.swing.GroupLayout(assetJpanel);
        assetJpanel.setLayout(assetJpanelLayout);
        assetJpanelLayout.setHorizontalGroup(
            assetJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetJpanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(assetJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        assetJpanelLayout.setVerticalGroup(
            assetJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetJpanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab3", assetJpanel);

        jLabel102.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel102.setText("Risk Score Management:");

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));

        RiskScoreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AssetName/s", "Risk Vulnerability", "Risk Likelihood", "Risk_Impact", "Risk Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        RiskScoreTable.setRowHeight(60);
        jScrollPane4.setViewportView(RiskScoreTable);

        jLabel48.setText("Showing: 23");

        Filter_Severity.setText("Filter Severity");
        Filter_Severity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Filter_SeverityMouseClicked(evt);
            }
        });
        Filter_Severity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Filter_SeverityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Filter_Severity, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Filter_Severity, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout risk_ScoreLayout = new javax.swing.GroupLayout(risk_Score);
        risk_Score.setLayout(risk_ScoreLayout);
        risk_ScoreLayout.setHorizontalGroup(
            risk_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(risk_ScoreLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(risk_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(291, Short.MAX_VALUE))
        );
        risk_ScoreLayout.setVerticalGroup(
            risk_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(risk_ScoreLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(360, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", risk_Score);

        jPanel9.setBackground(new java.awt.Color(44, 195, 112));
        jPanel9.setPreferredSize(new java.awt.Dimension(245, 64));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setText("Select Asset");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        jPanel11.setBackground(new java.awt.Color(44, 195, 112));

        jLabel29.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText(" Summary");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel30.setText("Asset:");

        AssetTextOut.setText("0");

        jLabel36.setText("Threat:");

        jLabel37.setText("0");

        jLabel96.setText("Risk Score:");

        LikelihoodTextOut.setText("0");

        ImpactTextOut.setText("0");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGap(46, 46, 46)
                            .addComponent(AssetTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ImpactTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LikelihoodTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AssetTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LikelihoodTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ImpactTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 253, Short.MAX_VALUE))
        );

        list1.setMultipleMode(true);
        list1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                list1ActionPerformed(evt);
            }
        });

        jLabel100.setText("Select Assets: ");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(list1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel100)
                .addContainerGap(976, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("tab1", jPanel13);

        jLabel35.setText("Select Threats: ");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addComponent(list2, javax.swing.GroupLayout.PREFERRED_SIZE, 1302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(list2, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("tab2", jPanel20);

        jLabel38.setFont(new java.awt.Font("Segoe UI Historic", 0, 18)); // NOI18N
        jLabel38.setText("Risk Rating Calculator");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setText("Vulnerability Identificaiton:");

        jLabel40.setText("Attack Vector:");

        jLabel41.setText("Attack Complexity:");

        jLabel42.setText("Privileges Required:");

        jLabel43.setText("Scope:");

        attackVectorBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel44.setText("CIA Impact Factors:");

        jLabel46.setText("Loss of Confidentiality:");

        jLabel47.setText("Loss of Integrity:");

        jLabel52.setText("Loss of Availability:");

        jLabel53.setText("Loss of Traceability:");

        confidentialityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jPanel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel56.setText("Consequences:");
        jLabel56.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel57.setText("Financial Damage:");

        financialBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel60.setText("Reputation Damage:");

        jLabel61.setText("Non-Compliance:");

        jLabel62.setText("Disruption of Service:");

        reputationBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        nonComplianceBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        disruptionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57)
                    .addComponent(financialBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60)
                    .addComponent(reputationBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61)
                    .addComponent(nonComplianceBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(disruptionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel60)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(financialBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reputationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nonComplianceBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(disruptionBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 61, Short.MAX_VALUE))
        );

        attackComplexityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        privilegesRequiredBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        scopeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        integrityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        availabilityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        tracabilityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        javax.swing.GroupLayout calcRiskPanelLayout = new javax.swing.GroupLayout(calcRiskPanel);
        calcRiskPanel.setLayout(calcRiskPanelLayout);
        calcRiskPanelLayout.setHorizontalGroup(
            calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calcRiskPanelLayout.createSequentialGroup()
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(calcRiskPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(attackVectorBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(calcRiskPanelLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel45))
                            .addGroup(calcRiskPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(calcRiskPanelLayout.createSequentialGroup()
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel46)
                                            .addComponent(jLabel40)
                                            .addComponent(confidentialityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel41)
                                            .addComponent(jLabel47)
                                            .addComponent(attackComplexityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(integrityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel42)
                                            .addComponent(jLabel52)
                                            .addComponent(privilegesRequiredBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(availabilityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tracabilityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(scopeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel53)))
                                        .addGap(152, 152, 152))
                                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(calcRiskPanelLayout.createSequentialGroup()
                        .addGap(494, 494, 494)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        calcRiskPanelLayout.setVerticalGroup(
            calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calcRiskPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(attackVectorBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attackComplexityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(privilegesRequiredBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scopeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addGap(12, 12, 12)
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confidentialityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(integrityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(availabilityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tracabilityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab3", calcRiskPanel);

        jPanel39.setBackground(new java.awt.Color(255, 102, 0));

        jLabel85.setText("Medium - 4");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel85)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel40.setBackground(new java.awt.Color(255, 255, 0));

        jLabel84.setText("Low - 2");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel84)
                .addGap(26, 26, 26))
        );

        jPanel43.setBackground(new java.awt.Color(255, 102, 0));

        jLabel87.setText("Medium - 5");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel87)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel44.setBackground(new java.awt.Color(255, 0, 0));

        jLabel89.setText("High - 7");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel89)
                .addGap(26, 26, 26))
        );

        jPanel45.setBackground(new java.awt.Color(153, 0, 0));

        jLabel91.setText("Critical - 9+");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel91)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel46.setBackground(new java.awt.Color(255, 0, 0));

        jLabel90.setText("High - 8");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel90)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel47.setBackground(new java.awt.Color(255, 102, 0));

        jLabel88.setText("Medium - 6");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel88)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel75.setText("High");

        jLabel76.setText("Low");

        jLabel77.setText("Medium");

        jLabel78.setText("Low");

        jLabel79.setText("Medium");

        jLabel80.setText("High");

        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel81.setText("Impact");

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel82.setText("Likelihood");

        jPanel48.setBackground(new java.awt.Color(51, 255, 0));

        jLabel83.setText("Minimal - 1");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel83)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel83)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel49.setBackground(new java.awt.Color(255, 255, 0));

        jLabel86.setText("Low - 3");

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel49Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(jLabel86)
                .addGap(71, 71, 71))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel86)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel41.setBackground(new java.awt.Color(153, 153, 153));
        jPanel41.setForeground(new java.awt.Color(153, 153, 153));

        jLabel94.setText("Your risk Score: ");

        riskScoreTextOut.setText("0");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(riskScoreTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(773, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(riskScoreTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel78)
                                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(402, 402, 402)
                        .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(176, 176, 176)
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab8", jPanel15);

        jPanel17.setBackground(new java.awt.Color(44, 195, 112));
        jPanel17.setPreferredSize(new java.awt.Dimension(245, 64));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel63.setText("Select Threats");
        jPanel17.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jPanel18.setBackground(new java.awt.Color(44, 195, 112));
        jPanel18.setPreferredSize(new java.awt.Dimension(245, 64));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setText("Risk Assessment");
        jPanel18.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jPanel10.setBackground(new java.awt.Color(44, 195, 112));
        jPanel10.setPreferredSize(new java.awt.Dimension(245, 64));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel58.setText("Save");
        jPanel10.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jButton2.setText("Back");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        Next.setText("Next");
        Next.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NextMouseClicked(evt);
            }
        });
        Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout riskCalcTabLayout = new javax.swing.GroupLayout(riskCalcTab);
        riskCalcTab.setLayout(riskCalcTabLayout);
        riskCalcTabLayout.setHorizontalGroup(
            riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(riskCalcTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(riskCalcTabLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(riskCalcTabLayout.createSequentialGroup()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(riskCalcTabLayout.createSequentialGroup()
                        .addGap(0, 1110, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(310, Short.MAX_VALUE))
        );
        riskCalcTabLayout.setVerticalGroup(
            riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(riskCalcTabLayout.createSequentialGroup()
                .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE)
                .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(243, 243, 243))
        );

        jTabbedPane1.addTab("tab4", riskCalcTab);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Assets:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("23");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(248, 82));

        jLabel10.setText("Risks Calcualted:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("13");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(248, 82));

        jLabel19.setText("Vulnerabilitys:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setText("13");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(248, 82));

        jLabel23.setText("Remaining Assets for Risk assessmet:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("10");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        homePageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Risk Computed"
            }
        ));
        jScrollPane10.setViewportView(homePageTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(598, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(257, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel2);

        jLabel103.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel103.setText("Threat Management:");

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));

        threat_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tick", "Threat", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        threat_table.setRowHeight(60);
        jScrollPane2.setViewportView(threat_table);
        if (threat_table.getColumnModel().getColumnCount() > 0) {
            threat_table.getColumnModel().getColumn(0).setMinWidth(30);
            threat_table.getColumnModel().getColumn(0).setPreferredWidth(30);
            threat_table.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        jTextPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jTextPane5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jScrollPane9.setViewportView(jTextPane5);

        jLabel8.setText("Search:");

        jButton9.setText("Go");

        jLabel49.setText("Showing: 42 Assets");

        jButton10.setText("Create_New");
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton10MouseClicked(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout ThreatJPanelLayout = new javax.swing.GroupLayout(ThreatJPanel);
        ThreatJPanel.setLayout(ThreatJPanelLayout);
        ThreatJPanelLayout.setHorizontalGroup(
            ThreatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatJPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(ThreatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        ThreatJPanelLayout.setVerticalGroup(
            ThreatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatJPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", ThreatJPanel);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 1720, 1050));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Hover effect
    private void OneMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OneMouseEntered
        One.setBackground(Color.white);
    }//GEN-LAST:event_OneMouseEntered

    private void OneMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OneMouseExited
        One.setBackground(new Color(44,195,112));
    }//GEN-LAST:event_OneMouseExited

    private void TwoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TwoMouseEntered
        Two.setBackground(Color.white);
    }//GEN-LAST:event_TwoMouseEntered

    private void TwoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TwoMouseExited
        Two.setBackground(new Color(44,195,112));
    }//GEN-LAST:event_TwoMouseExited

    private void Two1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Two1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Two1MouseEntered

    private void Two1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Two1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Two1MouseExited

    private void FourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FourMouseClicked
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_FourMouseClicked

    private int conseq = 0;
    private int threat = 0;
    private int likely = 0;
    
    private void Three3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Three3MouseClicked
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_Three3MouseClicked

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NextActionPerformed
    
    private int result;
    String selected;
    String assetsSelected;
    String vuln;
    private void NextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextMouseClicked

if (jTabbedPane2.getSelectedIndex() == 0) {
    String[] selecteditems = list1.getSelectedItems();
    StringBuilder sb = new StringBuilder();
    for (String items: selecteditems){
     sb.append(items).append(", ");
        assetsSelected = sb.toString();
    }
    jLabel30.setText("Asset: ");
    AssetTextOut.setText(assetsSelected);
    
    jTabbedPane2.setSelectedIndex(1);
} else if (jTabbedPane2.getSelectedIndex() == 1) {
    jLabel36.setText("Threats");
    vuln = list2.getSelectedItem();
    jLabel37.setText(vuln);
    jTabbedPane2.setSelectedIndex(2);
    
    
    
} else if (jTabbedPane2.getSelectedIndex() == 2) {
    int vulnerabilityID = (attackVectorBox.getSelectedIndex() + attackComplexityBox.getSelectedIndex() + privilegesRequiredBox.getSelectedIndex() + scopeBox.getSelectedIndex());
    int CIAimpact = (confidentialityBox.getSelectedIndex() + integrityBox.getSelectedIndex() + availabilityBox.getSelectedIndex() + tracabilityBox.getSelectedIndex());
    int consequences = (financialBox.getSelectedIndex() + reputationBox.getSelectedIndex() + nonComplianceBox.getSelectedIndex() + disruptionBox.getSelectedIndex());
    
    if (attackVectorBox.getSelectedIndex() == 0 || attackComplexityBox.getSelectedIndex() == 0 || privilegesRequiredBox.getSelectedIndex() == 0 || scopeBox.getSelectedIndex() == 0) {
        System.out.println("Vulnerbailty is 0");
    } else if (confidentialityBox.getSelectedIndex() == 0 || integrityBox.getSelectedIndex() == 0 || availabilityBox.getSelectedIndex() == 0 || tracabilityBox.getSelectedIndex() == 0) {
        System.out.println("CIA is 0");
    } else if (financialBox.getSelectedIndex() == 0 || reputationBox.getSelectedIndex() == 0 || nonComplianceBox.getSelectedIndex() == 0 || disruptionBox.getSelectedIndex() == 0) {
        System.out.println("Consequences is 0");
    } else {
       float Vuln = (float) vulnerabilityID / 4;
    float CIA = (float) CIAimpact / 4;
    float Cons = (float) consequences / 4;
    double Likelihood = Vuln;
    double Impact = (CIA + Cons) / 2;
    String likelihoodOutput = "";
    String ImpactOutput = "";
    // likelihood checker 
    if (Likelihood >= 0 && Likelihood < 3) {
        likelihoodOutput = "Low";
        System.out.println(likelihoodOutput);
    } else if (Likelihood >= 3 && Likelihood <= 6) {
        likelihoodOutput = "Medium";
        System.out.println(likelihoodOutput);
    } else if (Likelihood >= 6) {
        likelihoodOutput = "High";
        System.out.println(likelihoodOutput);
    } else {
        System.out.println("Likelihood checker error");
    }
    // impact checker
    if (Impact < 3) {
        ImpactOutput = "Low";
        System.out.println(ImpactOutput);
    } else if (Impact >= 3 && Likelihood <= 6) {
        ImpactOutput = "Medium";
        System.out.println(ImpactOutput);
    } else if (Impact >= 6.0) {
        ImpactOutput = "High";
        System.out.println(ImpactOutput);
    } else {
        System.out.println("IMPACT checker");
    }
    jLabel96.setText("Risk Score");
    likelihoodCalcuation = likelihoodOutput;
    impactCalcuation = ImpactOutput;
    calculateRiskScore();
    
    LikelihoodTextOut.setText(likelihoodOutput);
    ImpactTextOut.setText(ImpactOutput);
    jTabbedPane2.setSelectedIndex(3);
    riskScoreTextOut.setText("Risk Score: " + assignedValue);
    }
    

} else if (jTabbedPane2.getSelectedIndex() == 3) {
Next.setText("Save");
 try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");
        //Check connection 
        if (con != null) {
    	  System.out.println("Success");
      }
     
    //Get number of rows from risk_score database
    int rowCount = 0;
    String sqlNumofRows = "SELECT COUNT(*) FROM risk_score ";
    Statement rows = con.createStatement();
    ResultSet setRow = rows.executeQuery(sqlNumofRows);
    if (setRow.next()){
        rowCount = setRow.getInt(1);
        System.out.println("Number of rows:" + rowCount);
    }
   
      String sql = "INSERT INTO risk_score (idrisk_Score, Asset_Names, risk_vuln, risk_likelihood, risk_Impact, risk_Score) VALUES ('"+(rowCount+1)+"', '"+assetsSelected+"', '"+vuln+"', '"+likelihoodCalcuation+"','"+impactCalcuation+"','"+impactCalcuation+"')";
      Statement stmt=con.createStatement();
      int rowsInserted = stmt.executeUpdate(sql);
      
      //Determine time if successful or not
      DefaultTableModel model = (DefaultTableModel)homePageTable.getModel();
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String formattedDateTime = now.format(formatter);
      
    //Get number of rows from risk_change  database
        int rowCountChange = 0;
        String NumofRows = "SELECT COUNT(*) FROM risk_change ";
        Statement Numrows = con.createStatement();
        ResultSet setRows = Numrows.executeQuery(NumofRows);
        if (setRows.next()){
            rowCountChange = setRows.getInt(1);
            System.out.println("Number of rows:" + rowCountChange);
        } 
        

  //    String riskComputedSQL = "INSERT INTO risk_change (`Row`, `Time`, `risk_status`) VALUES ('"+(rowCountChange+1)+"', "+formattedDateTime+"', 'Success');";
    //  Statement stmts=con.createStatement();
    //  int rowsInserteds = stmts.executeUpdate(sql);
      String riskComputedSQL;
      if (rowsInserted > 0){
          System.out.println(rowsInserted + " Rows inserted.");
          jTabbedPane1.setSelectedIndex(3);
          riskComputedSQL = "INSERT INTO risk_change (`Row`, `Time`, `risk_status`) VALUES ('" + (rowCountChange + 1) + "', '" + formattedDateTime + "', 'Success');";
//          model.addRow(new Object[] {formattedDateTime, "Success"});
          
      } else {
       System.out.println("No rows Inserted");
       jTabbedPane1.setSelectedIndex(3);
         riskComputedSQL = "INSERT INTO risk_change (`Row`, `Time`, `risk_status`) VALUES ('" + (rowCountChange + 1) + "', '" + formattedDateTime + "', 'Failure');";
//       model.addRow(new Object[] {formattedDateTime, "Failure"});
      }
    Statement riskComputedStmt = con.createStatement();
    int riskComputedRowsInserted = riskComputedStmt.executeUpdate(riskComputedSQL);
    
    //riskMain5.sqlQuerys(); 
      //Finish Connection
      con.close();
      
      
  } catch (Exception e) {
     System.out.println(e);
  }


} 
    }//GEN-LAST:event_NextMouseClicked

    public void riskScoreTabInit(){
        
        
    }
    
    
    private void list1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_list1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_list1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
     if (jTabbedPane2.getModel().getSelectedIndex() == 0){
       System.out.println("Out Of Bounds");
     } else {
     jTabbedPane2.setSelectedIndex(jTabbedPane2.getSelectedIndex()-1);
    
    }//GEN-LAST:event_jButton2MouseClicked
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        assetReg s = new assetReg();
        s.setLocationRelativeTo(null);
        s.setVisible(true);  
    }//GEN-LAST:event_jButton5MouseClicked

    private void jFrame1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jFrame1PropertyChange
        
    }//GEN-LAST:event_jFrame1PropertyChange

    private void jFrame1WindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jFrame1WindowActivated
        
    }//GEN-LAST:event_jFrame1WindowActivated

    private void jFrame1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFrame1KeyPressed
   
    }//GEN-LAST:event_jFrame1KeyPressed

    private void Four1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Four1MouseClicked
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_Four1MouseClicked

    private void Filter_SeverityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Filter_SeverityMouseClicked
        
        
        if (Filter_Severity.getText() == "Filter Severity"){
           // RiskScoreTable.getColumn(riskScore);

        //   model.setRowCount(0);
           
           for (int i=0; i < riskScore.size(); i++){
               if (riskScore.get(i) == "Low"){
                 
             } else if (riskScore.get(i) == "Medium"){
                 
                 
             }else if (riskScore.get(i) == "High"){
                 
           } else {
                 break;
             }
           }
            
            
            Filter_Severity.setText("Filter Normal");
        } else if (Filter_Severity.getText() == "Filter Normal"){
            
            
            Filter_Severity.setText("Filter Severity");
        }
    }//GEN-LAST:event_Filter_SeverityMouseClicked

    private void Filter_SeverityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Filter_SeverityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Filter_SeverityActionPerformed

    private void AssetPanelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AssetPanelKeyPressed
     //delete
    }//GEN-LAST:event_AssetPanelKeyPressed

    private void AssetPanelTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AssetPanelTextMouseClicked
          jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_AssetPanelTextMouseClicked

    private void AssetPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AssetPanelMouseClicked
           jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_AssetPanelMouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
           jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10MouseClicked
    
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt){ 
        System.out.println("Clicked");
        
    }
    
    public static void InitsqlQuerys(){
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");
      
      //Check connection
      if (con != null) {
    	  System.out.println("Success");
      }
   
      //Asset Table Input
      Statement stmt=con.createStatement();
     ResultSet assetNamez=stmt.executeQuery("SELECT * FROM assets");
     while (assetNamez.next()) {      
    	 assetNames.add(assetNamez.getString("AssetName"));
         assetDes.add(assetNamez.getString("Description"));
     }
     
     //Threat Table Input
     Statement threatState=con.createStatement();
     ResultSet threatResultSet=threatState.executeQuery("SELECT * FROM threats");
     while (threatResultSet.next()) {      
    	 Threat.add(threatResultSet.getString("Threat"));
         Threat_Des.add(threatResultSet.getString("Threat_Description"));
         //System.out.println(Threat.size());
     }
      
     //RiskScoreTable Input
     Statement risk=con.createStatement();
     ResultSet riskResults=risk.executeQuery("SELECT * FROM risk_Score");
     while (riskResults.next()){
         assetNamesRisk.add(riskResults.getString("Asset_Names"));
         risk_vuln.add(riskResults.getString("risk_vuln"));
         likelihood.add(riskResults.getString("risk_likelihood"));
         Impact.add(riskResults.getString("risk_Impact"));
         riskScore.add(riskResults.getString("risk_Score"));
     }
     
     //homePageTable Input
     Statement homePage=con.createStatement();
     ResultSet homePageResults=risk.executeQuery("SELECT * FROM risk_change");
     while (homePageResults.next()){
         date_time.add(homePageResults.getString("Time"));
         status.add(homePageResults.getString("risk_status"));
     }

     
     
      //Finish Connection
      con.close(); 
    } catch (Exception e) {
     System.out.println(e);
  }
        
    }
 
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
            java.util.logging.Logger.getLogger(riskMain5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(riskMain5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(riskMain5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(riskMain5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new riskMain5().setVisible(true);
            }
        });
        
        InitsqlQuerys();
   ///////////////////////////////////////////////////////////////////////////////////     

       
   
 //////////////////////////////////////////////////////////////////////////////////////       
        
       
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AssetPanel;
    private javax.swing.JLabel AssetPanelText;
    private javax.swing.JLabel AssetTextOut;
    private javax.swing.JButton Filter_Severity;
    private javax.swing.JPanel Four;
    private javax.swing.JPanel Four1;
    private javax.swing.JLabel ImpactTextOut;
    private javax.swing.JLabel LikelihoodTextOut;
    private javax.swing.JButton Next;
    private javax.swing.JPanel One;
    private javax.swing.JTable RiskScoreTable;
    private javax.swing.JPanel ThreatJPanel;
    private javax.swing.JPanel Three3;
    private javax.swing.JPanel Three5;
    private javax.swing.JPanel Three6;
    private javax.swing.JPanel Two;
    private javax.swing.JPanel Two1;
    private javax.swing.JPanel assetJpanel;
    private javax.swing.JComboBox<String> attackComplexityBox;
    private javax.swing.JComboBox<String> attackVectorBox;
    private javax.swing.JComboBox<String> availabilityBox;
    private javax.swing.JPanel calcRiskPanel;
    private javax.swing.JComboBox<String> confidentialityBox;
    private javax.swing.JComboBox<String> disruptionBox;
    private javax.swing.JComboBox<String> financialBox;
    private javax.swing.JTable homePageTable;
    private javax.swing.JComboBox<String> integrityBox;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane5;
    private java.awt.List list1;
    private java.awt.List list2;
    private javax.swing.JComboBox<String> nonComplianceBox;
    private javax.swing.JComboBox<String> privilegesRequiredBox;
    private javax.swing.JComboBox<String> reputationBox;
    private javax.swing.JPanel riskCalcTab;
    private javax.swing.JLabel riskScoreTextOut;
    private javax.swing.JPanel risk_Score;
    private javax.swing.JComboBox<String> scopeBox;
    private javax.swing.JTable threat_table;
    private javax.swing.JPanel topBar;
    private javax.swing.JComboBox<String> tracabilityBox;
    // End of variables declaration//GEN-END:variables
}
