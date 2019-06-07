package org.iota.qcm;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ExtensionLoader {
    public void load(String path) throws IOException {
        File file = new File(path);
        URLClassLoader cl = new URLClassLoader(
                new URL[]{file.toURI().toURL()},
                getClass().getClassLoader()
        );
        URL resource = cl.findResource("META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(resource.openStream());
        Attributes attr = manifest.getAttributes("Extension");
        Set<Object> sets = attr.keySet();




        JarFile file = new JarFile(path);
        Manifest manifest = file.getManifest();
        Attributes a = manifest.getMainAttributes();
        JSONArray j = new JSONArray(a.getValue("Attach-Entity"));
    }
}
