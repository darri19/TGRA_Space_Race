package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Vector3D;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

public class Player {
	
	private float speed;
	private float topSpeed = 30;
	private float bottomSpeed = -5;
	private Vector3D direction;
	private Point3D position;
	private float angle;
	
	private MeshModel model;
	private Texture tex;
	
	public Player(Point3D pos, Vector3D dir, MeshModel model, Texture tex){
		this.position = pos;
		this.direction = dir;
		this.direction.normalize();
		this.model = model;
		this.tex = tex;
		this.speed = 0;
		this.angle = 0;
	}
	
	public void update(){
		float deltaSpeed = speed*Gdx.graphics.getDeltaTime();
		this.position.x += deltaSpeed * this.direction.x;
		this.position.y += deltaSpeed * this.direction.y;
		this.position.z += deltaSpeed * this.direction.z;
	}

	public Point3D getPos(){
		return position;
	}

	public Vector3D getDir(){
		return direction;
	}
	
	public float getAngle(){
		return angle;
	}

	public MeshModel getModel(){
		return model;
	}

	public Texture getTex(){
		return tex;
	}

	public void accelerateForward(){
		if(speed <= topSpeed)
			speed += 0.5f;
	}

	public void accelerateBack(){
		if(speed >= bottomSpeed)
			speed -= 0.5f;
	}
	
	public void decelerate(){
		if((speed < 0.2 && speed > 0) || (speed > -0.2 && speed < 0)){
			speed = 0;
		}else if(speed > 0){
			speed -= 0.1;
		}else if(speed < 0){
			speed += 0.1;
		}
	}
	
	public void rotate(float angle){

		this.angle -= angle;
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = -(float)Math.sin(radians);
		float xTemp = this.direction.x;

		this.direction.x = xTemp * c + this.direction.z * s;
		this.direction.z = -xTemp * s + this.direction.z * c;
		
		this.direction.normalize();
//
//		u.set(t.x * c - n.x * s, t.y * c - n.y * s, t.z * c - n.z * s);
//		n.set(t.x * s + n.x * c, t.y * s + n.y * c, t.z * s + n.z * c);
	}
}
