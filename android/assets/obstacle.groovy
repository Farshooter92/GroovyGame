import com.badlogic.gdx.math.Vector2
import com.benstone.Actors.B2DActor
import com.benstone.Utils.Constants

// This is a simple script for some ground behavior

// Properties
// Want to get the ground actor
B2DActor obstacle = (B2DActor)getProperty(Constants.OBSTACLE_ID)

if (obstacle != null)
{
    // Move the actor down 1 meter
    obstacle.getBody().setLinearVelocity(new Vector2(0f, 1f))
}