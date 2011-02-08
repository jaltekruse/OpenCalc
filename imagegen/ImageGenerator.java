package imagegen;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

import tree.BinExpression;
import tree.Decimal;
import tree.EvalException;
import tree.Expression;
import tree.Operator;
import tree.Value;
import tree.Number;
import tree.Fraction;
import tree.Var;
import gui.CalcPanel;
import gui.MainApplet;

public class ImageGenerator {
        private MainApplet mainApp;
        public ImageGenerator(MainApplet main){
                mainApp = main;
        }
        public void printImage(Value v, Component c) throws EvalException{
                Graphics g = c.getGraphics();
                Font oldFont = c.getFont();
                Font calcFont = new Font("Mathematica7.pfa", Font.BOLD, 16);
                c.setFont(calcFont);
                if(v instanceof Fraction){
                        Fraction v1 = (Fraction)v;
                        switch(mainApp.getParser().getGUI().getCalcPanel().getAnswersIn()){
                        case 1:
                                g.drawString(v1.toString(), 15, 15);
                                c.print(g);
                                break;
                        case 2:
                                g.drawString(v1.toDec().toString(), 70, 30);
                                break;
                        default:
                                break;
                        }
                }
                
        }
        
        private BufferedImage getImage(String fileName){
        BufferedImage tempImage = null;
        try {
            //System.out.println("Version2_FILENAME!!!!!:  " + fileName);
            tempImage =  ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/" + fileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return tempImage;
    }
}

