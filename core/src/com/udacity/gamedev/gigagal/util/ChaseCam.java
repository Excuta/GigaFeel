package com.udacity.gamedev.gigagal.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.udacity.gamedev.gigagal.entities.GigaGal;

public class ChaseCam {

    public static final String TAG = ChaseCam.class.getName();

    public Camera camera;
    public GigaGal target;
    private Boolean following;
    private Vector2 velocity;

    public ChaseCam() {
        following = true;
        velocity = new Vector2();
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
        float xDestination;
        if (target.getFacing().equals(Enums.Direction.RIGHT)) {
            xDestination = target.getPosition().x + Constants.CHASE_CAM_DIRECTION_OFFSET;
        } else {
            xDestination = target.getPosition().x - Constants.CHASE_CAM_DIRECTION_OFFSET;
        }
        updateVelocity(xDestination);
        float changeX = velocity.x * delta;
        if (Math.abs(camera.position.x - xDestination) <= Math.abs(changeX)) {
            camera.position.x = xDestination;
        } else camera.position.x += changeX;
        camera.position.y = target.getPosition().y;
        camera.position.z = 0;
        if (target.isShooting) {
            shake(delta);
        }
    }

    private void updateVelocity(float xDestination) {
        float changeVelocity = Constants.CHASE_CAM_ACCELERATION;
        if (xDestination > camera.position.x) {
            velocity.x += changeVelocity;
            if (velocity.x > Constants.CHASE_CAM_MAX_SPEED) {
                velocity.x = Constants.CHASE_CAM_MAX_SPEED;
            }
            if (camera.position.x >= xDestination) camera.position.x = xDestination;
        } else if (xDestination < camera.position.x) {
            velocity.x -= changeVelocity;
            if (velocity.x < -1 * Constants.CHASE_CAM_MAX_SPEED) {
                velocity.x = -1 * Constants.CHASE_CAM_MAX_SPEED;
            }
            if (camera.position.x <= xDestination) camera.position.x = xDestination;
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
