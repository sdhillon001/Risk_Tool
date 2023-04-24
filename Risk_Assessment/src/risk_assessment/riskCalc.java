/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package risk_assessment;

import java.util.Scanner;
import java.util.ArrayList;
import java.lang.reflect.Array;

/**
 *
 * @author sdhil
 */
public class riskCalc extends mainPage{
   
    // Vulnerability + Likelihood Scores
    private final float None = 1.0f;
    private final float Low = 2.5f;
    private final float Med = 5.0f;
    private final float High = 10.0f;

    // Impact Scores
    private final float Negligiable = 10.0f;
    private final float Minimal = 50.0f;
    private final float Reasonable = 100.0f;
    private final float Substantial = 500.0f;
   
    //Risk Outcome
    private static float riskOutcome;
    private float riskOutcomeTest;
    private float test3;
    private float test4;
    
    private final float Likelihood[] = {1.0f, 2.5f, 5.0f, 10.0f};
    private final float Impact[] = {10.0f, 50.0f, 100.0f, 500.0f};
    
    public void calculated(){
    mainPage p = new mainPage();
    int test = p.likelihoodOutputINT;
    int test1 = ImpactOutputINT;
    System.out.println(test + "test");
    System.out.println(ImpactOutputINT + "test1");
    
 
     test3 =  Array.getFloat(Likelihood, test);

     test4 =  Array.getFloat(Impact, test1);
    
      System.out.println(test3 + "likelihoodOutputINT"); 
      System.out.println(test4 + "ImpactOutputINT"); 
   riskOutcomeTest= test3 * test4;
    
   System.out.println(riskOutcomeTest + "outcome"); 
    
   // p.likelihoodOutput ;
        
        
        if(riskOutcomeTest <= 50){
            System.out.println("Light Green");
        }else if(riskOutcomeTest > 50 && riskOutcomeTest <=100){
            System.out.println("Dark Green");
        } else if (riskOutcomeTest > 100 && riskOutcomeTest <=299){
         System.out.println("Yellow");
        } else if (riskOutcomeTest >= 300 && riskOutcomeTest <= 999){
            System.out.println("Bright Red");
        }else if (riskOutcomeTest >= 1000 && riskOutcomeTest <= 5000){
            System.out.println("Dark Red");
        } else {
            System.out.println("OutofBounds");
        }
    }
    
    public static void main(String[] args){
   //  System.out.println("Welcome to RiskCalculator");
   //  Scanner sc = new Scanner(System.in);
   //  System.out.println("Input Number");
//     System.out.println("Enter Likelihood of attack occuring from these options:" + System.lineSeparator()
//             + "None" + System.lineSeparator() + "Low" + System.lineSeparator() + "Med" + System.lineSeparator() + "High");
//     String likelihood = sc.nextLine();
   // String str = sc.nextLine();
   // int input= Integer.parseInt(str);
   // System.out.println(input);
   // riskOutcome = input;
    riskCalc cal = new riskCalc();
    cal.calculated();
    
    }
    
}
