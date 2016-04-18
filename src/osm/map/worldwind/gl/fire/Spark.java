package osm.map.worldwind.gl.fire;

public class Spark extends Particle {
    
    public float roty, rotx, inc;
    public float size;
    private int type;
    
    public Spark (float pos[], float veloc[], float life) {
        super(pos, veloc, life);
        this.roty = (float) (Math.random()*360);
        this.size = (float) (Math.random()*15+10f);
        this.inc = (float) Math.random();
        this.type = 0;    
    }
    
    public int getType() {
        return this.type;
    }
    
}
