package logic;


import java.io.*;
import java.nio.file.Files;

public class FileManager {

    public final String PATH = "save";
    public FileManager()
    {
        File folder = new File(PATH);
        if(!folder.exists()) {
            folder.mkdir();
        }
    }
    public void save(String test,String code,String exerciseName)
    {
        BufferedWriter writer = null, writer2 = null;

        try{writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(PATH +"/"+ exerciseName +"_test.txt"), "UTF-8"));
            writer2 = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(PATH +"/"+ exerciseName + "_program.txt"), "UTF-8"));
            writer.write(test);
            writer2.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }   finally {
            try {
                writer.close();
                writer2.close();
            } catch (Exception e){}}
    }

}
