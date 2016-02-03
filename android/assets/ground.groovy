import com.benstone.Actors.B2DActor
import com.benstone.Utils.Constants

// This is a simple script for some ground behavior

// Properties
// Want to get the ground actor
B2DActor ground = (B2DActor)getProperty(Constants.GROUND_ID)

if (ground != null)
{
    float previousPosX = ground.getBody().getTransform().getPosition().x
    float previousPosY =  ground.getBody().getTransform().getPosition().y

    // Move the actor down 1 meter
    ground.getBody().setTransform(previousPosX, (float)(previousPosY - 1), 0f)
}