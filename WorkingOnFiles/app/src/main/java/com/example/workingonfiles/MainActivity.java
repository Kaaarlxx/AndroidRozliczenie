package com.example.workingonfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        streamwrite("Random text");
        streamread();
        overwritebyraf("Ala ma");
        overwritebyraf("Ala");
        writebyraf("ma kota");
        readbyraf();
    }
    private void streamwrite(String str) {
        String filename = "test.txt";
        System.out.println(this.getFilesDir());
        File file = new File(this.getFilesDir(), filename);
        try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
                fos.write(str.getBytes(StandardCharsets.UTF_8));
                fos.write("\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void streamread(){
        FileInputStream fis = null;
        try {
            fis = this.openFileInput("test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line = br.readLine()) != null)
            {
                text.append(line);
                text.append('\n');
            }
            br.close();
            TextView tv = findViewById(R.id.textfromfile);
            tv.setText(text);
        }
        catch (IOException e)
        { }

    }

    private void overwritebyraf(String str){
        try {
            File f = new File(getFilesDir(), "test2.txt");
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.setLength(0);
            raf.seek(0);
            raf.writeUTF(str + "\n" );
            raf.close(); } catch (IOException ex) { ex.printStackTrace(); }

    }
    private void writebyraf(String str){
        try {
            File f = new File(getFilesDir(), "test2.txt");
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.seek(raf.length());
            raf.writeUTF(str);
        } catch (IOException ex) { ex.printStackTrace(); }
    }


    private void readbyraf(){
        try {
            File f = new File(getFilesDir(), "test2.txt");
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            TextView tv = findViewById(R.id.textfromfile2);
            tv.setText(raf.readUTF());
            raf.close(); } catch (IOException ex) { ex.printStackTrace(); }

    }
}
