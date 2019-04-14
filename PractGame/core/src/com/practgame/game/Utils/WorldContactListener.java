package com.practgame.game.Utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.practgame.game.Scenes.WindowManager;
import com.practgame.game.Screens.MenuLevel;
import com.practgame.game.Screens.PlayScreen;
import com.practgame.game.Sprites.ActionBrick;
import com.practgame.game.Sprites.BlockTileObject;
import com.practgame.game.Sprites.Bullet;

import java.util.logging.Logger;


public class WorldContactListener implements ContactListener {
    private final static Logger LOGGER = Logger.getLogger(WorldContactListener.class.getName());
    private WindowManager windowManager;
    private boolean messageShown;
    private World world;
    private PlayScreen playScreen;

        public WorldContactListener(WindowManager wm, World world){
            windowManager = wm;
            this.world = world;
        }

    public WorldContactListener(WindowManager wm, World world,PlayScreen playScreen ){
        windowManager = wm;
        this.world = world;
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        LOGGER.info("Contact began");
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

       //  TODO destroying bullet after hitting an object , first version 04/14

        LOGGER.info("On beginContact a : " + contact.getFixtureA().getUserData() + "  b : " + contact.getFixtureB().getUserData());

        if (contact.getFixtureB().getUserData() instanceof Bullet && contact.getFixtureA().getUserData() != "next_level") {
            LOGGER.info("destroy method ran, destroy on " + contact.getFixtureB().getUserData());
            playScreen.destroy((Bullet) contact.getFixtureB().getUserData());
        }

        if(("feet").equals(fixA.getUserData()) || ("feet").equals(fixB.getUserData())){
            windowManager.onGround = true;
            LOGGER.info("Player is on Ground");
        }



        if(("player").equals(fixA.getUserData()) || ("player").equals(fixB.getUserData())){
            Fixture player = ("player").equals(fixA.getUserData()) ? fixA : fixB;
            Fixture object = fixA == player ? fixB : fixA;

            LOGGER.info("player : "  + player.getUserData());
            LOGGER.info("object : "  + object.getUserData());


            if(("lobby").equals(object.getUserData())){
                windowManager.showMessage("lobby");
                messageShown = true;
            }

            if(("lift").equals(object.getUserData())){
                windowManager.showMessage("lift");
                windowManager.waitingForAnwser = "lift";
                messageShown = true;
            }

            if(("next_level".equals(object.getUserData()))){
                windowManager.showMessage("next_level");
                windowManager.waitingForAnwser = "next_level";
                messageShown = true;
            }
        }
    }


    @Override
    public void endContact(Contact contact){
            if(messageShown) {
                messageShown = false;
                windowManager.hideMessage();
            }
            windowManager.waitingForAnwser = "none";
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
