package isi.died.tp.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class AristaView {

    private VerticeView origen;
    private VerticeView destino;
    private Shape linea;
    private Stroke formatoLinea;
    private Paint color;

    public AristaView(VerticeView origen, VerticeView destino) {
    	this.origen = origen;
    	this.destino = destino;
    	this.color = Color.BLACK;
    }

    public Paint getColor() {
        if(this.color==null) this.color = new GradientPaint(origen.getCoordenadaX() + 10,origen.getCoordenadaY() + 10,destino.getColorBase(),destino.getCoordenadaX() + 10, destino.getCoordenadaY() + 10,origen.getColorBase());
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public boolean pertenece(Point2D p) {
        return this.linea.contains(p);
    }

    public Shape getLinea() {
        if(this.linea==null)  this.linea = new Line2D.Double(origen.getCoordenadaX()+25, origen.getCoordenadaY()+25, destino.getCoordenadaX()+25, destino.getCoordenadaY()+25);
        return linea;
    }

    public void setLinea(Shape linea) {
        this.linea = linea;
    }

    public Stroke getFormatoLinea() {
        if(this.formatoLinea==null)  this.formatoLinea = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);       
        return formatoLinea;
    }

    public void setFormatoLinea(Stroke formatoLinea) {
        this.formatoLinea = formatoLinea;
    }

    public VerticeView getOrigen() {
        return origen;
    }

    public void setOrigen(VerticeView origen) {
        this.origen = origen;
    }

    public VerticeView getDestino() {
        return destino;
    }

    public void setDestino(VerticeView destino) {
        this.destino = destino;
    }
    
    public void update() {
    	linea = new Line2D.Double(origen.getCoordenadaX()+25, origen.getCoordenadaY()+25, destino.getCoordenadaX()+25, destino.getCoordenadaY()+25);  
    }
    
    
    @Override
    public String toString() {
        return "Arista{" + "origen=" + origen + ", destino=" + destino + ", linea=" + linea + ", formatoLinea=" + formatoLinea + ", gradiente=" + color + '}';
    }
}