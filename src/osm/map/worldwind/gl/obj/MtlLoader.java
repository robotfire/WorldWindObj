package osm.map.worldwind.gl.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MtlLoader {

    String basePath;
    public List<Material> materials = new ArrayList<Material>();

    public MtlLoader(String basePath, String mtlPath) {
        this.basePath = basePath;
        File mtlFile = new File(mtlPath);
        if (!mtlFile.isAbsolute()) {
            mtlFile = new File(new File(basePath), mtlPath);
        }
        try {
            FileReader fr = new FileReader(mtlFile);
            BufferedReader brm = new BufferedReader(fr);
            loadobject(brm);
            brm.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Could not open file: " + mtlFile.toString());
            materials = null;
        }
    }

    public Material getMtl(String namepass) {
        for (int i = 0; i < materials.size(); i++) {
            Material mtl = (Material) materials.get(i);
            if (mtl.name.matches(namepass)) {
                return mtl;
            }
        }
        return null;
    }

    private float[] getValues(String str) {
        String[] data = str.split("\\s+");
        return new float[]{Float.valueOf(data[1]).floatValue(), Float.valueOf(data[2]).floatValue(), Float.valueOf(data[3]).floatValue()};
    }

    private float getValue(String str) {
        return Float.valueOf(str.split("\\s+")[1]).floatValue();
    }

    private void loadobject(BufferedReader br) {
        int linecounter = 0;
        try {

            String newline;
            boolean firstpass = true;
            Material matset = new Material();
            int mtlcounter = 0;

            while (((newline = br.readLine()) != null)) {
                linecounter++;
                newline = newline.trim();
                if (newline.length() > 0) {
                    if (newline.startsWith("newmtl")) {
                        if (firstpass) {
                            firstpass = false;
                        } else {
                            materials.add(matset);
                            matset = new Material();
                        }
                        String[] coordstext = newline.split("\\s+");
                        matset.name = coordstext[1];
                        matset.mtlnum = mtlcounter;
                        mtlcounter++;
                    }
                    if (newline.startsWith("Ka")) {
                        matset.Ka = getValues(newline);
                    }
                    if (newline.startsWith("Kd")) {
                        matset.Kd = getValues(newline);
                    }
                    if (newline.startsWith("Ks")) {
                        matset.Ks = getValues(newline);
                    }
                    if (newline.startsWith("d")) {
                        matset.d = getValue(newline);
                    }
                    if (newline.startsWith("Ns")) {
                        matset.Ns = getValue(newline);
                    }
                    if (newline.startsWith("map_Kd")) { //texture image
                        String map_Kd = newline.trim().substring(newline.indexOf(" ")).trim();
                        if (!new File(map_Kd).isAbsolute()) {
                            map_Kd = basePath + "/" + map_Kd;
                        }
                        File file = new File(map_Kd);
                        if (file.exists() && file.canRead()) {
                            matset.map_Kd = file;
                        } else {
                            System.err.println("Error: unable to read texture " + file.toString());
                        }
                    }
                }
            }
            materials.add(matset);

        } catch (IOException e) {
            System.out.println("Failed to read file: " + br.toString());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Malformed MTL (on line " + linecounter + "): " + br.toString() + "\r \r" + e.getMessage());
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Malformed MTL (on line " + linecounter + "): " + br.toString() + "\r \r" + e.getMessage());
        }
    }

    public class Material {

        public String name;
        public int mtlnum;
        public float Ns, Ni, Tr, illum;
        public float[] Tf = new float[3];
        public float d = 1f;
        public float[] Ka = new float[3];
        public float[] Kd = new float[3];
        public float[] Ks = new float[3];
        public float[] Ke = new float[3];
        public File map_Kd = null;
    }
}
