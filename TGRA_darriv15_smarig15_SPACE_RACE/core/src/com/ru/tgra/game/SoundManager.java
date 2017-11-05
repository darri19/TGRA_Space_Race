package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	public static final Sound music = Gdx.audio.newSound(Gdx.files.internal("sounds/lepper.mp3"));
	
	private SoundManager(){
		
	}
}
