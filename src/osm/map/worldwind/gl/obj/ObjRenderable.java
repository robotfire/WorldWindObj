package osm.map.worldwind.gl.obj;

import com.jogamp.opengl.GL2;
import gov.nasa.worldwind.geom.Position;

import gov.nasa.worldwind.render.DrawContext;
import osm.map.worldwind.gl.GLRenderable;

public class ObjRenderable extends GLRenderable {

    ObjLoader loader = null;
    String modelSource;
    boolean centerit = false;
    boolean flipTextureVertically = false;
    ObjLoaderProgressListener listener = null;

    public ObjRenderable(Position pos, String modelSource, ObjLoaderProgressListener listener) {
        super(pos);
        this.modelSource = modelSource;
        this.listener = listener;
    }

    public ObjRenderable(Position pos, String modelSource, boolean centerit, boolean flipTextureVertically, ObjLoaderProgressListener listener) {
        super(pos);
        this.modelSource = modelSource;
        this.centerit = centerit;
        this.flipTextureVertically = flipTextureVertically;
        this.listener = listener;
    }

    //**************************************************************************
    //*** API
    //**************************************************************************
    public ObjLoader getLoader() {
        return loader;
    }
    
    
    //**************************************************************************
    //*** GLRenderable
    //**************************************************************************
    @Override
    protected void drawGL(DrawContext dc) {
        GL2 gl = dc.getGL().getGL2();
        // gl.glRotated(180, 1, 0, 0);
        // gl.glRotated(180, 0, 0, 1);
        
        //--- Loading conmplex objects will freeze the event thread
        if (loader == null) loader = new ObjLoader(modelSource, dc.getGL().getGL2(), centerit, flipTextureVertically, listener);
           
        eyeDistanceOffset = Math.max(Math.max(loader.getXWidth(), loader.getYHeight()), loader.getZDepth());
            
        loader.opengldraw(gl);

    }

}
