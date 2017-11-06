package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Color;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Vector3D;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

public class Player {
	
	
	private float speed;
	private float topSpeed = 1000;
	private float bottomSpeed = -5;
	private Vector3D direction;
	private Point3D position;
	private float angle;
	
	private MeshModel model;
	private Texture tex;
	private int nextGate = 0;
	
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
	}
	
	public int getNextGate(){
		return nextGate;
	}
	
	public void setNextGate(int gate){
		nextGate = gate;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void checkCollisions(Player other){
		
		if(Math.sqrt( ( other.position.x-position.x ) * ( other.position.x-position.x )  + ( other.position.z-position.z ) * ( other.position.z-position.z ) ) < 5.3f){
			float deltaSpeed1 = speed*Gdx.graphics.getDeltaTime();
			float deltaSpeed2 = other.getSpeed()*Gdx.graphics.getDeltaTime();

			float newVelX1 = (direction.x * deltaSpeed1 * (5 - 5) + (2 * 5 * other.direction.x *  deltaSpeed2)) / (5 + 5);
			float newVelZ1 = (direction.z * deltaSpeed1 * (5 - 5) + (2 * 5 * other.direction.z *  deltaSpeed2)) / (5 + 5);
			float newVelX2 = (other.direction.x *  deltaSpeed2 * (5 - 5) + (2 * 5 * direction.x * deltaSpeed1)) / (5 + 5);
			float newVelZ2 = (other.direction.z *  deltaSpeed2 * (5 - 5) + (2 * 5 * direction.z * deltaSpeed1)) / (5 + 5);
			
			position.x += newVelX1;
			position.z += newVelZ1;
			other.position.x += newVelX2;
			other.position.z += newVelZ2;
		}
		
	}
	
}
