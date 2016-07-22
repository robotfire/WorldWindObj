package osm.map.worldwind.gl.obj;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import static gov.nasa.worldwindx.examples.ApplicationTemplate.insertBeforeCompass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Tester extends ApplicationTemplate {

	public static class AppFrame extends ApplicationTemplate.AppFrame {
		Position pos;
		RenderableLayer layer;
		Timer timer;
//		double alt = 180;
		double alt =270;
		private ObjRenderable renderable;

		public AppFrame() {

			String model550="C:/RaptorX/projects/models/djif550mitGoPro/model.dae";
			String model950="C:/RaptorX/projects/WorldWind-Ardor3D/ArdorModelLoader/models/obj/Hexa950modOBJ/Hexa950mod.obj";
			String pitcher="C:/RaptorX/projects/WorldWind-Ardor3D/ArdorModelLoader/models/obj/pitcher.obj";
            String modelep3="C:/RaptorX/projects/models/ep3/ep3b.obj";
			String copter = "C:/RaptorX/projects/models/copter/copter.obj";
//			String model950 = "C:/RaptorX/projects/models/Hexacopter Attack Drone/Hexa 950 mod 3DS/Hexa 950 mod.3DS";
//			String model950 = "C:/RaptorX/projects/models/Hexacopter Attack Drone/3ds/hexa.3ds";

			layer = new RenderableLayer();
//			pos = Position.fromDegrees(30, -100, alt);
			pos = Position.fromDegrees(35.77750,-120.80565,alt);

//			this.renderable = new ObjRenderable(pos, model950, true, false);
			this.renderable = new ObjRenderable(pos, copter, false, true);
//			this.renderable.setSize(100);
			this.renderable.setSize(1);
//			this.renderable.setAzimuth(90);
			this.renderable.setKeepConstantSize(true);
//			this.renderable.setElevation(-90);
//            model.setYaw(55);
			layer.addRenderable(this.renderable);
			insertBeforeCompass(getWwd(), layer);

			this.timer = new Timer(1000, new ActionListener() {
				boolean first = true;
				@Override
				public void actionPerformed(ActionEvent e) {
					if(first) {
						gotoPos();											
						first =false;
					} else {
						updatePosition();
					}
				}
			});
			timer.start();
		}

		private void gotoPos() {
			this.getWwd().getView().goTo(pos, alt);
		}

		private void updatePosition() {
//			pos = pos.add(Position.fromDegrees(.01, .01));
//			model.setPosition(pos);
			this.renderable.setPosition(pos);
//			model.setYaw(model.getYaw()+45); // roll
//			model.setRoll(model.getRoll()+45);
//			model.setPitch(model.getPitch()+45);
//			model.setPitch(model.getPitch()+(Math.random()-.5)*5);
			layer.firePropertyChange(AVKey.LAYER,null,this);
			//this.getWwd().redraw();
//			timer.stop();
		}


		protected RenderableLayer getLayer() {
			for (Layer layer : getWwd().getModel().getLayers()) {
				if (layer.getName().contains("Renderable")) {
					return (RenderableLayer) layer;
				}
			}

			return null;
		}

	}

	public static void main(String[] args) {
		ApplicationTemplate.start("World Wind Cones", AppFrame.class);
	}
}
