package osm.map.worldwind.gl.obj;

import gov.nasa.worldwind.geom.Position;

import gov.nasa.worldwind.render.DrawContext;
import java.util.HashMap;
import java.util.Map;
import javax.media.opengl.GL2;
import osm.map.worldwind.gl.GLRenderable;

public class ObjRenderable extends GLRenderable {

	static Map<String, ObjLoader> modelCache = new HashMap<>();
	String modelSource;
	boolean centerit = false, flipTextureVertically = false;

	public ObjRenderable(Position pos, String modelSource) {
		super(pos);
		this.modelSource = modelSource;
	}

	public ObjRenderable(Position pos, String modelSource, boolean centerit, boolean flipTextureVertically) {
		super(pos);
		this.modelSource = modelSource;
		this.centerit = centerit;
		this.flipTextureVertically = flipTextureVertically;
	}

	protected ObjLoader getModel(final DrawContext dc) {
		String key = modelSource + "#" + dc.hashCode();
		if (modelCache.get(key) == null) {
			modelCache.put(key, new ObjLoader(modelSource, dc.getGL().getGL2(), centerit, flipTextureVertically));
		}
		ObjLoader model = modelCache.get(key);
		eyeDistanceOffset = Math.max(Math.max(model.getXWidth(), model.getYHeight()), model.getZDepth());
		return modelCache.get(key);
	}

	public static void reload() {
		modelCache.clear();
	}

	@Override
	protected void drawGL(DrawContext dc) {
		GL2 gl = dc.getGL().getGL2();
		gl.glRotated(90, 1, 0, 0);
		getModel(dc).opengldraw(gl);
	}

}
