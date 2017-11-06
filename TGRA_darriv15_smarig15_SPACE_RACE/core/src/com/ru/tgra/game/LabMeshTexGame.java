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
import com.ru.tgra.graphics.motion.BezierMotion;
import com.ru.tgra.graphics.motion.LinearMotion;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

public class LabMeshTexGame extends ApplicationAdapter implements InputProcessor {

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
		
		motion = new BezierMotion(new Point3D(-100,0,-100), new Point3D(-100,0,100),
								new Point3D(100,0,100), new Point3D(100,0,-100),
								3.0f, 7.0f);
		
		gates = new ArrayList<Gate>();
		for(int i = 0; i < 50; i++){
			Point3D pos = new Point3D();
			motion.getCurrentPos(3.0f+i*(4.0f/50.0f), pos);
			gates.add(new Gate(pos, 0, ring, null));
		}

		player1 = new Player(new Point3D(1.0f,1.0f,1.0f), new Vector3D(0.0f,0.0f,1.0f), ship, null);
		player2 = new Player(new Point3D(6.0f,1.0f,1.0f), new Vector3D(0.0f,0.0f,1.0f), ship2, null);
		
		
		BoxGraphic.create();
		SphereGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		cam = new Camera();
		cam.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));
		cam2 = new Camera();
		cam2.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));

		//topCam = new Camera();
		//orthoCam.orthographicProjection(-5, 5, -5, 5, 3.0f, 100);
		//topCam.perspectiveProjection(30.0f, 1, 3, 100);

		//TODO: try this way to create a texture image
		/*Pixmap pm = new Pixmap(128, 128, Format.RGBA8888);
		for(int i = 0; i < pm.getWidth(); i++)
		{
			for(int j = 0; j < pm.getWidth(); j++)
			{
				pm.drawPixel(i, j, rand.nextInt());
			}
		}
		tex = new Texture(pm);*/

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
//		
//		if(firstFrame){
//			currentTime = 0.0f;
//			firstFrame = false;
//		}else{
//			currentTime += Gdx.graphics.getRawDeltaTime();
//		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player1.rotate(-80 * deltaTime);
			//cam.slide(-3.0f * deltaTime, 0, 0);
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player1.rotate(80 * deltaTime);
			//cam.slide(3.0f * deltaTime, 0, 0);
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
			//cam.pitch(-90.0f * deltaTime);
		}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player2.accelerateBack();
			//cam.pitch(90.0f * deltaTime);
		}else{
			player2.decelerate();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			//cam.roll(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)) {
			//cam.roll(90.0f * deltaTime);
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
		//motion.getCurrentPos(currentTime, modelPosition);
		player1.update();
		player2.update();
		
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
				cam.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.2f, 200.0f);
				shader.setViewMatrix(cam.getViewMatrix());
				shader.setProjectionMatrix(cam.getProjectionMatrix());
				shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
				

				Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
				drawSkybox(player1);
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
				
//				float radius = 50f;
//				for(int i = 0; i < 8; i++){
//					float angle = (float) (i* Math.PI * 2.0 / 8);
//
//					ModelMatrix.main.pushMatrix();
//					ModelMatrix.main.addTranslation((float)Math.cos(angle)*radius, 0f, (float)Math.sin(angle)*radius);
//					ModelMatrix.main.addRotationY((float)(-angle*180/Math.PI));
//					shader.setModelMatrix(ModelMatrix.main.getMatrix());
//					ring.draw(shader, phobTex, 5);
//					ModelMatrix.main.popMatrix();
//				}
				
				for(Gate g : gates){
					ModelMatrix.main.pushMatrix();
					ModelMatrix.main.addTranslation(g.getPos().x, g.getPos().y, g.getPos().z);
					ModelMatrix.main.addRotationY(g.getAngle());
					shader.setModelMatrix(ModelMatrix.main.getMatrix());
					g.getModel().draw(shader, g.getTex(),5);
					ModelMatrix.main.popMatrix();
				}

				
				drawPyramids();
			}
			else
			{
				Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				cam2.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.2f, 200);
				shader.setViewMatrix(cam2.getViewMatrix());
				shader.setProjectionMatrix(cam2.getProjectionMatrix());
				shader.setEyePosition(cam2.eye.x, cam2.eye.y, cam2.eye.z, 1.0f);
				

				Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
				drawSkybox(player2);
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
				
				float radius = 50f;
				for(int i = 0; i < 8; i++){
					float angle = (float) (i* Math.PI * 2.0 / 8);

					shader.setMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
					shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
					shader.setMaterialEmission(0, 0, 0, 1);
					shader.setShininess(50.0f);
					ModelMatrix.main.pushMatrix();
					ModelMatrix.main.addTranslation((float)Math.cos(angle)*radius, 0f, (float)Math.sin(angle)*radius);
					ModelMatrix.main.addRotationY((float)(-angle*180/Math.PI));
					shader.setModelMatrix(ModelMatrix.main.getMatrix());
					ring.draw(shader, null, 5);
					ModelMatrix.main.popMatrix();
				}
				
				

				drawPyramids();
			}


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
		ModelMatrix.main.addRotationY(player.getAngle());
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