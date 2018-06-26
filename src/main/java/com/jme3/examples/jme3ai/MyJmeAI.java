package com.jme3.examples.jme3ai;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.examples.jme3ai.ai.NavMeshState;
import com.jme3.examples.jme3ai.appstates.KeyboardRunState;
import com.jme3.examples.jme3ai.appstates.MyTerrainState;
import com.jme3.examples.jme3ai.appstates.PCState;


public class MyJmeAI extends SimpleApplication{


    public MyJmeAI(){
        super(new StatsAppState(), new DebugKeysAppState(), new MyTerrainState(), new KeyboardRunState());

        NavMeshState state = new NavMeshState();
        getStateManager().attach(state);
        getStateManager().attach(new PCState(state));
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new BulletAppState());
    }


    public static void main(String[] args){
        MyJmeAI app = new MyJmeAI();
        app.start();
    }
}
