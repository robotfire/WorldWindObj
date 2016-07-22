package osm.map.worldwind.gl.fire;

import com.jogamp.opengl.GL2;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.render.*;

import java.awt.*;
import osm.map.worldwind.gl.GLRenderable;

public class FireRenderable extends GLRenderable {

    Fire fire;
    float radius, height, lifespan;
    int genRate;
    boolean initialized = false;

    public FireRenderable(Position position, float radius, float height, float lifespan, int genRate, double size) {
        super(position);
        this.radius = radius;
        this.height = height;
        this.lifespan = lifespan;
        this.genRate = genRate;
        this.size = size;
        this.useLighting = false;
    }
    
    public void reload() {
        initialized = false;
    }
    
    long previousTimeStamp = System.currentTimeMillis();
    protected void drawGL(DrawContext dc) {
        GL2 gl = dc.getGL().getGL2();
        if (!initialized) {
            if (fire != null) {
                fire.setGenRate(0);
            }
            fire = new Fire(radius, height, lifespan, genRate);
            initialized = true;
        }
        fire.draw(gl);
        long current = System.currentTimeMillis();
        if (current - previousTimeStamp > 30) {
            fire.update(0.2f);
            this.previousTimeStamp = current;
        }
    }
    
    public void setRadius(double radius) {
        fire.setRadius((float)radius);
    }
    
    public void setHeight(double height) {
        fire.setHeight((float)height);
    }
    
    public void setLifeSpan(double lifespan) {
        fire.setLifeSpan((float)lifespan);
    }
    
    public void setGenRate(int genRate) {
        fire.setGenRate(genRate);
    }
    
    public void setColor(Color color) {
        fire.setColor(color);
    }
    
}
