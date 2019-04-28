package com.jumper.main.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jumper.main.Game.Clouds;

public interface State {

    void update(float dt);
    void render(SpriteBatch sb);
    void dispose();
}
