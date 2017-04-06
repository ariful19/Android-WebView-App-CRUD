package com.example.brainsoft.sqlitetest;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by mahmud on 4/4/2017.
 */

public class JavaScriptInterface {
    public static ArrayList<Person> list = new ArrayList<>();
    private String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/mydb";
    private Context ctx;

    public JavaScriptInterface(Context ctx) {
        this.ctx = ctx;
    }

    @JavascriptInterface
    public void Msg(String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

    }

    @JavascriptInterface
    public void Add(final String name, final int age) {
        //path= android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/mydb.odb";
        Person p = new Person(name, age);
        list.add(p);
        Person maxp = Collections.max(list, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getId() == o2.getId() ? 0 :
                        o1.getId() > o2.getId() ? 1 : -1;
            }
        });
        p.setId(maxp.id + 1);

        SaveList();
    }


    @JavascriptInterface
    public void Save(int id, String name, int age) {
       int res = Collections.binarySearch(list, new Person(id, name, age), new Comparator<Person>
                () {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getId()>o2.getId())
                    return 1;
                else if (o1.getId()<o2.getId())
                    return -1;
                return 0;
            }
        });
        Person p=list.get(res);
        p.setName(name);
        p.setAge(age);
        SaveList();
    }

    private void SaveList() {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @JavascriptInterface
    public void Remove(int i){
        int res = Collections.binarySearch(list, new Person(i, "", 0), new Comparator<Person>
                () {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getId()>o2.getId())
                    return 1;
                else if (o1.getId()<o2.getId())
                    return -1;
                return 0;
            }
        });
        list.remove(res);
        SaveList();
    }

    @JavascriptInterface
    public String GetAll() {
        // path= android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/mydb.odb";
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            list = (ArrayList<Person>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            new Gson().toJson(list);
        }catch(ClassNotFoundException c) {
            c.printStackTrace();
            new Gson().toJson(list);
        }
        String res=new Gson().toJson(list);
        return res;
    }
}
