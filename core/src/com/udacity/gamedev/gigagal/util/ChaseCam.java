package com.udacity.gamedev.gigagal.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.udacity.gamedev.gigagal.entities.GigaGal;

public class ChaseCam {

    public static final String TAG = ChaseCam.class.getName();

    public Camera camera;
    public GigaGal target;
    private Boolean following;

    private Enums.Direction lastFrameDirection;

    public ChaseCam() {
        following = true;
    }


    public void update(float delta) {

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            following = !following;
        }

        if (following) {
            if (target.getFacing().equals(Enums.Direction.RIGHT)) {
                camera.position.x = target.getPosition().x + Constants.CHASE_CAM_DIRECTION_OFFSET;
                lastFrameDirection = Enums.Direction.RIGHT;
            } else {
                camera.position.x = target.getPosition().x - Constants.CHASE_CAM_DIRECTION_OFFSET;
                lastFrameDirection = Enums.Direction.LEFT;
            }
            camera.position.y = target.getPosition().y;
        } else {
            if (Gdx.input.isKeyPressed(Keys.A)) {
                camera.position.x -= delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Keys.D)) {
                camera.position.x += delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Keys.W)) {
                camera.position.y += delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
            if (Gdx.input.isKeyPressed(Keys.S)) {
                camera.position.y -= delta * Constants.CHASE_CAM_MOVE_SPEED;
            }
        }
    }
}
