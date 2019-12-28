import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
final class Lighting implements Animatable
{
   
    private double time;
    private Tile tile;
	
    public Lighting(double time)
    {
        this.time = time;
        
	}
	
	public int getAnimationPeriod(){
		return (int)100;
	}

    public Action createAnimationAction(int repeatCount){
		return new AnimationAction(this, 1);
	}

    public void setTime(double time)
    {
        this.time = time;
    }

	public void nextImage (){
		
		this.tile = AnimationAction.makeTile(getColor());
	}

    public java.awt.Color getColor()
    {
        double alphad = (1.0 - (1.0 * Math.cos((2 * Math.PI * (time / 30000)))));
        alphad = (alphad / 10);
        float alpha = (float) alphad;
        float r = 1.0f;
        float g = 1.0f;
        float b = 0.2f;
        java.awt.Color color = new java.awt.Color(r, g, b, alpha);
        return color;
    }
    

}




