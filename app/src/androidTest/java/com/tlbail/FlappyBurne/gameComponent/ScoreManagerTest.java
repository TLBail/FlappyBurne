package com.tlbail.FlappyBurne.gameComponent;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.tlbail.FlappyBurne.User.LocalDataLoader.UserPropertyFileLoader;
import com.tlbail.FlappyBurne.User.User;
import com.tlbail.FlappyBurne.User.UserPropertyLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScoreManagerTest {

    private Context context;

    @Before
    public void init(){
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        UserPropertyLoader userPropertyFileLoader = new UserPropertyFileLoader(context);
        User user = new User("bob",userPropertyFileLoader);

        user.setProperty("minecraft", "enorme");

    }


    @Test
    public void testLoadingUserScore(){
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        UserPropertyLoader userPropertyFileLoader = new UserPropertyFileLoader(context);
        User user = new User("bob",userPropertyFileLoader);

        System.out.println("user.get(\"minecraft\") = " + user.get("minecraft"));
        Assert.assertEquals("enorme", user.get("minecraft") );

    }
    
    @Test
    public void displayAllproperty(){

        

    }
}