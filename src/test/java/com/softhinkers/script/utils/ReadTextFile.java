package com.softhinkers.script.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadTextFile {

    private List<String> listUrlString = null;

    public ReadTextFile(String filePath) {
        listUrlString = readTextFile(filePath);
    }

    private List<String> readTextFile(String filePath) {
        try {
            File f = new File(filePath);
            return FileUtils.readLines(f, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getListUrlString() {
        return listUrlString;
    }


}
