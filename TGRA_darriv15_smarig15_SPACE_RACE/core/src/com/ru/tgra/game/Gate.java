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
	int timesTraversed = 0;
	
	
	public Gate(Point3D pos, float angle, MeshModel model, Texture tex, float size, Color color){
		this.pos = pos;
		this.angle = angle;
		this.model = model;
		this.tex = tex;
		this.size = size;
		this.color = color;
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
	
	public boolean traversing(Point3D obj){
		if(obj.x < pos.x+size && obj.x > pos.x-size &&
		   obj.z < pos.z+size && obj.z > pos.z-size	){
			return true;
		}
		else{
			return false;
		}
	}
	
}
