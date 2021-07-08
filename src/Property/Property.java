package Property;

import java.io.*;
import java.util.Properties;

public class Property {

    public  void Default(){
        Properties userProp=new Properties();

        userProp.setProperty("theme","dark");
        userProp.setProperty("xpos","125.25");
        userProp.setProperty("ypos","50");
        userProp.setProperty("width","255");
        userProp.setProperty("height","500");

        File file = new File("Default.properties");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);) {
            userProp.store(bufferedOutputStream,"Prperties");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
