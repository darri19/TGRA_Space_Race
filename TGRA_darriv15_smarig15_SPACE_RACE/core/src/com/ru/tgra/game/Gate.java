package com.ru.tgra.game;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

public class Gate {
	
	Point3D pos;
	float angle;
	MeshModel model;
	Texture tex;
	float size;
	
	public Gate(Point3D pos, float angle, MeshModel model, Texture tex, float size){
		this.pos = pos;
		this.angle = angle;
		this.model = model;
		this.tex = tex;
		this.size = size;
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
	
}
