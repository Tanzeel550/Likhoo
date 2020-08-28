package sample;

import java.io.*;

public class Model {
    private String text;

    private final static Model instance = new Model();

    private Model(){};

    public static Model getInstance() {
        return instance;
    }

    public void saveData(File file, String fileData) throws Exception {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(fileData);
        }
    }

    public String openData(File file) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder fileData = new StringBuilder();
        String currLine;

        while ((currLine = bufferedReader.readLine())!=null){
            fileData.append(currLine + "\n");
        }
        return fileData.toString();
    }

}
