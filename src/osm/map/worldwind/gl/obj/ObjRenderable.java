package osm.map.worldwind.gl.obj;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;

import gov.nasa.worldwind.render.DrawContext;
import java.awt.Toolkit;
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

//		final double meshSize = modelRenderable.getBoundingBox().getDiameter();
//		final double currDist = dc.getView().getEyePoint().distanceTo3(dc.getGlobe().computePointFromPosition(position));
//		final double unitPixel = dc.getView().computePixelSizeAtDistance(currDist);
//		return Math.min(modelRenderable.getMaximumScale(), unitPixel * pixelSize / meshSize);

	private double getPixelsPerMeter() {
		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
		return dpi/.0254;
	}

	@Override
	protected double computeSize(DrawContext dc, Vec4 loc) {
		if (this.keepConstantSize) {
			return size;
		}
		if (loc == null) {
			System.err.println("Null location when computing size");
			return 1;
		}
		double d = loc.distanceTo3(dc.getView().getEyePoint());
		double metersPerPixel = dc.getView().computePixelSizeAtDistance(d);
		double dpm = this.getPixelsPerMeter();

		double modelSizeMeters = this.eyeDistanceOffset;
		double modelPixels = modelSizeMeters / metersPerPixel;

		double scale = size/modelPixels;

		if(scale < .5) {
			scale = .5;
		}
		return scale;
	}

}
