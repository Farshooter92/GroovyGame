import com.badlogic.gdx.scenes.scene2d.Actor

// All of this is inside of a Run method

// Properties
Actor actor = (Actor)getProperty("Actor");

if (actor != null)
{
    actor.setRotation((float)(actor.getRotation() + 45))
    println "rotate"
}