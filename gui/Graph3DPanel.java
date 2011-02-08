package gui;

/**
 *
 * @author Brendan DuBois
 */

import tree.*;

import java.awt.Graphics;
import java.awt.Dimension;

public class Graph3DPanel extends SubPanel
{
    private MainApplet calcObj;
    private Graph3D2 graph;
    private ExpressionParser parser;
    public VarStorage varList;

    public Graph3DPanel(int xSize, int ySize, MainApplet currCalcObj)
    {
        super();
        calcObj = currCalcObj;
        try
        {
            Expression[] expressions = new Expression[1];
            parser = new ExpressionParser();
            expressions[0] = (Expression)parser.ParseExpression("x + y^2");
            varList = parser.getVarList();
            graph = new Graph3D2(this, expressions);
        }
        catch(ParseException e)
        {
            graph = new Graph3D2(this);
        }

        setPreferredSize(new Dimension(xSize, ySize));
    }

    public void paint(Graphics g)
    {
        if(this != null && calcObj != null)
        {
            graph.render(g);
        }
    }
}