package com.yazeen.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yazeen.game.Build.CollisionManager;
import com.yazeen.game.Scenes.Controller;
import com.yazeen.game.Build.WorldCreator;
import com.yazeen.game.Scenes.Hud;
import com.yazeen.game.Sprites.Enemy;
import com.yazeen.game.Sprites.Green;
import com.yazeen.game.Sprites.Hero;
import com.yazeen.game.TheLastHope;

/**
 * Created by Yzn on 2017-03-04.
 */

public class playScreen implements Screen {

    //Game
    private TheLastHope myGame;
    private OrthographicCamera myGameCamera;
    private Viewport myGamePort;

    //ScreenObjects
    private Controller controller;

    //Controller-On Screen For android
    private Hud hud;

    //player
    private Hero player;

    //Tiled map
    private TiledMap map;
    private TmxMapLoader maploader;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D
    private World world;

    //for debug
    private Box2DDebugRenderer b2dr;

    //Animations
    private TextureAtlas atlas;

    //CollisionManager
    CollisionManager worldCollisionManager;
    private WorldCreator creator;

    //Music
    private Music bkms;
    //Score
    public int score;

    //Spam-input check
    private float jumpAndShootTime;

    public playScreen(TheLastHope myGame) {

        //initiering
        atlas = new TextureAtlas("gameSprite.pack");//Import the TexturePackage
        this.myGame = myGame;
        //this.gameOver = new GameOver(myGame);

        this.worldCollisionManager= new CollisionManager();
        this.jumpAndShootTime= 0;
        //Camera
        myGameCamera = new OrthographicCamera();
        myGamePort = new FitViewport(TheLastHope.G_WIDTH /TheLastHope.GPPM, TheLastHope.G_HEIGHT/TheLastHope.GPPM, myGameCamera); //More : ScreenViewport & StrechViewport

        //loading the map
        maploader = new TmxMapLoader();
        map = maploader.load("war.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / TheLastHope.GPPM); //UnitScale

        myGameCamera.position.set(myGamePort.getWorldWidth()/2 ,myGamePort.getWorldHeight()/2,0);

        //Vector(): Gravity
        world = new World(new Vector2(0, TheLastHope.G_GRAVITY), true);
        b2dr = new Box2DDebugRenderer();
        b2dr.SHAPE_AWAKE.set(0,0,0,0);

        //Controller-On Screen For android
        controller = new Controller(myGame);

        //Player
        player = new Hero(this);
        //hud
        hud = new Hud(myGame.myBatch, player.getPlayer_Health());

        //Create the World
        creator = new WorldCreator(this);

        //Music
        bkms = TheLastHope.audioManager.get("audio/bkms.ogg", Music.class);
        bkms.setLooping(true);
        bkms.play();

        //Identify Collision
        world.setContactListener(worldCollisionManager);




}
    public TextureAtlas getAtlas()
    {
        return this.atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        jumpAndShootTime +=dt;
        //
        if (controller.isJumpPressed() && jumpAndShootTime >= 0.3f)
        {
            jumpAndShootTime = 0;
            player.jump();
        }

        if (controller.isRightPressed() && player.getB2dbody().getLinearVelocity().x <= TheLastHope.PLAYER_SPEED ||Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            player.getB2dbody().applyLinearImpulse(new Vector2(0.1f,0),player.getB2dbody().getWorldCenter(), true);
        }

        if (controller.isLeftPressed()&& player.getB2dbody().getLinearVelocity().x >= (-TheLastHope.PLAYER_SPEED) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            player.getB2dbody().applyLinearImpulse(new Vector2(-0.1f,0),player.getB2dbody().getWorldCenter(), true);
        }

        if (controller.isAttackPressed() && jumpAndShootTime >= 0.3f)
        {
            jumpAndShootTime = 0;
            player.fire();
        }

        //Gdx.app.log("Time: ",Float.toString(jumpAndShootTime));

    }


    //check inputs
    public void update(float dt) {

        handleInput(dt);
        world.step(1/60f,6,2);
        player.update(dt);
        for (Enemy enemy: creator.getSpikys())
            enemy.update(dt);
        for (Enemy enemy: creator.getGreens())
            enemy.update(dt);
        hud.update(dt);
        myGameCamera.position.x = player.getB2dbody().getPosition().x;
        myGameCamera.update();
        renderer.setView(myGameCamera);
        hud.updateHealth(player.getPlayer_Health());

    }

    public TiledMap getMap()
    {
        return map;
    }

    public World getWorld()
    {
        return world;
    }

    @Override
    public void render(float delta) {

        //Clear the screen
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render gameMap
        renderer.render();

        //för debug
        //b2dr.render(world,myGameCamera.combined);

        controller.draw();
        myGame.myBatch.setProjectionMatrix(myGameCamera.combined);
        myGame.myBatch.begin();
        player.draw(myGame.myBatch);
        for (Enemy enemy: creator.getSpikys())
            enemy.draw(myGame.myBatch);
        for (Enemy enemy: creator.getGreens())
            enemy.draw(myGame.myBatch);
        myGame.myBatch.end();
        myGame.myBatch.setProjectionMatrix(hud.myStage.getCamera().combined);
        hud.myStage.draw();
        if (player.isDead())
        {
            saveScore();
            myGame.setScreen(new GameOver(myGame));
        }
    }

    @Override
    public void resize(int width, int height) {
        //hantera viewporten efter skärmens storlek
        myGamePort.update(width,height);
        controller.resize(width,height);

    }

    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public void saveScore()
    {
        this.score = hud.getScore();

        FileHandle readFile = Gdx.files.internal("highscore.txt");
        int temp = Integer.parseInt(readFile.readString());
        if(this.score > temp)
        {
            FileHandle file = Gdx.files.local("highscore.txt");
            file.writeString(Integer.toString(this.score), false);
        }


        /*if (Gdx.app.getType() == Application.ApplicationType.Android) {
            File dir = new File(Path);

        }*/

        /*int temp = 0;
        this.score = hud.getTime();
        try {
            Scanner in = new Scanner(new File("highscore.txt"));
            temp = in.nextInt();
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(this.score > temp)
        {
            try
            {
                PrintWriter out  = new PrintWriter("highscore.txt");
                out.println(this.score);
                out.close();
            }
            //Om filen inte finns.
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            Gdx.app.log("Saved To File","");
        }*/

    }

}

