package com.benstone.Utils;

import com.badlogic.gdx.utils.StringBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Ben on 1/29/2016.
 */
public class FileUtils {

    ///////////////////////////////////////////////////////////////////////////
    //						    Utility Methods								 //
    ///////////////////////////////////////////////////////////////////////////

    public static String fileToString(File file) throws IOException
    {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        StringBuilder result = new StringBuilder();

        String line = br.readLine();

        while (line != null)
        {
            result.append(line + "\n");
            line = br.readLine();
        }

        fr.close();
        br.close();
        return result.toString();
    }

}
