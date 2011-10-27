package ThirdManda;

import java.util.Date;
import java.util.Random;

public class DriftingClock {
	private long adjustment;
	private Date startTime;
	private Random generator;	
	
    private int startdif;
    private int drift;	
	
	public DriftingClock()
	{
		startTime = new Date();
		
		generator = new Random();
		
        startdif = (generator.nextInt(1000) - 500) * 1000;
        drift = generator.nextInt(100);
	}
	
	public Date getTime()
	{
		Date currentTime = new Date();
		
		long passedTime = currentTime.getTime() - startTime.getTime();
		
		long driftedTime = Math.round(passedTime * ((drift - 50)/1000));
		
		return new Date(currentTime.getTime() + driftedTime + startdif + adjustment);	
	}
	
	public void Adjust(int i)
	{
		adjustment += i;
	}
}
