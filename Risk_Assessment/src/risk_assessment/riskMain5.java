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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JTable;
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

private String calcLikelihood;
private String calcImpact;
    
private String likelihoodCalcuation;
private String impactCalcuation;
    /**
     * Creates new form riskMain
     */
    public riskMain5() {
        initComponents();
        Tab_tabbedPane.setSelectedIndex(3);
        initTables();  
        hideTabsandText();
    }
    

    
    public void setHome(){
    Tab_tabbedPane.setSelectedIndex(3);
    }
    
    
/**
 * Initializes and populates four tables for asset management, threat management,
 * risk score calculation, and home page display in the application. It populates
 * the tables using data stored in various lists.
 * 
 * 1. Asset Management Table:
 *    - Sets the first column width of the assetTable.
 *    - Creates a DefaultTableModel object associated with the assetTable.
 *    - Updates the table with assetNames and assetDes data.
 *    - Updates the Asset_Showing and AssetHomepage_Text components with the number of assets.
 *
 * 2. Threat Management Table:
 *    - Sets the first column width of the threat_table.
 *    - Creates a DefaultTableModel object associated with the threat_table.
 *    - Updates the table with Threat and Threat_Des data.
 *    - Updates the Showing_threats and Vuln_homepageLabel components with the number of threats.
 * 
 * 3. Risk Score Table:
 *    - Creates a DefaultTableModel object associated with the RiskScoreTable.
 *    - Updates the table with assetNamesRisk, risk_vuln, likelihood, Impact, and riskScore data.
 *    - Updates the Showing_RiskScore and RiskCalc_Homepage components with the number of risk scores.
 * 
 * 4. Home Page Table:
 *    - Creates a DefaultTableModel object associated with the homePageTable.
 *    - Updates the table with date_time and status data.
 */
    public void initTables(){
        //Asset Management table
        assetTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        DefaultTableModel model = (DefaultTableModel)assetTable.getModel();
        model.setRowCount(0);
        AssetList.clear();
        Object rowData[] = new Object[3];
        for (int i =0; i < assetNames.size(); i++){
            rowData[1] = assetNames.get(i);
            rowData[2] = assetDes.get(i);
            model.addRow(rowData);   
            AssetList.add(assetNames.get(i));
        }
 
         Asset_Showing.setText("Showing:" + assetNames.size()+ " Assets");
        AssetHomepage_Text.setText(String.valueOf(assetNames.size()));

        //threat Management Table
        threat_table.getColumnModel().getColumn(0).setPreferredWidth(10);
        DefaultTableModel threat_tableModel = (DefaultTableModel)threat_table.getModel();
        threat_tableModel.setRowCount(0);
        ThreatList.clear();
        Object ThreatRowData[] = new Object[3];
        System.out.println(Threat.size());
        for (int i =0; i < Threat.size(); i++){
            ThreatRowData[1] = Threat.get(i);
            ThreatRowData[2] = Threat_Des.get(i);
            ThreatList.add(Threat.get(i));
            threat_tableModel.addRow(ThreatRowData);
        }        
        Showing_threats.setText("Showing:" + Threat.size()+ " Threats");
        Vuln_homepageLabel.setText(String.valueOf(assetNames.size()));
        
        //Risk score table
        DefaultTableModel RiskScoreModel = (DefaultTableModel)RiskScoreTable.getModel();
        Object riskRowData[] = new Object[6];
         RiskScoreModel.setRowCount(0);
        for (int i = 0; i < assetNamesRisk.size(); i++){
         riskRowData[0] = assetNamesRisk.get(i); 
         riskRowData[1] = risk_vuln.get(i);
         riskRowData[2] = likelihood.get(i);
         riskRowData[3] = Impact.get(i);
         riskRowData[4] = riskScore.get(i);
         likelihoodCalcuation = likelihood.get(i);
         impactCalcuation = Impact.get(i);
         RiskScoreModel.addRow(riskRowData);
        }
        Showing_RiskScore.setText("Showing:" + assetNamesRisk.size()+ " Risk Scores");
        RiskCalc_Homepage.setText(String.valueOf(assetNamesRisk.size()));
        
        //homePageTabel
         DefaultTableModel homePage = (DefaultTableModel)homePageTable.getModel();
         Object homePageRowData[] = new Object[2];
         for(int i =0; i < status.size(); i++){
             homePageRowData[0] = date_time.get(i);
             homePageRowData[1]= status.get(i);
             homePage.addRow(homePageRowData);
         }
        
    }
    /**
 * Updates the "risk_score" table in the "test" database.
 * This method retrieves the number of rows in the "risk_score" table and prints it to the console.
 * This method does not modify any records in the table.
 */
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

        } catch (Exception e) {
     System.out.println(e);
              }
        
    }
    

    @Override
    public void layout() {
        super.layout(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public void hideTabsandText(){
    //jTabbedPane2.setTabLayoutPolicy(jTabbedPane2.SCROLL_TAB_LAYOUT);
    jTabbedPane2.setLayout(new CardLayout()); 
    SummaryAssetText.setText(" ");
    SummarySelectedAssetText.setText(" ");
    SummaryThreatText.setText("");
    SummaryThreatSelectionText.setText("");
    SummaryRiskScoreText.setText("");
    SummaryLikelihoodText.setText("");
    SummaryImpactTextOut.setText("");
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
        LogoLabel = new javax.swing.JLabel();
        BackgroundMain = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        NavigationBarPanel = new javax.swing.JPanel();
        DashboardPanel = new javax.swing.JPanel();
        Home_Icon = new javax.swing.JLabel();
        DashboardJLabel = new javax.swing.JLabel();
        ManagementPanel = new javax.swing.JPanel();
        ManagementJLabel = new javax.swing.JLabel();
        AssetPanel = new javax.swing.JPanel();
        AssetPanelText = new javax.swing.JLabel();
        ThreatPanel = new javax.swing.JPanel();
        ThreatLabel = new javax.swing.JLabel();
        FeaturesPanel = new javax.swing.JPanel();
        FeaturesLabel = new javax.swing.JLabel();
        RiskCalcPanel = new javax.swing.JPanel();
        RiskCalcText = new javax.swing.JLabel();
        AuditPanel = new javax.swing.JPanel();
        AuditText = new javax.swing.JLabel();
        HelpPanel = new javax.swing.JPanel();
        HelpText = new javax.swing.JLabel();
        RiskScorePanel = new javax.swing.JPanel();
        RiskScoreText = new javax.swing.JLabel();
        Tab_tabbedPane = new javax.swing.JTabbedPane();
        assetJpanel = new javax.swing.JPanel();
        AssetManagmentText = new javax.swing.JLabel();
        AssetTableBackgroundPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        assetTable = new javax.swing.JTable();
        Asset_Showing = new javax.swing.JLabel();
        createNewAsset = new javax.swing.JButton();
        AssetUpdateBtn = new javax.swing.JButton();
        AssetDeleteBtn = new javax.swing.JButton();
        risk_Score = new javax.swing.JPanel();
        RiskScoreManagementText = new javax.swing.JLabel();
        RiskScoreTableBackgroundPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        RiskScoreTable = new javax.swing.JTable();
        Showing_RiskScore = new javax.swing.JLabel();
        Filter_Severity = new javax.swing.JButton();
        RiskScoreDeleteBtn = new javax.swing.JButton();
        riskCalcTab = new javax.swing.JPanel();
        SelectAssetPanel = new javax.swing.JPanel();
        SelectAssetText = new javax.swing.JLabel();
        SummaryPanel = new javax.swing.JPanel();
        TopSummaryPanel = new javax.swing.JPanel();
        SummaryText = new javax.swing.JLabel();
        SummaryAssetText = new javax.swing.JLabel();
        SummarySelectedAssetText = new javax.swing.JLabel();
        SummaryThreatText = new javax.swing.JLabel();
        SummaryThreatSelectionText = new javax.swing.JLabel();
        SummaryRiskScoreText = new javax.swing.JLabel();
        SummaryLikelihoodText = new javax.swing.JLabel();
        SummaryImpactTextOut = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        AssetSelectionPanel = new javax.swing.JPanel();
        AssetList = new java.awt.List();
        SelectAssetsLabel = new javax.swing.JLabel();
        ThreatSelecitonPanel = new javax.swing.JPanel();
        ThreatList = new java.awt.List();
        SelectThreatLabel = new javax.swing.JLabel();
        calcRiskPanel = new javax.swing.JPanel();
        RiskRatingText = new javax.swing.JLabel();
        VulnerabilityText = new javax.swing.JLabel();
        AttackVectorText = new javax.swing.JLabel();
        AttackComplexityText = new javax.swing.JLabel();
        PrivilegesText = new javax.swing.JLabel();
        ScopeText = new javax.swing.JLabel();
        attackVectorBox = new javax.swing.JComboBox<>();
        CIAText = new javax.swing.JLabel();
        ConfidentialityText = new javax.swing.JLabel();
        IntegrityText = new javax.swing.JLabel();
        AvailabilityText = new javax.swing.JLabel();
        TraceabilityText = new javax.swing.JLabel();
        confidentialityBox = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        ConsequencesText = new javax.swing.JLabel();
        FinancialDamageText = new javax.swing.JLabel();
        financialBox = new javax.swing.JComboBox<>();
        ReputationText = new javax.swing.JLabel();
        NonComplianceRiskText = new javax.swing.JLabel();
        DisruptionOfServiceText = new javax.swing.JLabel();
        reputationBox = new javax.swing.JComboBox<>();
        nonComplianceBox = new javax.swing.JComboBox<>();
        disruptionBox = new javax.swing.JComboBox<>();
        attackComplexityBox = new javax.swing.JComboBox<>();
        privilegesRequiredBox = new javax.swing.JComboBox<>();
        scopeBox = new javax.swing.JComboBox<>();
        integrityBox = new javax.swing.JComboBox<>();
        availabilityBox = new javax.swing.JComboBox<>();
        tracabilityBox = new javax.swing.JComboBox<>();
        SavePanel = new javax.swing.JPanel();
        RightMediumPanel = new javax.swing.JPanel();
        Medium4 = new javax.swing.JLabel();
        LowPanel = new javax.swing.JPanel();
        Low2Text = new javax.swing.JLabel();
        MiddleMediumpanel = new javax.swing.JPanel();
        Medium5Text = new javax.swing.JLabel();
        TopHighPanel = new javax.swing.JPanel();
        High7Text = new javax.swing.JLabel();
        CriticalPanel = new javax.swing.JPanel();
        CriticalText = new javax.swing.JLabel();
        RightHighPanel = new javax.swing.JPanel();
        High8Text = new javax.swing.JLabel();
        leftMediumPanel = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        LikelihoodHighText = new javax.swing.JLabel();
        LikelihoodLowText = new javax.swing.JLabel();
        LikelihoodMediumText = new javax.swing.JLabel();
        ImpactLowText = new javax.swing.JLabel();
        ImpactMediumText = new javax.swing.JLabel();
        ImpactHighText = new javax.swing.JLabel();
        MainImpactText = new javax.swing.JLabel();
        MainLikelihoodText = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        riskScoreTextOut = new javax.swing.JLabel();
        SelectThreatPanel = new javax.swing.JPanel();
        SelectThreatText = new javax.swing.JLabel();
        TopRiskAssessmentPanel = new javax.swing.JPanel();
        RiskAssessmentPanel = new javax.swing.JLabel();
        SaveTextPanel = new javax.swing.JPanel();
        SaveTextLabel = new javax.swing.JLabel();
        BackBtn = new javax.swing.JButton();
        Next = new javax.swing.JButton();
        Homepage = new javax.swing.JPanel();
        HomePageAssetPanel = new javax.swing.JPanel();
        Asset_HomepageLable = new javax.swing.JLabel();
        AssetHomepage_Text = new javax.swing.JLabel();
        HomePageRiskPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        RiskCalc_Homepage = new javax.swing.JLabel();
        HomePageThreatPanel = new javax.swing.JPanel();
        Vulnerabilitys_HomepageLabel = new javax.swing.JLabel();
        Vuln_homepageLabel = new javax.swing.JLabel();
        HomepageTable = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        homePageTable = new javax.swing.JTable();
        ThreatJPanel = new javax.swing.JPanel();
        ThreatManagementText = new javax.swing.JLabel();
        ThreatTableBackground = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        threat_table = new javax.swing.JTable();
        Showing_threats = new javax.swing.JLabel();
        CreateNewThreat = new javax.swing.JButton();
        update_Threats = new javax.swing.JButton();
        Threat_Delete = new javax.swing.JButton();
        Help = new javax.swing.JPanel();
        HelpBackground = new javax.swing.JPanel();
        AssetManagementHelpText = new javax.swing.JLabel();
        ThreatManagmentHelpText = new javax.swing.JLabel();
        RiskScoreManagmentHelpText = new javax.swing.JLabel();
        RiskScoreHelpText = new javax.swing.JLabel();
        AuditHelpText = new javax.swing.JLabel();
        IntroductionHelpText = new javax.swing.JLabel();

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

        LogoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/risk_assessment/assets/MainLogo.png"))); // NOI18N

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
        BackgroundMain.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(30);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(2).setHeaderValue("Likelihood");
        }

        javax.swing.GroupLayout topBarLayout = new javax.swing.GroupLayout(topBar);
        topBar.setLayout(topBarLayout);
        topBarLayout.setHorizontalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topBarLayout.createSequentialGroup()
                .addGroup(topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topBarLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(LogoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(topBarLayout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(BackgroundMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(347, Short.MAX_VALUE))
        );
        topBarLayout.setVerticalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topBarLayout.createSequentialGroup()
                .addComponent(LogoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackgroundMain, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(topBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 60));

        NavigationBarPanel.setBackground(new java.awt.Color(44, 195, 112));

        DashboardPanel.setBackground(new java.awt.Color(44, 195, 112));
        DashboardPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DashboardPanel.setMinimumSize(new java.awt.Dimension(200, 111));
        DashboardPanel.setPreferredSize(new java.awt.Dimension(200, 111));
        DashboardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DashboardPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DashboardPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DashboardPanelMouseExited(evt);
            }
        });

        Home_Icon.setBackground(new java.awt.Color(255, 255, 255));
        Home_Icon.setForeground(new java.awt.Color(255, 255, 255));
        Home_Icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/risk_assessment/assets/Home.png"))); // NOI18N

        DashboardJLabel.setBackground(new java.awt.Color(0, 0, 0));
        DashboardJLabel.setFont(new java.awt.Font("Cambria Math", 0, 18)); // NOI18N
        DashboardJLabel.setText("My Dashboard");

        javax.swing.GroupLayout DashboardPanelLayout = new javax.swing.GroupLayout(DashboardPanel);
        DashboardPanel.setLayout(DashboardPanelLayout);
        DashboardPanelLayout.setHorizontalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(Home_Icon)
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DashboardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        DashboardPanelLayout.setVerticalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Home_Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DashboardJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ManagementPanel.setBackground(new java.awt.Color(44, 195, 112));
        ManagementPanel.setMinimumSize(new java.awt.Dimension(200, 111));
        ManagementPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ManagementPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ManagementPanelMouseExited(evt);
            }
        });

        ManagementJLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ManagementJLabel.setText("Management");

        javax.swing.GroupLayout ManagementPanelLayout = new javax.swing.GroupLayout(ManagementPanel);
        ManagementPanel.setLayout(ManagementPanelLayout);
        ManagementPanelLayout.setHorizontalGroup(
            ManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagementPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(ManagementJLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ManagementPanelLayout.setVerticalGroup(
            ManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ManagementJLabel)
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

        ThreatPanel.setBackground(new java.awt.Color(44, 195, 112));
        ThreatPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ThreatPanel.setMinimumSize(new java.awt.Dimension(200, 111));
        ThreatPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThreatPanelMouseClicked(evt);
            }
        });

        ThreatLabel.setText("Threat");
        ThreatLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThreatLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ThreatPanelLayout = new javax.swing.GroupLayout(ThreatPanel);
        ThreatPanel.setLayout(ThreatPanelLayout);
        ThreatPanelLayout.setHorizontalGroup(
            ThreatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(ThreatLabel)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        ThreatPanelLayout.setVerticalGroup(
            ThreatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ThreatLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FeaturesPanel.setBackground(new java.awt.Color(44, 195, 112));
        FeaturesPanel.setMinimumSize(new java.awt.Dimension(200, 111));
        FeaturesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                FeaturesPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                FeaturesPanelMouseExited(evt);
            }
        });

        FeaturesLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        FeaturesLabel.setText("Features");

        javax.swing.GroupLayout FeaturesPanelLayout = new javax.swing.GroupLayout(FeaturesPanel);
        FeaturesPanel.setLayout(FeaturesPanelLayout);
        FeaturesPanelLayout.setHorizontalGroup(
            FeaturesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FeaturesPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(FeaturesLabel)
                .addGap(64, 64, 64))
        );
        FeaturesPanelLayout.setVerticalGroup(
            FeaturesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FeaturesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FeaturesLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RiskCalcPanel.setBackground(new java.awt.Color(44, 195, 112));
        RiskCalcPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RiskCalcPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RiskCalcPanelMouseClicked(evt);
            }
        });

        RiskCalcText.setText("Risk Calculator");

        javax.swing.GroupLayout RiskCalcPanelLayout = new javax.swing.GroupLayout(RiskCalcPanel);
        RiskCalcPanel.setLayout(RiskCalcPanelLayout);
        RiskCalcPanelLayout.setHorizontalGroup(
            RiskCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiskCalcPanelLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(RiskCalcText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RiskCalcPanelLayout.setVerticalGroup(
            RiskCalcPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiskCalcPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(RiskCalcText)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        AuditPanel.setBackground(new java.awt.Color(44, 195, 112));
        AuditPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AuditPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AuditPanelMouseClicked(evt);
            }
        });

        AuditText.setText("Audit");

        javax.swing.GroupLayout AuditPanelLayout = new javax.swing.GroupLayout(AuditPanel);
        AuditPanel.setLayout(AuditPanelLayout);
        AuditPanelLayout.setHorizontalGroup(
            AuditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AuditPanelLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(AuditText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AuditPanelLayout.setVerticalGroup(
            AuditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AuditPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(AuditText)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        HelpPanel.setBackground(new java.awt.Color(44, 195, 112));
        HelpPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        HelpPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HelpPanelMouseClicked(evt);
            }
        });

        HelpText.setText("Help");

        javax.swing.GroupLayout HelpPanelLayout = new javax.swing.GroupLayout(HelpPanel);
        HelpPanel.setLayout(HelpPanelLayout);
        HelpPanelLayout.setHorizontalGroup(
            HelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HelpPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(HelpText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HelpPanelLayout.setVerticalGroup(
            HelpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HelpPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(HelpText)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        RiskScorePanel.setBackground(new java.awt.Color(44, 195, 112));
        RiskScorePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RiskScorePanel.setMinimumSize(new java.awt.Dimension(200, 111));
        RiskScorePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RiskScorePanelMouseClicked(evt);
            }
        });

        RiskScoreText.setText("Risk Score");
        RiskScoreText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RiskScoreTextMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout RiskScorePanelLayout = new javax.swing.GroupLayout(RiskScorePanel);
        RiskScorePanel.setLayout(RiskScorePanelLayout);
        RiskScorePanelLayout.setHorizontalGroup(
            RiskScorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiskScorePanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(RiskScoreText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RiskScorePanelLayout.setVerticalGroup(
            RiskScorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiskScorePanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(RiskScoreText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout NavigationBarPanelLayout = new javax.swing.GroupLayout(NavigationBarPanel);
        NavigationBarPanel.setLayout(NavigationBarPanelLayout);
        NavigationBarPanelLayout.setHorizontalGroup(
            NavigationBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(ManagementPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NavigationBarPanelLayout.createSequentialGroup()
                .addGroup(NavigationBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ThreatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AssetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FeaturesPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RiskCalcPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AuditPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HelpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RiskScorePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        NavigationBarPanelLayout.setVerticalGroup(
            NavigationBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationBarPanelLayout.createSequentialGroup()
                .addComponent(DashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ManagementPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(AssetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(ThreatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(RiskScorePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FeaturesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RiskCalcPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(AuditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(HelpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(416, Short.MAX_VALUE))
        );

        getContentPane().add(NavigationBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 200, 960));

        AssetManagmentText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        AssetManagmentText.setText("Asset Management:");

        AssetTableBackgroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        assetTable.setModel(new javax.swing.table.DefaultTableModel(
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
        assetTable.setRowHeight(60);
        jScrollPane3.setViewportView(assetTable);
        if (assetTable.getColumnModel().getColumnCount() > 0) {
            assetTable.getColumnModel().getColumn(0).setMinWidth(30);
            assetTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            assetTable.getColumnModel().getColumn(0).setMaxWidth(30);
        }

        Asset_Showing.setText("Showing: 42 Assets");

        createNewAsset.setText("Create_New");
        createNewAsset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createNewAssetMouseClicked(evt);
            }
        });
        createNewAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewAssetActionPerformed(evt);
            }
        });

        AssetUpdateBtn.setText("Update");
        AssetUpdateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AssetUpdateBtnMouseClicked(evt);
            }
        });
        AssetUpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AssetUpdateBtnActionPerformed(evt);
            }
        });

        AssetDeleteBtn.setText("Delete Selected");
        AssetDeleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AssetDeleteBtnMouseClicked(evt);
            }
        });
        AssetDeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AssetDeleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AssetTableBackgroundPanelLayout = new javax.swing.GroupLayout(AssetTableBackgroundPanel);
        AssetTableBackgroundPanel.setLayout(AssetTableBackgroundPanelLayout);
        AssetTableBackgroundPanelLayout.setHorizontalGroup(
            AssetTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AssetTableBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Asset_Showing, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AssetDeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AssetUpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createNewAsset, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(AssetTableBackgroundPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AssetTableBackgroundPanelLayout.setVerticalGroup(
            AssetTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssetTableBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AssetTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AssetTableBackgroundPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(AssetTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Asset_Showing, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(createNewAsset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(AssetUpdateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AssetDeleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout assetJpanelLayout = new javax.swing.GroupLayout(assetJpanel);
        assetJpanel.setLayout(assetJpanelLayout);
        assetJpanelLayout.setHorizontalGroup(
            assetJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetJpanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(assetJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AssetTableBackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AssetManagmentText, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        assetJpanelLayout.setVerticalGroup(
            assetJpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetJpanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(AssetManagmentText, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AssetTableBackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(361, Short.MAX_VALUE))
        );

        Tab_tabbedPane.addTab("tab3", assetJpanel);

        RiskScoreManagementText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        RiskScoreManagementText.setText("Risk Score Management:");

        RiskScoreTableBackgroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        RiskScoreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AssetName/s", "Threat", "Risk Likelihood", "Risk_Impact", "Risk Score"
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

        Showing_RiskScore.setText("Showing: 23");

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

        RiskScoreDeleteBtn.setText("Delete Selected");
        RiskScoreDeleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RiskScoreDeleteBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout RiskScoreTableBackgroundPanelLayout = new javax.swing.GroupLayout(RiskScoreTableBackgroundPanel);
        RiskScoreTableBackgroundPanel.setLayout(RiskScoreTableBackgroundPanelLayout);
        RiskScoreTableBackgroundPanelLayout.setHorizontalGroup(
            RiskScoreTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(RiskScoreTableBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Showing_RiskScore, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RiskScoreDeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Filter_Severity, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(RiskScoreTableBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        RiskScoreTableBackgroundPanelLayout.setVerticalGroup(
            RiskScoreTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiskScoreTableBackgroundPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(RiskScoreTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RiskScoreDeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RiskScoreTableBackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Showing_RiskScore, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Filter_Severity, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(RiskScoreTableBackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RiskScoreManagementText, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(291, Short.MAX_VALUE))
        );
        risk_ScoreLayout.setVerticalGroup(
            risk_ScoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(risk_ScoreLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(RiskScoreManagementText, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RiskScoreTableBackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(360, Short.MAX_VALUE))
        );

        Tab_tabbedPane.addTab("tab2", risk_Score);

        SelectAssetPanel.setBackground(new java.awt.Color(44, 195, 112));
        SelectAssetPanel.setPreferredSize(new java.awt.Dimension(245, 64));
        SelectAssetPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SelectAssetText.setText("Select Asset");
        SelectAssetPanel.add(SelectAssetText, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        SummaryPanel.setBackground(new java.awt.Color(204, 204, 204));

        TopSummaryPanel.setBackground(new java.awt.Color(44, 195, 112));

        SummaryText.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        SummaryText.setForeground(new java.awt.Color(255, 255, 255));
        SummaryText.setText(" Summary");

        javax.swing.GroupLayout TopSummaryPanelLayout = new javax.swing.GroupLayout(TopSummaryPanel);
        TopSummaryPanel.setLayout(TopSummaryPanelLayout);
        TopSummaryPanelLayout.setHorizontalGroup(
            TopSummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopSummaryPanelLayout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(SummaryText, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );
        TopSummaryPanelLayout.setVerticalGroup(
            TopSummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopSummaryPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(SummaryText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        SummaryAssetText.setText("Asset:");

        SummarySelectedAssetText.setText("0");

        SummaryThreatText.setText("Threat:");

        SummaryThreatSelectionText.setText("0");

        SummaryRiskScoreText.setText("Risk Score:");

        SummaryLikelihoodText.setText("0");

        SummaryImpactTextOut.setText("0");

        javax.swing.GroupLayout SummaryPanelLayout = new javax.swing.GroupLayout(SummaryPanel);
        SummaryPanel.setLayout(SummaryPanelLayout);
        SummaryPanelLayout.setHorizontalGroup(
            SummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TopSummaryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SummaryPanelLayout.createSequentialGroup()
                .addGroup(SummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SummaryPanelLayout.createSequentialGroup()
                            .addGap(46, 46, 46)
                            .addComponent(SummarySelectedAssetText, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(SummaryPanelLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(SummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(SummaryAssetText, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(SummaryThreatText, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(SummaryRiskScoreText, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(SummaryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SummaryImpactTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SummaryLikelihoodText, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SummaryThreatSelectionText, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        SummaryPanelLayout.setVerticalGroup(
            SummaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SummaryPanelLayout.createSequentialGroup()
                .addComponent(TopSummaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SummaryAssetText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SummarySelectedAssetText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SummaryThreatText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SummaryThreatSelectionText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SummaryRiskScoreText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SummaryLikelihoodText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SummaryImpactTextOut, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 253, Short.MAX_VALUE))
        );

        AssetList.setMultipleMode(true);
        AssetList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AssetListActionPerformed(evt);
            }
        });

        SelectAssetsLabel.setText("Select Assets: ");

        javax.swing.GroupLayout AssetSelectionPanelLayout = new javax.swing.GroupLayout(AssetSelectionPanel);
        AssetSelectionPanel.setLayout(AssetSelectionPanelLayout);
        AssetSelectionPanelLayout.setHorizontalGroup(
            AssetSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AssetList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AssetSelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SelectAssetsLabel)
                .addContainerGap(976, Short.MAX_VALUE))
        );
        AssetSelectionPanelLayout.setVerticalGroup(
            AssetSelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AssetSelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SelectAssetsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(AssetList, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("tab1", AssetSelectionPanel);

        SelectThreatLabel.setText("Select Threats: ");

        javax.swing.GroupLayout ThreatSelecitonPanelLayout = new javax.swing.GroupLayout(ThreatSelecitonPanel);
        ThreatSelecitonPanel.setLayout(ThreatSelecitonPanelLayout);
        ThreatSelecitonPanelLayout.setHorizontalGroup(
            ThreatSelecitonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatSelecitonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SelectThreatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThreatSelecitonPanelLayout.createSequentialGroup()
                .addComponent(ThreatList, javax.swing.GroupLayout.PREFERRED_SIZE, 1302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ThreatSelecitonPanelLayout.setVerticalGroup(
            ThreatSelecitonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThreatSelecitonPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(SelectThreatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(ThreatList, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("tab2", ThreatSelecitonPanel);

        RiskRatingText.setFont(new java.awt.Font("Segoe UI Historic", 0, 18)); // NOI18N
        RiskRatingText.setText("Risk Rating Calculator");

        VulnerabilityText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        VulnerabilityText.setText("Vulnerability Identificaiton:");

        AttackVectorText.setText("Attack Vector:");

        AttackComplexityText.setText("Attack Complexity:");

        PrivilegesText.setText("Privileges Required:");

        ScopeText.setText("Scope:");

        attackVectorBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        CIAText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CIAText.setText("CIA Impact Factors:");

        ConfidentialityText.setText("Loss of Confidentiality:");

        IntegrityText.setText("Loss of Integrity:");

        AvailabilityText.setText("Loss of Availability:");

        TraceabilityText.setText("Loss of Traceability:");

        confidentialityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jPanel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        ConsequencesText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ConsequencesText.setText("Consequences:");
        ConsequencesText.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        FinancialDamageText.setText("Financial Damage:");

        financialBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        ReputationText.setText("Reputation Damage:");

        NonComplianceRiskText.setText("Non-Compliance:");

        DisruptionOfServiceText.setText("Disruption of Service:");

        reputationBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        nonComplianceBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        disruptionBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ConsequencesText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FinancialDamageText)
                    .addComponent(financialBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ReputationText)
                    .addComponent(reputationBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NonComplianceRiskText)
                    .addComponent(nonComplianceBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(disruptionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DisruptionOfServiceText))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ConsequencesText, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FinancialDamageText)
                    .addComponent(ReputationText)
                    .addComponent(NonComplianceRiskText)
                    .addComponent(DisruptionOfServiceText))
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
                            .addComponent(VulnerabilityText, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(attackVectorBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CIAText, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(calcRiskPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(calcRiskPanelLayout.createSequentialGroup()
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ConfidentialityText)
                                            .addComponent(AttackVectorText)
                                            .addComponent(confidentialityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(AttackComplexityText)
                                            .addComponent(IntegrityText)
                                            .addComponent(attackComplexityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(integrityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(PrivilegesText)
                                            .addComponent(AvailabilityText)
                                            .addComponent(privilegesRequiredBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(availabilityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(100, 100, 100)
                                        .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tracabilityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ScopeText, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scopeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TraceabilityText)))
                                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(calcRiskPanelLayout.createSequentialGroup()
                        .addGap(494, 494, 494)
                        .addComponent(RiskRatingText, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(281, 281, Short.MAX_VALUE))
        );
        calcRiskPanelLayout.setVerticalGroup(
            calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calcRiskPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RiskRatingText, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(VulnerabilityText, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AttackVectorText)
                    .addComponent(AttackComplexityText)
                    .addComponent(PrivilegesText)
                    .addComponent(ScopeText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(attackVectorBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attackComplexityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(privilegesRequiredBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scopeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(CIAText, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(calcRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfidentialityText)
                    .addComponent(IntegrityText)
                    .addComponent(AvailabilityText)
                    .addComponent(TraceabilityText))
                .addGap(18, 18, 18)
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

        RightMediumPanel.setBackground(new java.awt.Color(255, 102, 0));

        Medium4.setText("Medium - 4");

        javax.swing.GroupLayout RightMediumPanelLayout = new javax.swing.GroupLayout(RightMediumPanel);
        RightMediumPanel.setLayout(RightMediumPanelLayout);
        RightMediumPanelLayout.setHorizontalGroup(
            RightMediumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightMediumPanelLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(Medium4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        RightMediumPanelLayout.setVerticalGroup(
            RightMediumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightMediumPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(Medium4)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        LowPanel.setBackground(new java.awt.Color(255, 255, 0));

        Low2Text.setText("Low - 2");

        javax.swing.GroupLayout LowPanelLayout = new javax.swing.GroupLayout(LowPanel);
        LowPanel.setLayout(LowPanelLayout);
        LowPanelLayout.setHorizontalGroup(
            LowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LowPanelLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(Low2Text, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        LowPanelLayout.setVerticalGroup(
            LowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LowPanelLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(Low2Text)
                .addGap(26, 26, 26))
        );

        MiddleMediumpanel.setBackground(new java.awt.Color(255, 102, 0));

        Medium5Text.setText("Medium - 5");

        javax.swing.GroupLayout MiddleMediumpanelLayout = new javax.swing.GroupLayout(MiddleMediumpanel);
        MiddleMediumpanel.setLayout(MiddleMediumpanelLayout);
        MiddleMediumpanelLayout.setHorizontalGroup(
            MiddleMediumpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MiddleMediumpanelLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(Medium5Text, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        MiddleMediumpanelLayout.setVerticalGroup(
            MiddleMediumpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MiddleMediumpanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(Medium5Text)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        TopHighPanel.setBackground(new java.awt.Color(255, 0, 0));

        High7Text.setText("High - 7");

        javax.swing.GroupLayout TopHighPanelLayout = new javax.swing.GroupLayout(TopHighPanel);
        TopHighPanel.setLayout(TopHighPanelLayout);
        TopHighPanelLayout.setHorizontalGroup(
            TopHighPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopHighPanelLayout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(High7Text, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        TopHighPanelLayout.setVerticalGroup(
            TopHighPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopHighPanelLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(High7Text)
                .addGap(26, 26, 26))
        );

        CriticalPanel.setBackground(new java.awt.Color(153, 0, 0));

        CriticalText.setText("Critical - 9+");

        javax.swing.GroupLayout CriticalPanelLayout = new javax.swing.GroupLayout(CriticalPanel);
        CriticalPanel.setLayout(CriticalPanelLayout);
        CriticalPanelLayout.setHorizontalGroup(
            CriticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CriticalPanelLayout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(CriticalText, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        CriticalPanelLayout.setVerticalGroup(
            CriticalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CriticalPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(CriticalText)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        RightHighPanel.setBackground(new java.awt.Color(255, 0, 0));

        High8Text.setText("High - 8");

        javax.swing.GroupLayout RightHighPanelLayout = new javax.swing.GroupLayout(RightHighPanel);
        RightHighPanel.setLayout(RightHighPanelLayout);
        RightHighPanelLayout.setHorizontalGroup(
            RightHighPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightHighPanelLayout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(High8Text, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        RightHighPanelLayout.setVerticalGroup(
            RightHighPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightHighPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(High8Text)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        leftMediumPanel.setBackground(new java.awt.Color(255, 102, 0));

        jLabel88.setText("Medium - 6");

        javax.swing.GroupLayout leftMediumPanelLayout = new javax.swing.GroupLayout(leftMediumPanel);
        leftMediumPanel.setLayout(leftMediumPanelLayout);
        leftMediumPanelLayout.setHorizontalGroup(
            leftMediumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftMediumPanelLayout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        leftMediumPanelLayout.setVerticalGroup(
            leftMediumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftMediumPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel88)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        LikelihoodHighText.setText("High");

        LikelihoodLowText.setText("Low");

        LikelihoodMediumText.setText("Medium");

        ImpactLowText.setText("Low");

        ImpactMediumText.setText("Medium");

        ImpactHighText.setText("High");

        MainImpactText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MainImpactText.setText("Impact");

        MainLikelihoodText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MainLikelihoodText.setText("Likelihood");

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

        javax.swing.GroupLayout SavePanelLayout = new javax.swing.GroupLayout(SavePanel);
        SavePanel.setLayout(SavePanelLayout);
        SavePanelLayout.setHorizontalGroup(
            SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SavePanelLayout.createSequentialGroup()
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(MainImpactText, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ImpactLowText)
                                    .addComponent(ImpactMediumText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ImpactHighText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(leftMediumPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addComponent(LowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MiddleMediumpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RightHighPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addComponent(RightMediumPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TopHighPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CriticalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(MainLikelihoodText, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(SavePanelLayout.createSequentialGroup()
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addGap(402, 402, 402)
                        .addComponent(LikelihoodLowText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155)
                        .addComponent(LikelihoodMediumText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(176, 176, 176)
                        .addComponent(LikelihoodHighText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SavePanelLayout.setVerticalGroup(
            SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SavePanelLayout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(RightMediumPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TopHighPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CriticalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addComponent(ImpactHighText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SavePanelLayout.createSequentialGroup()
                        .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RightHighPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ImpactMediumText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MainImpactText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(leftMediumPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(SavePanelLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(ImpactLowText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(MiddleMediumpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LikelihoodHighText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LikelihoodLowText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LikelihoodMediumText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(MainLikelihoodText, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab8", SavePanel);

        SelectThreatPanel.setBackground(new java.awt.Color(44, 195, 112));
        SelectThreatPanel.setPreferredSize(new java.awt.Dimension(245, 64));
        SelectThreatPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SelectThreatText.setText("Select Threats");
        SelectThreatPanel.add(SelectThreatText, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        TopRiskAssessmentPanel.setBackground(new java.awt.Color(44, 195, 112));
        TopRiskAssessmentPanel.setPreferredSize(new java.awt.Dimension(245, 64));
        TopRiskAssessmentPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RiskAssessmentPanel.setText("Risk Assessment");
        TopRiskAssessmentPanel.add(RiskAssessmentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        SaveTextPanel.setBackground(new java.awt.Color(44, 195, 112));
        SaveTextPanel.setPreferredSize(new java.awt.Dimension(245, 64));
        SaveTextPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SaveTextLabel.setText("Save");
        SaveTextPanel.add(SaveTextLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        BackBtn.setText("Back");
        BackBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackBtnMouseClicked(evt);
            }
        });
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
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
                        .addComponent(SummaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(riskCalcTabLayout.createSequentialGroup()
                                .addComponent(SelectAssetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(SelectThreatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(TopRiskAssessmentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(SaveTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(riskCalcTabLayout.createSequentialGroup()
                        .addGap(0, 1110, Short.MAX_VALUE)
                        .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(310, Short.MAX_VALUE))
        );
        riskCalcTabLayout.setVerticalGroup(
            riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(riskCalcTabLayout.createSequentialGroup()
                .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(TopRiskAssessmentPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SelectThreatPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SelectAssetPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SaveTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SummaryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE)
                .addGroup(riskCalcTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(243, 243, 243))
        );

        Tab_tabbedPane.addTab("tab4", riskCalcTab);

        HomePageAssetPanel.setBackground(new java.awt.Color(255, 255, 255));

        Asset_HomepageLable.setText("Assets:");

        AssetHomepage_Text.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        AssetHomepage_Text.setText("23");

        javax.swing.GroupLayout HomePageAssetPanelLayout = new javax.swing.GroupLayout(HomePageAssetPanel);
        HomePageAssetPanel.setLayout(HomePageAssetPanelLayout);
        HomePageAssetPanelLayout.setHorizontalGroup(
            HomePageAssetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageAssetPanelLayout.createSequentialGroup()
                .addGroup(HomePageAssetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePageAssetPanelLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(AssetHomepage_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HomePageAssetPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Asset_HomepageLable)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        HomePageAssetPanelLayout.setVerticalGroup(
            HomePageAssetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageAssetPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(Asset_HomepageLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AssetHomepage_Text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        HomePageRiskPanel.setBackground(new java.awt.Color(255, 255, 255));
        HomePageRiskPanel.setPreferredSize(new java.awt.Dimension(248, 82));

        jLabel10.setText("Risks Calcualted:");

        RiskCalc_Homepage.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        RiskCalc_Homepage.setText("13");

        javax.swing.GroupLayout HomePageRiskPanelLayout = new javax.swing.GroupLayout(HomePageRiskPanel);
        HomePageRiskPanel.setLayout(HomePageRiskPanelLayout);
        HomePageRiskPanelLayout.setHorizontalGroup(
            HomePageRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageRiskPanelLayout.createSequentialGroup()
                .addGroup(HomePageRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePageRiskPanelLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(RiskCalc_Homepage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HomePageRiskPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        HomePageRiskPanelLayout.setVerticalGroup(
            HomePageRiskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageRiskPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RiskCalc_Homepage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        HomePageThreatPanel.setBackground(new java.awt.Color(255, 255, 255));
        HomePageThreatPanel.setPreferredSize(new java.awt.Dimension(248, 82));

        Vulnerabilitys_HomepageLabel.setText("Threats:");

        Vuln_homepageLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Vuln_homepageLabel.setText("13");

        javax.swing.GroupLayout HomePageThreatPanelLayout = new javax.swing.GroupLayout(HomePageThreatPanel);
        HomePageThreatPanel.setLayout(HomePageThreatPanelLayout);
        HomePageThreatPanelLayout.setHorizontalGroup(
            HomePageThreatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageThreatPanelLayout.createSequentialGroup()
                .addGroup(HomePageThreatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePageThreatPanelLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(Vuln_homepageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HomePageThreatPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Vulnerabilitys_HomepageLabel)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        HomePageThreatPanelLayout.setVerticalGroup(
            HomePageThreatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePageThreatPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(Vulnerabilitys_HomepageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Vuln_homepageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        HomepageTable.setBackground(new java.awt.Color(255, 255, 255));

        homePageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Risk Computed"
            }
        ));
        jScrollPane10.setViewportView(homePageTable);

        javax.swing.GroupLayout HomepageTableLayout = new javax.swing.GroupLayout(HomepageTable);
        HomepageTable.setLayout(HomepageTableLayout);
        HomepageTableLayout.setHorizontalGroup(
            HomepageTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomepageTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                .addContainerGap())
        );
        HomepageTableLayout.setVerticalGroup(
            HomepageTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomepageTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout HomepageLayout = new javax.swing.GroupLayout(Homepage);
        Homepage.setLayout(HomepageLayout);
        HomepageLayout.setHorizontalGroup(
            HomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomepageLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(HomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HomepageTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(HomepageLayout.createSequentialGroup()
                        .addComponent(HomePageAssetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(HomePageThreatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(HomePageRiskPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(882, Short.MAX_VALUE))
        );
        HomepageLayout.setVerticalGroup(
            HomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomepageLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(HomepageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(HomePageRiskPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HomePageAssetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HomePageThreatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(HomepageTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(257, Short.MAX_VALUE))
        );

        Tab_tabbedPane.addTab("tab4", Homepage);

        ThreatManagementText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ThreatManagementText.setText("Threat Management:");

        ThreatTableBackground.setBackground(new java.awt.Color(255, 255, 255));

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

        Showing_threats.setText("Showing: 42 Threats");

        CreateNewThreat.setText("Create_New");
        CreateNewThreat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreateNewThreatMouseClicked(evt);
            }
        });
        CreateNewThreat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateNewThreatActionPerformed(evt);
            }
        });

        update_Threats.setText("Update");
        update_Threats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                update_ThreatsMouseClicked(evt);
            }
        });
        update_Threats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_ThreatsActionPerformed(evt);
            }
        });

        Threat_Delete.setText("Delete");
        Threat_Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Threat_DeleteMouseClicked(evt);
            }
        });
        Threat_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Threat_DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ThreatTableBackgroundLayout = new javax.swing.GroupLayout(ThreatTableBackground);
        ThreatTableBackground.setLayout(ThreatTableBackgroundLayout);
        ThreatTableBackgroundLayout.setHorizontalGroup(
            ThreatTableBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ThreatTableBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Showing_threats, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Threat_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(update_Threats, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CreateNewThreat, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(ThreatTableBackgroundLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ThreatTableBackgroundLayout.setVerticalGroup(
            ThreatTableBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatTableBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ThreatTableBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ThreatTableBackgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(ThreatTableBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Showing_threats, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CreateNewThreat, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(update_Threats, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Threat_Delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout ThreatJPanelLayout = new javax.swing.GroupLayout(ThreatJPanel);
        ThreatJPanel.setLayout(ThreatJPanelLayout);
        ThreatJPanelLayout.setHorizontalGroup(
            ThreatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatJPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(ThreatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ThreatTableBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ThreatManagementText, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        ThreatJPanelLayout.setVerticalGroup(
            ThreatJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThreatJPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(ThreatManagementText, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(ThreatTableBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );

        Tab_tabbedPane.addTab("tab5", ThreatJPanel);

        HelpBackground.setBackground(new java.awt.Color(255, 255, 255));

        AssetManagementHelpText.setText("Asset Management Tab: This tab allows you to manage the assets in the system. You can add, edit, and delete assets and view their details.");

        ThreatManagmentHelpText.setText("Threat Management Tab: This tab allows you to manage the threats in the system. You can add, edit, and delete threats and view their details. ");

        RiskScoreManagmentHelpText.setText("Risk Score Tab: This tab allows you to calculate the risk scores for each asset based on the likelihood and impact of the threats. You can view the risk scores for each asset in a table format.");

        RiskScoreHelpText.setText("To calculate the risk scores, select the assets and threats from the respective tables, input the metrics that are displayed on  tab and click on the “Next” button, and the risk scores will be displayed in the “Risk Scores” table.");

        AuditHelpText.setText("To output the audit , click on the “Audit” tab which will allow you to export the file.");

        IntroductionHelpText.setText("Introduction: This guide provides information on how to use the tool effectively and efficiently. The guide covers all the key features and functionalities of the tool.");

        javax.swing.GroupLayout HelpBackgroundLayout = new javax.swing.GroupLayout(HelpBackground);
        HelpBackground.setLayout(HelpBackgroundLayout);
        HelpBackgroundLayout.setHorizontalGroup(
            HelpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HelpBackgroundLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(HelpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IntroductionHelpText, javax.swing.GroupLayout.PREFERRED_SIZE, 924, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AssetManagementHelpText)
                    .addComponent(ThreatManagmentHelpText)
                    .addComponent(RiskScoreManagmentHelpText)
                    .addComponent(AuditHelpText, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RiskScoreHelpText, javax.swing.GroupLayout.PREFERRED_SIZE, 1263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(820, Short.MAX_VALUE))
        );
        HelpBackgroundLayout.setVerticalGroup(
            HelpBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HelpBackgroundLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(IntroductionHelpText)
                .addGap(47, 47, 47)
                .addComponent(AssetManagementHelpText)
                .addGap(37, 37, 37)
                .addComponent(ThreatManagmentHelpText)
                .addGap(46, 46, 46)
                .addComponent(RiskScoreManagmentHelpText)
                .addGap(88, 88, 88)
                .addComponent(RiskScoreHelpText)
                .addGap(46, 46, 46)
                .addComponent(AuditHelpText)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HelpLayout = new javax.swing.GroupLayout(Help);
        Help.setLayout(HelpLayout);
        HelpLayout.setHorizontalGroup(
            HelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HelpLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(HelpBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HelpLayout.setVerticalGroup(
            HelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HelpLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(HelpBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(349, Short.MAX_VALUE))
        );

        Tab_tabbedPane.addTab("tab3", Help);

        getContentPane().add(Tab_tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 1720, 1050));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Hover effect
    private void DashboardPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardPanelMouseEntered
  //     One.setBackground(Color.white);
    }//GEN-LAST:event_DashboardPanelMouseEntered

    private void DashboardPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardPanelMouseExited
  //      One.setBackground(new Color(44,195,112));
    }//GEN-LAST:event_DashboardPanelMouseExited

    private void ManagementPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManagementPanelMouseEntered
       //Two.setBackground(Color.white);
    }//GEN-LAST:event_ManagementPanelMouseEntered

    private void ManagementPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManagementPanelMouseExited
      //  Two.setBackground(new Color(44,195,112));
    }//GEN-LAST:event_ManagementPanelMouseExited

    private void FeaturesPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FeaturesPanelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_FeaturesPanelMouseEntered

    private void FeaturesPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FeaturesPanelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_FeaturesPanelMouseExited

    private void ThreatPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThreatPanelMouseClicked
        Tab_tabbedPane.setSelectedIndex(4);
    }//GEN-LAST:event_ThreatPanelMouseClicked

    private int conseq = 0;
    private int threat = 0;
    private int likely = 0;
    private String riskScoreOutput;
    
    private void RiskCalcPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RiskCalcPanelMouseClicked
        Tab_tabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_RiskCalcPanelMouseClicked

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NextActionPerformed
    
    private int result;
    String selected;
    String assetsSelected;
    String vuln;
    
    /**
 * Handles mouse clicks on the "Next" button.
 * This method controls the flow of the GUI and updates the "Summary" panel with user selections.
 * If the user reaches the final panel, this method saves the user's selections to a MySQL database.
 */
    private void NextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextMouseClicked

if (jTabbedPane2.getSelectedIndex() == 0) {
// If the first tab is selected
    // Get selected items from AssetList and format them into a string
    String[] selecteditems = AssetList.getSelectedItems();
    StringBuilder sb = new StringBuilder();
    for (String items: selecteditems){
        sb.append(items).append(", ");
        assetsSelected = sb.toString();
    }
    // Set summary text and move to the next tab
    SummaryAssetText.setText("Asset: ");
    SummarySelectedAssetText.setText(assetsSelected);
    jTabbedPane2.setSelectedIndex(1);
} 
// If the second tab is selected
else if (jTabbedPane2.getSelectedIndex() == 1) {
    // Get selected threat and set summary text, then move to the next tab
    String[] selecteditems = ThreatList.getSelectedItems();
    SummaryThreatText.setText("Threats");
    vuln = ThreatList.getSelectedItem();
    SummaryThreatSelectionText.setText(vuln);
    jTabbedPane2.setSelectedIndex(2);
} 
// If the third tab is selected
else if (jTabbedPane2.getSelectedIndex() == 2) {
    // Calculate vulnerability, CIA impact, and consequences values
    int vulnerabilityID = (attackVectorBox.getSelectedIndex() + attackComplexityBox.getSelectedIndex() + privilegesRequiredBox.getSelectedIndex() + scopeBox.getSelectedIndex());
    int CIAimpact = (confidentialityBox.getSelectedIndex() + integrityBox.getSelectedIndex() + availabilityBox.getSelectedIndex() + tracabilityBox.getSelectedIndex());
    int consequences = (financialBox.getSelectedIndex() + reputationBox.getSelectedIndex() + nonComplianceBox.getSelectedIndex() + disruptionBox.getSelectedIndex());
    
    // Check if any of the options are not selected and print error messages
    if (attackVectorBox.getSelectedIndex() == 0 || attackComplexityBox.getSelectedIndex() == 0 || privilegesRequiredBox.getSelectedIndex() == 0 || scopeBox.getSelectedIndex() == 0) {
        System.out.println("Vulnerability is 0");
    } else if (confidentialityBox.getSelectedIndex() == 0 || integrityBox.getSelectedIndex() == 0 || availabilityBox.getSelectedIndex() == 0 || tracabilityBox.getSelectedIndex() == 0) {
        System.out.println("CIA is 0");
    } else if (financialBox.getSelectedIndex() == 0 || reputationBox.getSelectedIndex() == 0 || nonComplianceBox.getSelectedIndex() == 0 || disruptionBox.getSelectedIndex() == 0) {
        System.out.println("Consequences is 0");
    } else {
        // Calculate likelihood, impact, and risk score based on selected options
        float Vuln = (float) vulnerabilityID / 4;
        float CIA = (float) CIAimpact / 4;
        float Cons = (float) consequences / 4;
        double LikelihoodTotal = (Vuln);
        double FinalImpact = (CIA + Cons) / 2;
        System.out.println(LikelihoodTotal);
        System.out.println(FinalImpact);
        
        String likelihoodOutput = "";
        String ImpactOutput = "";
        
 // likelihood checker
if (LikelihoodTotal >= 0 && LikelihoodTotal < 3) {
likelihoodOutput = "Low";
System.out.println(likelihoodOutput);
} else if (LikelihoodTotal >= 3 && LikelihoodTotal <= 6) {
likelihoodOutput = "Medium";
System.out.println(likelihoodOutput);
} else if (LikelihoodTotal >= 6) {
likelihoodOutput = "High";
System.out.println(likelihoodOutput);
} else if (LikelihoodTotal >= 9.0) {
likelihoodOutput = "Critical";
System.out.println(likelihoodOutput);
}else {
System.out.println("Likelihood checker error");
}

// impact checker
if (FinalImpact < 3) {
ImpactOutput = "Low";
System.out.println(ImpactOutput);
} else if (FinalImpact >= 3 && FinalImpact <= 6) {
ImpactOutput = "Medium";
System.out.println(ImpactOutput);
} else if (FinalImpact >= 6.0) {
ImpactOutput = "High";
System.out.println(ImpactOutput);
} else if (FinalImpact >= 9.0) {
ImpactOutput = "Critical";
System.out.println(ImpactOutput);
} else {
System.out.println("IMPACT checker error");
}

///
int riskScoreValue;

if (likelihoodOutput.equals("Minimal") || ImpactOutput.equals("Minimal")) {
    riskScoreValue = 1;
    riskScoreOutput = "Minimal";
} else if (likelihoodOutput.equals("Low") && ImpactOutput.equals("Low")) {
    riskScoreValue = 2;
    riskScoreOutput = "Low";
} else if ((likelihoodOutput.equals("Low") && ImpactOutput.equals("Medium")) ||
           (likelihoodOutput.equals("Medium") && ImpactOutput.equals("Low")) ||
           (likelihoodOutput.equals("Medium") && ImpactOutput.equals("Medium")) ||
           (likelihoodOutput.equals("Low") && ImpactOutput.equals("High")) ||
           (likelihoodOutput.equals("Medium") && ImpactOutput.equals("High")) ||
           (likelihoodOutput.equals("High") && ImpactOutput.equals("Low"))) {
    riskScoreValue = 3;
    riskScoreOutput = "Medium";
} else if ((likelihoodOutput.equals("High") && ImpactOutput.equals("Medium")) ||
           (likelihoodOutput.equals("Medium") && ImpactOutput.equals("Critical")) ||
           (likelihoodOutput.equals("High") && ImpactOutput.equals("High"))) {
    riskScoreValue = 4;
    riskScoreOutput = "High";
} else if (likelihoodOutput.equals("High") && ImpactOutput.equals("Critical")) {
    riskScoreValue = 5;
    riskScoreOutput = "Critical";
} else {
    System.out.println("Risk score calculator error");
    return;
}
///
    
    SummaryRiskScoreText.setText("Risk Score");
    likelihoodCalcuation = likelihoodOutput;
    impactCalcuation = ImpactOutput;
   System.out.println(likelihoodCalcuation);
     System.out.println(ImpactOutput);
    
    SummaryLikelihoodText.setText(likelihoodOutput);
    SummaryImpactTextOut.setText(ImpactOutput);
    jTabbedPane2.setSelectedIndex(3);
   int castedValue = (int)((LikelihoodTotal + FinalImpact) / 2);
    riskScoreTextOut.setText("Risk Score: " + castedValue);
    }
    

} else if (jTabbedPane2.getSelectedIndex() == 3) {
Next.setText("Save");
try {
    // Load the MySQL JDBC driver class
    Class.forName("com.mysql.cj.jdbc.Driver");
    
    // Create a connection to the MySQL database
    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");
    
    // Check if the connection is successful
    if (con != null) {
        System.out.println("Success");
    }
    
    // Get the number of rows in the risk_score table
    int rowCount = 0;
    String sqlNumofRows = "SELECT COUNT(*) FROM risk_score ";
    Statement rows = con.createStatement();
    ResultSet setRow = rows.executeQuery(sqlNumofRows);
    if (setRow.next()){
        rowCount = setRow.getInt(1);
        System.out.println("Number of rows:" + rowCount);
    }
    
    //get the current model of risk Score
    DefaultTableModel risk = (DefaultTableModel)RiskScoreTable.getModel();
    
    
    // Insert a new row into the risk_score table with the user's selections
    String sql = "INSERT INTO risk_score (idrisk_Score, Asset_Names, risk_vuln, risk_likelihood, risk_Impact, risk_Score) VALUES ('"+(rowCount+1)+"', '"+assetsSelected+"', '"+vuln+"', '"+likelihoodCalcuation+"','"+impactCalcuation+"','"+riskScoreOutput+"')";
    Statement stmt=con.createStatement();
    int rowsInserted = stmt.executeUpdate(sql);
    

    // Get the current date and time in the specified format
    DefaultTableModel model = (DefaultTableModel)homePageTable.getModel();
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    
    // Get the number of rows in the risk_change table
    int rowCountChange = 0;
    String NumofRows = "SELECT COUNT(*) FROM risk_change ";
    Statement Numrows = con.createStatement();
    ResultSet setRows = Numrows.executeQuery(NumofRows);
    if (setRows.next()){
        rowCountChange = setRows.getInt(1);
        System.out.println("Number of rows:" + rowCountChange);
    } 
    
    // Insert a new row into the risk_change table with the current date and time and the result of the operation
    String riskComputedSQL;
    if (rowsInserted > 0){
        System.out.println(rowsInserted + " Rows inserted.");
        Tab_tabbedPane.setSelectedIndex(3);
        jTabbedPane2.setSelectedIndex(0);
        SummarySelectedAssetText.setText("");
        SummaryThreatSelectionText.setText("");
        SummaryLikelihoodText.setText("");
        SummaryImpactTextOut.setText("");
        riskComputedSQL = "INSERT INTO risk_change (`Row`, `Time`, `risk_status`) VALUES ('" + (rowCountChange + 1) + "', '" + formattedDateTime + "', 'Success');";
        model.addRow(new Object[] {formattedDateTime, "Success"});
        Object[] data = {assetsSelected, vuln, likelihoodCalcuation, likelihoodCalcuation, riskScoreOutput};
        risk.addRow(data);
    } else {
        System.out.println("No rows Inserted");
        Tab_tabbedPane.setSelectedIndex(3);
        riskComputedSQL = "INSERT INTO risk_change (`Row`, `Time`, `risk_status`) VALUES ('" + (rowCountChange + 1) + "', '" + formattedDateTime + "', 'Failure');";
        model.addRow(new Object[] {formattedDateTime, "Failure"});
    }
    Statement riskComputedStmt = con.createStatement();
    int riskComputedRowsInserted = riskComputedStmt.executeUpdate(riskComputedSQL);
    
    // Close the connection to the MySQL database
    con.close();
  } catch (Exception e) {
     System.out.println(e);
  }


} 
    }//GEN-LAST:event_NextMouseClicked

    public void riskScoreTabInit(){
        
        
    }
    
    
    private void AssetListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AssetListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AssetListActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BackBtnActionPerformed

    private void BackBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackBtnMouseClicked
     if (jTabbedPane2.getModel().getSelectedIndex() == 0){
       System.out.println("Out Of Bounds");
     } else {
     jTabbedPane2.setSelectedIndex(jTabbedPane2.getSelectedIndex()-1);
    
    }//GEN-LAST:event_BackBtnMouseClicked
    }

    private void jFrame1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jFrame1PropertyChange
        
    }//GEN-LAST:event_jFrame1PropertyChange

    private void jFrame1WindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jFrame1WindowActivated
        
    }//GEN-LAST:event_jFrame1WindowActivated

    private void jFrame1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFrame1KeyPressed
   
    }//GEN-LAST:event_jFrame1KeyPressed

    private void RiskScorePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RiskScorePanelMouseClicked
        Tab_tabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_RiskScorePanelMouseClicked

    private void Filter_SeverityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Filter_SeverityMouseClicked
  JTable table = RiskScoreTable;
  DefaultTableModel model = (DefaultTableModel) table.getModel();
  int rowCount = model.getRowCount();
  int column = 4;

  if (Filter_Severity.getText().equals("Filter Severity")) {
    if (rowCount > 0) {
      for (int i = 0; i < rowCount; i++) {
        for (int j = i + 1; j < rowCount; j++) {
          int score1 = getScore((String) model.getValueAt(i, column));
          int score2 = getScore((String) model.getValueAt(j, column));
          if (score2 > score1) {
            model.moveRow(j, j, i);
          }
        }
      }
    }
    Filter_Severity.setText("Filter Normal");
  } else if (Filter_Severity.getText().equals("Filter Normal")) {
    if (rowCount > 0) {
      for (int i = 0; i < rowCount; i++) {
        for (int j = i + 1; j < rowCount; j++) {
          int score1 = getScore((String) model.getValueAt(i, column));
          int score2 = getScore((String) model.getValueAt(j, column));
          if (score1 > score2) {
            model.moveRow(j, j, i);
          }
        }
      }
    }
    Filter_Severity.setText("Filter Severity");
  }
}

private int getScore(String riskScore) {
  switch (riskScore) {
    case "High":
      return 3;
    case "Medium":
      return 2;
    case "Low":
      return 1;
    default:
      return 0;
  }
        
        
//        if (Filter_Severity.getText() == "Filter Severity"){
//          
//            
//            Filter_Severity.setText("Filter Normal");
//        } else if (Filter_Severity.getText() == "Filter Normal"){
//            
//            
//            Filter_Severity.setText("Filter Severity");
//        }
    }//GEN-LAST:event_Filter_SeverityMouseClicked

    private void Filter_SeverityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Filter_SeverityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Filter_SeverityActionPerformed

    private void AssetPanelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AssetPanelKeyPressed
     //delete
    }//GEN-LAST:event_AssetPanelKeyPressed

    private void AssetPanelTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AssetPanelTextMouseClicked
          Tab_tabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_AssetPanelTextMouseClicked

    private void AssetPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AssetPanelMouseClicked
           Tab_tabbedPane.setSelectedIndex(0);
           //initTables();
    }//GEN-LAST:event_AssetPanelMouseClicked

    private void ThreatLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThreatLabelMouseClicked
           Tab_tabbedPane.setSelectedIndex(4);
    }//GEN-LAST:event_ThreatLabelMouseClicked

    private void RiskScoreTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RiskScoreTextMouseClicked
        Tab_tabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_RiskScoreTextMouseClicked

    private void CreateNewThreatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateNewThreatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CreateNewThreatActionPerformed

    private void CreateNewThreatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CreateNewThreatMouseClicked
        threatReg s = new threatReg();
        s.setLocationRelativeTo(null);
       s.setVisible(true);
    }//GEN-LAST:event_CreateNewThreatMouseClicked

    private void createNewAssetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createNewAssetMouseClicked
        // TODO add your handling code here:
       assetReg s = new assetReg();
       s.setLocationRelativeTo(null);
       s.setVisible(true);
    }//GEN-LAST:event_createNewAssetMouseClicked

    private void createNewAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewAssetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createNewAssetActionPerformed

    private void AssetUpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AssetUpdateBtnActionPerformed
      //     assetNames.clear();
      //     assetDes.clear();
            InitsqlQuerys();
            initTables();


        

    }//GEN-LAST:event_AssetUpdateBtnActionPerformed

    private void update_ThreatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_update_ThreatsMouseClicked
         InitsqlQuerys();
            initTables();
    }//GEN-LAST:event_update_ThreatsMouseClicked

    private void update_ThreatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_ThreatsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_ThreatsActionPerformed

    private void AssetDeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AssetDeleteBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AssetDeleteBtnActionPerformed

    private void AssetDeleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AssetDeleteBtnMouseClicked
        System.out.println(assetTable.getSelectedRow());
int selectedRow = assetTable.getSelectedRow();
if (selectedRow != -1) {
  DefaultTableModel model = (DefaultTableModel) assetTable.getModel();
  String column2Value = (String) model.getValueAt(selectedRow, 1);
  String column3Value = (String) model.getValueAt(selectedRow, 2);
  

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");

      //Check connection
      if (con != null) {
        System.out.println("Success");
      }

      String sql = "DELETE FROM assets WHERE AssetName = ? AND Description = ?";
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1, column2Value);
      stmt.setString(2, column3Value);
      stmt.executeUpdate();
      model.removeRow(selectedRow);
      Tab_tabbedPane.setSelectedIndex(3);
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  
  
  
}    
    }//GEN-LAST:event_AssetDeleteBtnMouseClicked

    private void AssetUpdateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AssetUpdateBtnMouseClicked
           InitsqlQuerys();
          initTables();
    }//GEN-LAST:event_AssetUpdateBtnMouseClicked

    private void Threat_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Threat_DeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Threat_DeleteActionPerformed

    private void DashboardPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardPanelMouseClicked
       Tab_tabbedPane.setSelectedIndex(3);
    }//GEN-LAST:event_DashboardPanelMouseClicked

    private void Threat_DeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Threat_DeleteMouseClicked
        System.out.println(threat_table.getSelectedRow());
int selectedRow = threat_table.getSelectedRow();
if (selectedRow != -1) {
  DefaultTableModel model = (DefaultTableModel) threat_table.getModel();
  String column2Value = (String) model.getValueAt(selectedRow, 1);
  String column3Value = (String) model.getValueAt(selectedRow, 2);

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");

      //Check connection
      if (con != null) {
        System.out.println("Success");
      }

      String sql = "DELETE FROM threats WHERE Threat = ? AND Threat_Description = ?";
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1, column2Value);
      stmt.setString(2, column3Value);
      stmt.executeUpdate();
      model.removeRow(selectedRow);
      Tab_tabbedPane.setSelectedIndex(3);
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
}
    }//GEN-LAST:event_Threat_DeleteMouseClicked

    private void RiskScoreDeleteBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RiskScoreDeleteBtnMouseClicked
    int selectedRow = RiskScoreTable.getSelectedRow();
    if (selectedRow != -1) {
        DefaultTableModel model = (DefaultTableModel) RiskScoreTable.getModel();
        String column2Value = (String) model.getValueAt(selectedRow, 1);
        String column3Value = (String) model.getValueAt(selectedRow, 2);
        String column4Value = (String) model.getValueAt(selectedRow, 3);
        String column5Value = (String) model.getValueAt(selectedRow, 4);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");

            // Check connection
            if (con != null) {
                System.out.println("Success");
            }

            String sql = "DELETE FROM risk_score WHERE Asset_Names = ? AND risk_vuln= ? AND risk_likelihood = ? AND risk_Impact = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, column2Value);
            stmt.setString(2, column3Value);
            stmt.setString(3, column4Value);
            stmt.setString(4, column5Value);
           // stmt.setString(5, column6Value);
            stmt.executeUpdate();
            model.removeRow(selectedRow);
            Tab_tabbedPane.setSelectedIndex(3);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }







    }//GEN-LAST:event_RiskScoreDeleteBtnMouseClicked

    private void AuditPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AuditPanelMouseClicked
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(RiskScoreTable.getPrintable(JTable.PrintMode.FIT_WIDTH, null, null));
            if (job.printDialog()) {
                job.print();
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }        
//Tab_tabbedPane.setSelectedIndex(5);
    }//GEN-LAST:event_AuditPanelMouseClicked

    private void HelpPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HelpPanelMouseClicked
        Tab_tabbedPane.setSelectedIndex(5);
    }//GEN-LAST:event_HelpPanelMouseClicked
   
    
    public static void InitsqlQuerys(){
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "VG8tKJpMhJm336_");
      
      //Check connection
      if (con != null) {
    	  System.out.println("Success");
      }
   
        Statement stmt = con.createStatement();
        ResultSet assetNamez = stmt.executeQuery("SELECT * FROM assets");
      while (assetNamez.next()) {
     String name = assetNamez.getString("AssetName");
     if (!assetNames.contains(name)) {
      assetNames.add(name);
      assetDes.add(assetNamez.getString("Description"));
    }
  }

     
     //Threat Table Input
     Statement threatState=con.createStatement();
     ResultSet threatResultSet=threatState.executeQuery("SELECT * FROM threats");
  while (threatResultSet.next()) {
    String threat = threatResultSet.getString("Threat");
    if (!Threat.contains(threat)) {
      Threat.add(threat);
      Threat_Des.add(threatResultSet.getString("Threat_Description"));
    }
  }
 //RiskScoreTable Input
Statement riskScoreState = con.createStatement();
ResultSet riskScoreResultSet = riskScoreState.executeQuery("SELECT * FROM risk_Score");
while (riskScoreResultSet.next()) {
String assetName = riskScoreResultSet.getString("Asset_Names");
if (!assetNamesRisk.contains(assetName)) {
assetNamesRisk.add(assetName);
risk_vuln.add(riskScoreResultSet.getString("risk_vuln"));
likelihood.add(riskScoreResultSet.getString("risk_likelihood"));
Impact.add(riskScoreResultSet.getString("risk_Impact"));
riskScore.add(riskScoreResultSet.getString("risk_Score"));
}
}     
  
  
//RiskScoreTable Input
//     Statement risk=con.createStatement();
//     ResultSet riskResults=risk.executeQuery("SELECT * FROM risk_Score");
//     while (riskResults.next()){
//         assetNamesRisk.add(riskResults.getString("Asset_Names"));
//         risk_vuln.add(riskResults.getString("risk_vuln"));
//         likelihood.add(riskResults.getString("risk_likelihood"));
//         Impact.add(riskResults.getString("risk_Impact"));
//         riskScore.add(riskResults.getString("risk_Score"));
//     }
     
     date_time.clear();
status.clear();
Statement homePage = con.createStatement();
ResultSet homePageResults = homePage.executeQuery("SELECT * FROM risk_change");
while (homePageResults.next()) {
    date_time.add(homePageResults.getString("Time"));
    status.add(homePageResults.getString("risk_status"));
}
     
//     Statement homePage = con.createStatement();
//ResultSet homePageResults = homePage.executeQuery("SELECT * FROM risk_change");
//while (homePageResults.next()) {
//    String date = homePageResults.getString("Time");
//    if (!date_time.contains(date)) {
//        date_time.add(date);
//        status.add(homePageResults.getString("risk_status"));
//    }
//}
     
     
     
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
    private javax.swing.JButton AssetDeleteBtn;
    private javax.swing.JLabel AssetHomepage_Text;
    private java.awt.List AssetList;
    private javax.swing.JLabel AssetManagementHelpText;
    private javax.swing.JLabel AssetManagmentText;
    private javax.swing.JPanel AssetPanel;
    private javax.swing.JLabel AssetPanelText;
    private javax.swing.JPanel AssetSelectionPanel;
    private javax.swing.JPanel AssetTableBackgroundPanel;
    private javax.swing.JButton AssetUpdateBtn;
    private javax.swing.JLabel Asset_HomepageLable;
    private javax.swing.JLabel Asset_Showing;
    private javax.swing.JLabel AttackComplexityText;
    private javax.swing.JLabel AttackVectorText;
    private javax.swing.JLabel AuditHelpText;
    private javax.swing.JPanel AuditPanel;
    private javax.swing.JLabel AuditText;
    private javax.swing.JLabel AvailabilityText;
    private javax.swing.JButton BackBtn;
    private javax.swing.JScrollPane BackgroundMain;
    private javax.swing.JLabel CIAText;
    private javax.swing.JLabel ConfidentialityText;
    private javax.swing.JLabel ConsequencesText;
    private javax.swing.JButton CreateNewThreat;
    private javax.swing.JPanel CriticalPanel;
    private javax.swing.JLabel CriticalText;
    private javax.swing.JLabel DashboardJLabel;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JLabel DisruptionOfServiceText;
    private javax.swing.JLabel FeaturesLabel;
    private javax.swing.JPanel FeaturesPanel;
    private javax.swing.JButton Filter_Severity;
    private javax.swing.JLabel FinancialDamageText;
    private javax.swing.JPanel Help;
    private javax.swing.JPanel HelpBackground;
    private javax.swing.JPanel HelpPanel;
    private javax.swing.JLabel HelpText;
    private javax.swing.JLabel High7Text;
    private javax.swing.JLabel High8Text;
    private javax.swing.JPanel HomePageAssetPanel;
    private javax.swing.JPanel HomePageRiskPanel;
    private javax.swing.JPanel HomePageThreatPanel;
    private javax.swing.JLabel Home_Icon;
    private javax.swing.JPanel Homepage;
    private javax.swing.JPanel HomepageTable;
    private javax.swing.JLabel ImpactHighText;
    private javax.swing.JLabel ImpactLowText;
    private javax.swing.JLabel ImpactMediumText;
    private javax.swing.JLabel IntegrityText;
    private javax.swing.JLabel IntroductionHelpText;
    private javax.swing.JLabel LikelihoodHighText;
    private javax.swing.JLabel LikelihoodLowText;
    private javax.swing.JLabel LikelihoodMediumText;
    private javax.swing.JLabel LogoLabel;
    private javax.swing.JLabel Low2Text;
    private javax.swing.JPanel LowPanel;
    private javax.swing.JLabel MainImpactText;
    private javax.swing.JLabel MainLikelihoodText;
    private javax.swing.JLabel ManagementJLabel;
    private javax.swing.JPanel ManagementPanel;
    private javax.swing.JLabel Medium4;
    private javax.swing.JLabel Medium5Text;
    private javax.swing.JPanel MiddleMediumpanel;
    private javax.swing.JPanel NavigationBarPanel;
    private javax.swing.JButton Next;
    private javax.swing.JLabel NonComplianceRiskText;
    private javax.swing.JLabel PrivilegesText;
    private javax.swing.JLabel ReputationText;
    private javax.swing.JPanel RightHighPanel;
    private javax.swing.JPanel RightMediumPanel;
    private javax.swing.JLabel RiskAssessmentPanel;
    private javax.swing.JPanel RiskCalcPanel;
    private javax.swing.JLabel RiskCalcText;
    private javax.swing.JLabel RiskCalc_Homepage;
    private javax.swing.JLabel RiskRatingText;
    private javax.swing.JButton RiskScoreDeleteBtn;
    private javax.swing.JLabel RiskScoreHelpText;
    private javax.swing.JLabel RiskScoreManagementText;
    private javax.swing.JLabel RiskScoreManagmentHelpText;
    private javax.swing.JPanel RiskScorePanel;
    private javax.swing.JTable RiskScoreTable;
    private javax.swing.JPanel RiskScoreTableBackgroundPanel;
    private javax.swing.JLabel RiskScoreText;
    private javax.swing.JPanel SavePanel;
    private javax.swing.JLabel SaveTextLabel;
    private javax.swing.JPanel SaveTextPanel;
    private javax.swing.JLabel ScopeText;
    private javax.swing.JPanel SelectAssetPanel;
    private javax.swing.JLabel SelectAssetText;
    private javax.swing.JLabel SelectAssetsLabel;
    private javax.swing.JLabel SelectThreatLabel;
    private javax.swing.JPanel SelectThreatPanel;
    private javax.swing.JLabel SelectThreatText;
    private javax.swing.JLabel Showing_RiskScore;
    private javax.swing.JLabel Showing_threats;
    private javax.swing.JLabel SummaryAssetText;
    private javax.swing.JLabel SummaryImpactTextOut;
    private javax.swing.JLabel SummaryLikelihoodText;
    private javax.swing.JPanel SummaryPanel;
    private javax.swing.JLabel SummaryRiskScoreText;
    private javax.swing.JLabel SummarySelectedAssetText;
    private javax.swing.JLabel SummaryText;
    private javax.swing.JLabel SummaryThreatSelectionText;
    private javax.swing.JLabel SummaryThreatText;
    public javax.swing.JTabbedPane Tab_tabbedPane;
    private javax.swing.JPanel ThreatJPanel;
    private javax.swing.JLabel ThreatLabel;
    private java.awt.List ThreatList;
    private javax.swing.JLabel ThreatManagementText;
    private javax.swing.JLabel ThreatManagmentHelpText;
    private javax.swing.JPanel ThreatPanel;
    private javax.swing.JPanel ThreatSelecitonPanel;
    private javax.swing.JPanel ThreatTableBackground;
    private javax.swing.JButton Threat_Delete;
    private javax.swing.JPanel TopHighPanel;
    private javax.swing.JPanel TopRiskAssessmentPanel;
    private javax.swing.JPanel TopSummaryPanel;
    private javax.swing.JLabel TraceabilityText;
    private javax.swing.JLabel Vuln_homepageLabel;
    private javax.swing.JLabel VulnerabilityText;
    private javax.swing.JLabel Vulnerabilitys_HomepageLabel;
    private javax.swing.JPanel assetJpanel;
    private javax.swing.JTable assetTable;
    private javax.swing.JComboBox<String> attackComplexityBox;
    private javax.swing.JComboBox<String> attackVectorBox;
    private javax.swing.JComboBox<String> availabilityBox;
    private javax.swing.JPanel calcRiskPanel;
    private javax.swing.JComboBox<String> confidentialityBox;
    private javax.swing.JButton createNewAsset;
    private javax.swing.JComboBox<String> disruptionBox;
    private javax.swing.JComboBox<String> financialBox;
    private javax.swing.JTable homePageTable;
    private javax.swing.JComboBox<String> integrityBox;
    private javax.swing.JButton jButton6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JPanel leftMediumPanel;
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
    private javax.swing.JButton update_Threats;
    // End of variables declaration//GEN-END:variables
}
