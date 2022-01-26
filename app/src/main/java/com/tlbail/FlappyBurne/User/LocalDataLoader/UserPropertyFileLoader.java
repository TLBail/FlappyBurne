package com.tlbail.FlappyBurne.User.LocalDataLoader;

import android.content.Context;

import com.tlbail.FlappyBurne.User.UserProperty;
import com.tlbail.FlappyBurne.User.UserPropertyLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class UserPropertyFileLoader implements UserPropertyLoader {

    private static final String DATA_FILE_NAME = "data.xml";
    File directory;
    private Context context;


    public UserPropertyFileLoader(Context context){
        this.context = context;
        directory = context.getFilesDir();
        if(getUserProperty() == null){
            HashMap hashMap = new HashMap();
            UserProperty userProperty = new UserProperty(hashMap);
            setUserProperty(userProperty);
        }
    }


    @Override
    public UserProperty getUserProperty() {
        try {
            FileInputStream fileInputStream = context.openFileInput(DATA_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            UserProperty userProperty = (UserProperty) ois.readObject();
            fileInputStream.close();
            ois.close();
            return userProperty;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setUserProperty(UserProperty userProperty) {
        try {
            FileOutputStream fOut = context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oosOfUserProperty = new ObjectOutputStream(fOut);
            oosOfUserProperty.writeObject(userProperty);
            fOut.close();
            oosOfUserProperty.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stop(){

    }


}
