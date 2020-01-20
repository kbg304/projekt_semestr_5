package com.example.projekt_semestr_5;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileManager
{
    Context ctx;

    public FileManager(Context ctx)
    {
        this.ctx = ctx;
    }

    public void save(String filename, String data)
    {
        try {
            FileOutputStream fileOutputStream = ctx.openFileOutput(filename, ctx.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            File file = new File(ctx.getFilesDir(), filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String open(String filename)
    {
        String temp = "";
        try {
            FileInputStream fileInputStream = ctx.openFileInput(filename);
            int c;


            while((c = fileInputStream.read()) != -1)
            {
                temp += Character.toString((char)c);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

}
