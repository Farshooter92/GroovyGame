import com.benstone.Actors.B2DActor
import com.benstone.Utils.Constants

// This is a simple script for some default behavior

// Properties
// Want to get the ground actor
B2DActor actor = (B2DActor)getProperty(Constants.GROUND_ID)

if (actor != null)
{
    float previousPosX = actor.getBody().getTransform().getPosition().x
    float previousPosY =  actor.getBody().getTransform().getPosition().y

    // Move the actor down 1 meter
    actor.getBody().setTransform(previousPosX, (float)(previousPosY - 1), 0f)
}