package com.ru.tgra.game;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Color;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

public class Gate {
	
	Point3D pos;
	float angle;
	MeshModel model;
	Texture tex;
	float size;
	
	Color color;
	int numberOfGate;
	int timesTraversed = 0;
	
	
	public Gate(Point3D pos, float angle, MeshModel model, Texture tex, float size, Color color, int numOfGate){
		this.pos = pos;
		this.angle = angle;
		this.model = model;
		this.tex = tex;
		this.size = size;
		this.color = color;
		this.numberOfGate = numOfGate;
	}
	
	public Point3D getPos(){
		return pos;
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
	
	public boolean traversing(Player player){
		if(player.getNextGate() == this.numberOfGate && player.getPos().x < pos.x+1.5*size && player.getPos().x > pos.x-1.5*size &&
				player.getPos().z < pos.z+1.5*size && player.getPos().z > pos.z-1.5*size){
			player.setNextGate(player.getNextGate()+1);
			return true;
		}
		else{
			return false;
		}
	}
	
}
