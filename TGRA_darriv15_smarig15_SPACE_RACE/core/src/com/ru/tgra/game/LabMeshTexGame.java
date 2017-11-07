package com.ru.tgra.game;


import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.motion.BSplineMotion;
import com.ru.tgra.graphics.motion.BezierMotion;
import com.ru.tgra.graphics.motion.LinearMotion;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

public class LabMeshTexGame extends ApplicationAdapter implements InputProcessor {

	private final Color COLOR_GREEN = new Color(0.0f, 0.5f, 0.0f);
	private final Color COLOR_YELLOW = new Color(0.5f, 0.45f, 0.0f);
	
	Shader shader;


	private Camera cam;
	private Camera cam2;
	
	private float fov = 90.0f;

	float currentTime;
	boolean firstFrame = true;
	

	MeshModel ship;
	MeshModel ship2;
	MeshModel ring;
	
	Texture tex;

	Player player1;
	Player player2;
	
	ArrayList<Gate> gates;
	
	
	BezierMotion motion;

	private Texture skyTex;
	private Texture phobTex;
	
	Random rand = new Random();

	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		skyTex = new Texture(Gdx.files.internal("textures/space3.jpg"));

		//sky = G3DJModelLoader.loadG3DJFromFile("space_less_detailed.g3dj", true);
		ship = G3DJModelLoader.loadG3DJFromFile("ship_proto.g3dj", true);
		ship2 = G3DJModelLoader.loadG3DJFromFile("ship_proto2.g3dj", true);
		ring = G3DJModelLoader.loadG3DJFromFile("gateUpdate.g3dj", true);

		tex = new Texture(Gdx.files.internal("textures/tex01.png"));
		phobTex = new Texture(Gdx.files.internal("textures/phobos2k.png"));
//		
//		ArrayList<Point3D> points = new ArrayList<Point3D>();
//		
//		points.add(new Point3D(0.0f, 0.0f, 0.0f));
//		points.add(new Point3D(10.0f, 0.0f, 10.0f));
//		points.add(new Point3D(20.0f, 0.0f, 20.0f));
//		points.add(new Point3D(30.0f, 0.0f, 30.0f));
//		points.add(new Point3D(40.0f, 0.0f, 40.0f));
//		points.add(new Point3D(50.0f, 0.0f, 50.0f));
//		points.add(new Point3D(60.0f, 0.0f, 60.0f));
//		points.add(new Point3D(70.0f, 0.0f, 70.0f));
//		points.add(new Point3D(80.0f, 0.0f, 80.0f));
//		points.add(new Point3D(90.0f, 0.0f, 90.0f));
//		points.add(new Point3D(100.0f, 0.0f, 100.0f));
//		points.add(new Point3D(110.0f, 0.0f, 110.0f));
//		points.add(new Point3D(120.0f, 0.0f, 120.0f));
//		points.add(new Point3D(130.0f, 0.0f, 130.0f));
//		points.add(new Point3D(140.0f, 0.0f, 140.0f));
//		points.add(new Point3D(150.0f, 0.0f, 150.0f));
//		
//		motion = new BSplineMotion(points, 0, 10);
		
		ArrayList<BezierMotion> motions = new ArrayList<BezierMotion>();
		BezierMotion motion1 = new BezierMotion(new Point3D(-100,0,0), new Point3D(-100,0,-100),
				new Point3D(-100,0,-200), new Point3D(-100,0,-300),
				0.0f, 10.0f);
		motions.add(motion1);
		
		BezierMotion motion2 = new BezierMotion(new Point3D(-100,0,-350), new Point3D(-100,0,-450),
				new Point3D(0,0,-550), new Point3D(100,0,-550),
				0.0f, 10.0f);
		motions.add(motion2);
		
		BezierMotion motion3 = new BezierMotion(new Point3D(100,0,-550), new Point3D(250,0,-550),
				new Point3D(400,0,-550), new Point3D(600,0,-550),
				0.0f, 10.0f);
		motions.add(motion3);
		
		BezierMotion motion4 = new BezierMotion(new Point3D(700,0,-550), new Point3D(900,0,-550),
				new Point3D(900,0,-350), new Point3D(700,0,-350),
				0.0f, 10.0f);
		motions.add(motion4);
		
		BezierMotion motion5 = new BezierMotion(new Point3D(600,0,-350), new Point3D(500,0,-350),
				new Point3D(400,0,-350), new Point3D(300,0,-350),
				0.0f, 10.0f);
		motions.add(motion5);
		
		BezierMotion motion6 = new BezierMotion(new Point3D(200,0,-350), new Point3D(50,0,-350),
				new Point3D(50,0,-150), new Point3D(200,0,-150),
				0.0f, 10.0f);
		motions.add(motion6);
		
		BezierMotion motion7 = new BezierMotion(new Point3D(400,0,-150), new Point3D(500,0,-150),
				new Point3D(600,0,-100), new Point3D(600,0,50),
				0.0f, 10.0f);
		motions.add(motion7);
		
		BezierMotion motion8 = new BezierMotion(new Point3D(600,0,150), new Point3D(600,0,250),
				new Point3D(600,0,350), new Point3D(600,0,450),
				0.0f, 10.0f);
		motions.add(motion8);
		
		BezierMotion motion9 = new BezierMotion(new Point3D(600,0,550), new Point3D(600,0,650),
				new Point3D(500,0,700), new Point3D(450,0,700),
				0.0f, 10.0f);
		motions.add(motion9);
		
		BezierMotion motion10 = new BezierMotion(new Point3D(400,0,700), new Point3D(300,0,700),
				new Point3D(200,0,700), new Point3D(100,0,700),
				0.0f, 10.0f);
		motions.add(motion10);
		
		BezierMotion motion11 = new BezierMotion(new Point3D(0,0,700), new Point3D(-100,0,700),
				new Point3D(-100,0,700), new Point3D(-100,0,500),
				0.0f, 10.0f);
		motions.add(motion11);
		
		BezierMotion motion12 = new BezierMotion(new Point3D(-100,0,400), new Point3D(-100,0,300),
				new Point3D(-100,0,200), new Point3D(-100,0,100),
				0.0f, 10.0f);
		motions.add(motion12);
		
		

		gates = new ArrayList<Gate>();
		for(int i = 0; i < motions.size(); i++){
			for(int j = 0; j < 10; j++){
				Point3D pos = new Point3D();
				motions.get(i).getCurrentPos(j*(10.0f/10.0f), pos);
				float angle = motions.get(i).getCurrentAngle(j*(10.0f/10.0f));
				gates.add(new Gate(pos, angle, ring, null, 5, new Color(0.5f,0.5f,0),i*10 + j));
			}
		}
		

		player1 = new Player(new Point3D(-95.0f,1.0f,50.0f), new Vector3D(0.0f,0.0f,-1.0f), ship, null);
		player2 = new Player(new Point3D(-105.0f,1.0f,50.0f), new Vector3D(0.0f,0.0f,-1.0f), ship2, null);
		
		
		BoxGraphic.create();
		SphereGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		cam = new Camera();
		cam.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));
		cam2 = new Camera();
		cam2.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		long soundId = SoundManager.music.play();
		SoundManager.music.setLooping(soundId, true);
		SoundManager.music.setVolume(soundId, 0.15f);
	}

	private void input()
	{
	}
	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player1.rotate(-80 * deltaTime);
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player1.rotate(80 * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			player1.accelerateForward();
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			player1.accelerateBack();
		}else{
			player1.decelerate();
		}
		

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player2.rotate(-80 * deltaTime);
		}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player2.rotate(80 * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player2.accelerateForward();
		}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player2.accelerateBack();
		}else{
			player2.decelerate();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.T)) {
			fov -= 30.0f * deltaTime;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.G)) {
			fov += 30.0f * deltaTime;
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			Gdx.graphics.setDisplayMode(500, 500, false);
			Gdx.app.exit();
		}

		//do all updates to the game
		player1.update();
		player2.update();
		
		for(Gate g : gates){
			g.traversing(player1);
			g.traversing(player2);
		}
		
		player1.checkCollisions(player2);
		
		cam.look(new Point3D(player1.getPos().x - 6*player1.getDir().x, player1.getPos().y+5,player1.getPos().z-6*player1.getDir().z) , new Point3D(player1.getPos().x + 6*player1.getDir().x, player1.getPos().y,player1.getPos().z+ 6*player1.getDir().z), new Vector3D(0,1,0));
		cam2.look(new Point3D(player2.getPos().x - 6*player2.getDir().x, player2.getPos().y+5,player2.getPos().z-6*player2.getDir().z) , new Point3D(player2.getPos().x + 6*player2.getDir().x, player1.getPos().y,player2.getPos().z+ 6*player2.getDir().z), new Vector3D(0,1,0));

	}
	
	private void display()
	{
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		// Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

		//Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

		for(int viewNum = 0; viewNum < 2; viewNum++)
		{
			if(viewNum == 0)
			{

				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				cam.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.2f, 1000.0f);
				shader.setViewMatrix(cam.getViewMatrix());
				shader.setProjectionMatrix(cam.getProjectionMatrix());
				shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
				
				displayForPlayer(player1);
			}
			else
			{
				Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				cam2.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.2f, 1000);
				shader.setViewMatrix(cam2.getViewMatrix());
				shader.setProjectionMatrix(cam2.getProjectionMatrix());
				shader.setEyePosition(cam2.eye.x, cam2.eye.y, cam2.eye.z, 1.0f);
				
				displayForPlayer(player2);
			}


		}
	}
	
	private void displayForPlayer(Player player){


		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
		drawSkybox(player);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		

		ModelMatrix.main.loadIdentityMatrix();

		setLights();

		drawPlayer(player1);
		drawPlayer(player2);

		shader.setMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
		shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
		shader.setMaterialEmission(0, 0, 0, 1);
		shader.setShininess(50.0f);
		
		
		for(Gate g : gates){
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(g.getPos().x, g.getPos().y, g.getPos().z);
			ModelMatrix.main.addRotationY((float)(180.0f*g.getAngle()/Math.PI));
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			if(player.getNextGate() <= g.numberOfGate){
				g.color = COLOR_YELLOW;
			}else{
				g.color = COLOR_GREEN;
			}
			g.getModel().drawWithColor(shader, g.getTex(),g.size, g.color);
			ModelMatrix.main.popMatrix();
		}
	}

	private void setLights() {
		
		shader.setSpotDirection(-cam.n.x, -cam.n.y, -cam.n.z, 0.0f);
		shader.setSpotExponent(0.0f);
		shader.setConstantAttenuation(1.0f);
		shader.setLinearAttenuation(0.00f);
		shader.setQuadraticAttenuation(0.00f);

		shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		shader.setGlobalAmbient(1.0f, 1.0f, 1.0f, 1);
		
	}

	private void drawPlayer(Player player) {
		
		shader.setMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
		shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
		shader.setMaterialEmission(0, 0, 0, 1);
		shader.setShininess(50.0f);

		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(player.getPos().x, player.getPos().y, player.getPos().z);
		ModelMatrix.main.addRotationY(180+player.getAngle());
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		player.getModel().draw(shader, player.getTex(),1);
		ModelMatrix.main.popMatrix();
		
	}

	@Override
	public void render () {
		
		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}
	
	private void drawSkybox(Player player){
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(player.getPos().x, player.getPos().y, player.getPos().z);
		ModelMatrix.main.addScale(10,10,10);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere(shader, skyTex);
	}

	private void drawPyramids()
	{
		int maxLevel = 9;

		for(int pyramidNr = 0; pyramidNr < 2; pyramidNr++)
		{
			ModelMatrix.main.pushMatrix();
			if(pyramidNr == 0)
			{
				shader.setMaterialDiffuse(0.8f, 0.8f, 0.2f, 0.8f);
				shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
				shader.setShininess(150.0f);
				shader.setMaterialEmission(0, 0, 0, 1);
				ModelMatrix.main.addTranslation(0.0f, 0.0f, -7.0f);
			}
			else
			{
				shader.setMaterialDiffuse(0.5f, 0.3f, 1.0f, 0.3f);
				shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
				shader.setShininess(150.0f);
				shader.setMaterialEmission(0, 0, 0, 1);
				ModelMatrix.main.addTranslation(0.0f, 0.0f, 7.0f);
			}
			ModelMatrix.main.pushMatrix();
			for(int level = 0; level < maxLevel; level++)
			{
	
				ModelMatrix.main.addTranslation(0.55f, 1.0f, -0.55f);
	
				ModelMatrix.main.pushMatrix();
				for(int i = 0; i < maxLevel-level; i++)
				{
					ModelMatrix.main.addTranslation(1.1f, 0, 0);
					ModelMatrix.main.pushMatrix();
					for(int j = 0; j < maxLevel-level; j++)
					{
						ModelMatrix.main.addTranslation(0, 0, -1.1f);
						ModelMatrix.main.pushMatrix();
						if(i % 2 == 0)
						{
							ModelMatrix.main.addScale(0.2f, 1, 1);
						}
						else
						{
							ModelMatrix.main.addScale(1, 1, 0.2f);
						}
						shader.setModelMatrix(ModelMatrix.main.getMatrix());

						BoxGraphic.drawSolidCube(shader, null);
						//BoxGraphic.drawSolidCube(shader, tex);
						ModelMatrix.main.popMatrix();
					}
					ModelMatrix.main.popMatrix();
				}
				ModelMatrix.main.popMatrix();
			}
			ModelMatrix.main.popMatrix();
			ModelMatrix.main.popMatrix();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}