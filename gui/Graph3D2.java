package gui;

import tree.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Graph3D2
{
    public Graph3DPanel panel;
    private GraphFunc[] functions;
    private final double[][] AXES = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    private double[] viewAngles = {.8, .8, 0}; // Euler angles between view plane basis and XY plane
    private double[] viewCenter = {0, 0, -11}; // Center of view plane
    // NOTE: View basis must be orthonormal.
    private double[] focus = {0, 0, -9};
    private enum Mode
    {
        MESH, CONTOUR, GLOSSY;
    }
    private Mode renderMode;
    private int NUM_DIVISIONS;

    private Listener l;

    public Graph3D2(Graph3DPanel p)
    {
        super();
        panel = p;
        renderMode = Mode.MESH;
        NUM_DIVISIONS = 150;

        functions = new GraphFunc[0];

        /*view = new double[3][2];
        focus = new double[3];
        dist = 1.0;*/
    }
    public Graph3D2(Graph3DPanel p, Expression[] e)
    {
        super();
        panel = p;
        renderMode = Mode.MESH;
        NUM_DIVISIONS = 150;

        functions = new GraphFunc[e.length];
        int i;
        for(i = 0; i < e.length; i++)
            functions[i] = new GraphFunc(e[i]);

        /*view = new double[3][2];
        focus = new double[3];
        dist = 1.0;*/

        l = new Listener();
        panel.addKeyListener(l);
    }

    public void addFunction(Expression e, Color c)
    {
        GraphFunc funcs[] = new GraphFunc[functions.length + 1];
        int i;
        for(i = 0; i < functions.length; i++)
            funcs[i] = functions[i];
        funcs[functions.length] = new GraphFunc(e);
        funcs[functions.length].color = c;
        functions = funcs;
    }
    public Expression[] getFunctions()
    {
        Expression[] out = new Expression[functions.length];
        int i;

        if(functions.length == 0)
            return null;

        for(i = 0; i < functions.length; i++)
            out[i] = functions[i].expression;

        return out;
    }

    public void render(Graphics g)
    {
        double[] p = {.5, 0};
        p = transform(p);
        System.out.println("" + p[0] + ", " + p[1] + ", " + p[2]);
        renderMesh(g);
    }
    private void renderMesh(Graphics g)
    {
        double[][][] proj;
        double[] p = new double[2];
        int i, j;

        for(GraphFunc f: functions)
        {
            proj = projView(f);
            for(i = 0; i < NUM_DIVISIONS; i++)
            {
                for(j = 0; j <  NUM_DIVISIONS; j++)
                {
                    if(!Double.isNaN(proj[i][j][0]) && !Double.isNaN(proj[i][j][1]))
                    {
                        g.setColor(f.colorize(proj[i][j]));
                        g.fillOval((int)((double)i / (double)NUM_DIVISIONS * (double)panel.getWidth() + .5), (int)((double)j / (double)NUM_DIVISIONS * (double)panel.getHeight()), 2, 2);
                    }
                }
            }
        }
    }
    private void renderContour(Graphics g)
    {
    }
    private void renderGlossy(Graphics g)
    {
    }

    private double[][][] projView(GraphFunc f)
    {
        double[][][] proj = new double[NUM_DIVISIONS][NUM_DIVISIONS][2];
        double[] p = new double[2];
        int i, j;

        for(i = 0; i < NUM_DIVISIONS; i++)
        {
            for(j = 0; j < NUM_DIVISIONS; j++)
            {
                p[0] = (double)i / (double)NUM_DIVISIONS;
                p[1] = (double)j / (double)NUM_DIVISIONS;
                proj[i][j] = projPoint(f, p);
            }
        }

        return proj;
    }

    private double[] projPoint(GraphFunc f, double[] p)
    {
        final double[] NaN = {Double.NaN, Double.NaN};
        double[] x = new double[2];
        x[0] = focus[0];
        x[1] = focus[1];
        double[] xPast = new double[2];
        double z1 = focus[2], z2;
        double zSlope;
        double diff1, diff2 = 0.0;
        double deriv;
        double[] v = new double[2], temp = new double[3];
        boolean firstTime = true;

        final double DELTA = 1e-5;
        try
        {
            p = transform(p);
            p = add(focus, mult(-1, p));
            temp = p;
            p = setLength(p, 2);
            zSlope = temp[2] / mag(p);
            p = mult(1 / mag(p), p);

            v[0] = DELTA * p[0];
            v[1] = DELTA * p[1];
            xPast[0] = x[0];
            xPast[1] = x[1];
            do
            {
                z1 = z1 + (mag(x) - mag(xPast)) * zSlope;
                diff1 = Math.abs(f.evaluate(x) - z1);
                if(!firstTime && Math.abs(diff1) > Math.abs(diff2))
                    return NaN;
                z2 = z1 + (mag(add(x, v)) - mag(x)) * zSlope;
                diff2 = f.evaluate(add(x, v)) - z2;
                deriv = (diff2 - diff1) / DELTA;
                xPast = x;
                x = add(x, mult(-1 * diff1 / deriv, p));
                firstTime = false;
            } while(Math.abs(diff1) > DELTA);
            return x;
        }
        catch(EvalException e)
        {
            return NaN;
        }
    }
    private double[] transform(double[] p)
    {
        double[][] A = rotMatrix(viewAngles);
        double[] t = new double[3];

        p[0] -= .5;
        p[1] = .5 - p[1];
        p = setLength(p, 3);
        // matrix multiplication...[...]
        t[0] = dot(A[0], p);
        t[1] = dot(A[1], p);
        t[2] = dot(A[2], p);
        // ---------------
        t = add(t, viewCenter);

        return t;
    }

    private double focalDist()
    {
        return mag(add(viewCenter, mult(-1, focus)));
    }

    /* Basic linear algebra functions; should be replaced by
     * linear algebra library.
     */
    private double[][] rotMatrix(double[] angles)
    {
        double[][] A = new double[3][3];
        A[0][0] = Math.cos(angles[0]) * Math.cos(angles[1]);
        A[0][1] = Math.cos(angles[0]) * Math.sin(angles[1]) * Math.sin(angles[2]) - Math.sin(angles[0]) * Math.cos(angles[2]);
        A[0][2] = Math.sin(angles[0]) * Math.sin(angles[2]) - Math.cos(angles[0]) * Math.sin(angles[1]) * Math.cos(angles[2]);
        A[1][0] = Math.sin(angles[0]) * Math.cos(angles[1]);
        A[1][1] = Math.sin(angles[0]) * Math.sin(angles[1]) * Math.sin(angles[2]) + Math.cos(angles[0]) * Math.cos(angles[2]);
        A[1][2] = Math.sin(angles[0]) * Math.sin(angles[1]) * Math.cos(angles[2]) - Math.cos(angles[0]) * Math.sin(angles[2]);
        A[2][0] = -1.0 * Math.sin(angles[1]);
        A[2][1] = Math.cos(angles[1]) * Math.sin(angles[2]);
        A[2][2] = Math.cos(angles[1]) * Math.cos(angles[2]);
        return A;
    }
    private double[] setLength(double[] v, int l)
    {
        double[] u = new double[l];
        int i;

        for(i = 0; i < v.length && i < l; i++)
            u[i] = v[i];
        for(; i < l; i++)
            u[i] = 0;

        return u;
    }
    private double[] add(double[] a, double[] b)
    {
        double[] s = new double[a.length];
        int i;
        for(i = 0; i < s.length; i++)
            s[i] = a[i] + b[i];
        return s;
    }
    private double[] mult(double a, double[] x)
    {
        double[] y = new double[x.length];
        int i;
        for(i = 0; i < y.length; i++)
            y[i] = a * x[i];
        return y;
    }
    private double dot(double[] a, double[] b)
    {
        double s = 0.0;
        int i = 0;

        for(i = 0; i < a.length; i++)
            s += a[i] * b[i];
        return s;
    }
    private double mag(double[] v)
    {
        double mag = 0;
        int i;
        for(i = 0; i < v.length; i++)
            mag += Math.pow(v[i], 2.0);
        mag = Math.pow(mag, .5);
        return mag;
    }
    private int round(double x)
    {
        return (int)(x + .5);
    }
    /*
     * ------------------------
     */

    /* Probably to be replaced or extended outside Graph3D.
     * Class Function should be created to replace Expression
     * in this application.
     */
    private class GraphFunc
    {
        public Expression expression;
        public Color color = Color.RED;

        public GraphFunc(Expression e)
        {
            super();
            expression = e;
        }

        public Color colorize(double[] p)
        {
            double precision = mag(focus) / focalDist() / (double)NUM_DIVISIONS;
            if(Math.abs(p[0] - (double)(int)p[0]) < precision || Math.abs(p[1] - (double)(int)p[1]) < precision)
                return Color.BLACK;
            else
                return color;
        }

        public double evaluate(double[] p) throws EvalException
        {
            panel.varList.setVarVal("x", new Decimal(p[0]));
            panel.varList.setVarVal("y", new Decimal(p[1]));
            return ((Decimal)expression.eval()).getValue();
        }
    }

    private class Listener extends MouseInputAdapter implements KeyListener
    {
        public void keyPressed(KeyEvent k)
        {
        }
        public void keyReleased(KeyEvent k)
        {
            System.out.println("MOO!");
            int key = k.getKeyCode();

            boolean r = true;
            switch(key)
            {
                case KeyEvent.VK_1: viewAngles[0] += .2; break;
                case KeyEvent.VK_2: viewAngles[1] += .2; break;
                case KeyEvent.VK_3: viewAngles[2] += .2; break;
                case KeyEvent.VK_4: viewAngles[0] -= .2; break;
                case KeyEvent.VK_5: viewAngles[1] -= .2; break;
                case KeyEvent.VK_6: viewAngles[2] -= .2; break;
                default: r = false;
            }
            if(r)
                panel.repaint();
        }
        public void keyTyped(KeyEvent k)
        {
        }

        public void mousePressed(MouseEvent m)
        {
        }
        public void mouseReleased(MouseEvent m)
        {
        }
        public void mouseClicked(MouseEvent m)
        {
        }
    }
}