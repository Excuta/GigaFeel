package com.udacity.gamedev.gigagal.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
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
            followTarget(delta);
        } else {
            freeCamInput(delta);
        }
    }

    private void followTarget(float delta) {
        if (target.getFacing().equals(Enums.Direction.RIGHT)) {
            camera.position.x = target.getPosition().x + Constants.CHASE_CAM_DIRECTION_OFFSET;
            lastFrameDirection = Enums.Direction.RIGHT;
        } else {
            camera.position.x = target.getPosition().x - Constants.CHASE_CAM_DIRECTION_OFFSET;
            lastFrameDirection = Enums.Direction.LEFT;
        }
        camera.position.y = target.getPosition().y;
        camera.position.z = 0;
        if (target.isShooting) {
            shake(delta);
        }
    }

    private void shake(float delta) {
        Vector3 shake = new Vector3();
        boolean shouldXShake = MathUtils.randomBoolean();
        if (shouldXShake) {
            if (MathUtils.randomBoolean()) {
                shake.x = Constants.CHASE_CAM_SHAKE * -1;
            } else {
                shake.x = Constants.CHASE_CAM_SHAKE;
            }
        }
        boolean shouldYShake = MathUtils.randomBoolean();
        if (shouldYShake) {
            if (MathUtils.randomBoolean()) {
                shake.y = Constants.CHASE_CAM_SHAKE * -1;
            } else {
                shake.y = Constants.CHASE_CAM_SHAKE;
            }
        }
        camera.position.mulAdd(shake, delta);
    }

    private void freeCamInput(float delta) {
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
