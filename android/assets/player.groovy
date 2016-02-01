import com.benstone.Actors.Player
import com.benstone.Utils.Constants

// This is a simple script for some ground behavior

// Properties
// Want to get the ground actor
Player player = (Player)getProperty(Constants.PLAYER_ID)

if (player != null)
{
    // Make the player faster
    // player.setSideToSideSpeed(8);

    // Make the player jump higher
    // player.setJumpForce(12000);
}