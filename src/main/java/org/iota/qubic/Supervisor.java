package org.iota.qubic;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IStringConverterFactory;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Supervisor {
    public static void main(String[] argv) {
        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse(argv);
        /* First, load the jars and libs */

        /* Second, load the entities */

        /* Third, load the effects */
    }

    public void run() {

    }

    static class Args {
        @Parameter
        private List<String> parameters = new ArrayList<>();

        @Parameter(names = "--jlib", description = "A java lib to load", converter = JLibConverter.class)
        private List<JLib> jlib = new ArrayList<>();
    }

    static class JLibConverter implements IStringConverter<JLib> {
        @Override
        public JLib convert(String value) {
            try {
                String[] pathAndList = value.split("\\s*:\\s*");

                File file = new File(pathAndList[0]);
                URLClassLoader child = new URLClassLoader(
                        new URL[]{file.toURI().toURL()},
                        getClass().getClassLoader()
                );

                //pathAndList[1]
                Matcher m = java.util.regex.Pattern.compile("x").matcher(pathAndList[1]);
                for(int i = 0; i < m.groupCount(); i++) {
                    Class<?> clazz = Class.forName(pathAndList[1], true, child);
                    Method method = clazz.getDeclaredMethod("");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    static class EffectConverter implements IStringConverter<Qcm.Effect> {
        @Override
        public Qcm.Effect convert(String value) {
            return null;
        }
    }
    static class EntityConverter implements IStringConverter<Qcm.Entity> {
        @Override
        public Qcm.Entity convert(String value) {
            return null;
        }
    }
    static class EnvironmentConverter implements IStringConverter<Qcm.Environment> {
        @Override
        public Qcm.Environment convert(String value) {
            return null;
        }
    }
}
