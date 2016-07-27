
import java.util.Date;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
public class Generator    //Generator class to create a log file for cpu usage for 1000 servers 
{
	private static final long MILLISECONDS_PER_DAY = 86400000L;
	private static final long CURRENT_TIME = System.currentTimeMillis();
	private static final Date START_DATE = new Date(CURRENT_TIME - MILLISECONDS_PER_DAY);
	public Generator(String datapath) throws IOException{ //Constructor which takes the datapath for creating the log file as input
		//this.datapath = datapath;
		this.generate(datapath);
	}
	private void generate(String datapath) throws IOException{ //This method creates a log file, the path for which is supplied by the user when program is executed
		int cpuid =1;   //Cpu id for each server (0 or 1)
		int count =0;   //To check if log exists
		int ip=0;       //part of ip address which changes 
		int ip1=1;      //part of ip address which changes
		int no=0;
		long endTime,startTime;    //Timestamp for start and end
		PrintWriter outputfile = new PrintWriter(datapath);
		BufferedWriter out = null;
		outputfile.println("Timestamp" +"  " +"IP" + "  " + "cpuid" + "  " + "usage");
		startTime = START_DATE.getTime() / 1000L;	
		try  
		{
		    FileWriter fstream = new FileWriter(datapath, true); //Creates a new file if doesnt exist at the given path,true tells to append data.
		    out = new BufferedWriter(fstream);
		    endTime = System.currentTimeMillis() / 1000L;
			while(startTime < endTime)
			{		
				Random rand = new Random();   //Cpu usage some random number between 0-100%
				int randomNum = rand.nextInt((100 - 0) + 1) + 0; 
				if(no <=2)
				{	if(cpuid == 0)
						cpuid =1;
					else if(cpuid == 1)
						cpuid =0;
				//Writes log to log file 1000 servers per minute and two entries for each server in a minute
					out.write(startTime +"  " +"192.168."+ip1+"."+ip+ "  " + cpuid + "  " + randomNum+"\n"); 
					count++;
					no++;
				}
				if(no ==2 && ip<255)
				{
					ip++;
					no=0;
				}
				else if(no ==2 && ip ==255 &&ip1<4)
				{
					ip = 0;
					no =0;
					ip1++;
				}
				if(count == 2000)
				{	startTime+=60;
					count = 0;
					ip1=1;
					ip=0;
				}
			}
		}
		catch (IOException e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
		finally
		{
		    if(out != null) {
		        out.close();    //Closes the output file once done writing
		    }
		}
	}
	public static void main(String[] args) throws ParseException, IOException {
		
		Generator gen = new Generator(args[0]);   //Creating an object for class Generator
}
}
