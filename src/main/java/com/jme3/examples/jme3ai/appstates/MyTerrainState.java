package com.jme3.examples.jme3ai.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HeightfieldCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.*;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;


public class MyTerrainState extends BaseAppState{
    private SimpleApplication app;
    private Node loadedMap = new Node();
    private RigidBodyControl landscape;

    @Override
    protected void initialize(Application app) {
        this.app = (SimpleApplication) app;
        initFloor();
        initLight();
    }

    @Override
    protected void cleanup(Application app) {
        loadedMap.removeControl(RigidBodyControl.class);

    }

    //onEnable()/onDisable() can be used for managing things that should
    //only exist while the state is enabled. Prime examples would be scene
    //graph attachment or input listener attachment.
    @Override
    protected void onEnable() {
        getPhysicsSpace().add(landscape);
        app.getRootNode().attachChild(loadedMap);

    }

    @Override
    protected void onDisable() {
        getPhysicsSpace().remove(landscape);
        app.getRootNode().detachChild(loadedMap);

    }

    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }

    private void initFloor() {
        final Node loadModel = (Node) getApplication().getAssetManager().loadModel("Scenes/testScene4.j3o");
        final TerrainQuad terrain = (com.jme3.terrain.geomipmap.TerrainQuad) loadModel.getChild("terrain-testScene4");
        terrain.removeFromParent();
        loadedMap.attachChild(terrain);

        loadModel.getChildren().forEach(child->{
            loadedMap.attachChild(child);
        });

        CollisionShape sceneShape = new HeightfieldCollisionShape(terrain.getHeightMap(), new Vector3f(1, 1, 1));
        landscape = new RigidBodyControl(sceneShape, 0);
        loadedMap.addControl(landscape);

    }

    private void initLight() {
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        app.getRootNode().addLight(sun);

        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        app.getRootNode().addLight(ambient);
    }

    /**
     * @return the PhysicsSpace
     */
    private PhysicsSpace getPhysicsSpace() {
        return getState(BulletAppState.class).getPhysicsSpace();
    }
}
