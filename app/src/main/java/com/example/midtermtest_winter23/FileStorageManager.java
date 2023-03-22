package com.example.midtermtest_winter23;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileStorageManager {


    public static void writeNewToDoToTheFile(Task task, Context c){
        String filename = "allTodos.txt";
        // Private : delete all the content and then write
        // Append : append the new content to the existing one
        // convert this task into a string then write it.
        try (FileOutputStream fos = c.openFileOutput(filename, Context.MODE_APPEND)) {
            String stringtask = task.toString();
            fos.write(stringtask.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeStringToFile(String s,Context c){
        String filename = "allTodos.txt";
         try (FileOutputStream fos = c.openFileOutput(filename, Context.MODE_APPEND)) {
            fos.write(s.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Task> getAllToDosFromTheFile(Context context){
        ArrayList<Task> allTasks = new ArrayList<>(0);
        try {
            FileInputStream fis = context.openFileInput("allTodos.txt");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String oneTask =  reader.readLine();
            while (oneTask != null) {
                allTasks.add(Task.fromString(oneTask));
                oneTask = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allTasks;

    }

    public static void deleteAllTodosFromTheFile(Context c){
        String filename = "allTodos.txt";
        try (FileOutputStream fos =
                     c.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write("".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOneTaskAt(int index,Context c){
      //  ArrayList<Task> allTasks = new ArrayList<>(0);
        try {
            FileInputStream fis = c.openFileInput("allTodos.txt");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String oneTask =  reader.readLine();
             int i = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while (oneTask != null ) {
                if (i != index){
                    stringBuilder.append(oneTask+'\n');// only other tasks
                    oneTask = reader.readLine();
                }
                else {
                    oneTask = reader.readLine();
                }
                i++;
            }
            deleteAllTodosFromTheFile(c);
            writeStringToFile(stringBuilder.toString(),c);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
