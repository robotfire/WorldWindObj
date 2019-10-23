/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osm.map.worldwind.gl.obj;

/**
 *
 * @author sbodmer
 */
public interface ObjLoaderProgressListener {
    public void objLoading(String file, String message);
    public void objLoadingFailed(String file, String message);
    public void objLoaded(String file, String message);
    
}
