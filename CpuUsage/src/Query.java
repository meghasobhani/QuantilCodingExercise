import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Query {
	String query;
	String ip;
	String cpu;
	String datapath;
	String dateAtStart,dateAtEnd;

	private static long[] result = new long[100] ;
	private static String[] usage = new String[100];
	private static String[] timeStamp = new String[100] ;
	//Constructor taking all input arguments for query like ip,startDate and time, endDate and time and datapath of log file to be readed
	public Query(String path,String ip,String cpu,String startDate, String startTime, String endDate, String endTime) throws ParseException{
		this.ip = ip;
		this.cpu = cpu;
		dateAtStart = startDate+" "+startTime;
		dateAtEnd = endDate+" "+endTime;
		this.dateAtStart = DateToUnix(dateAtStart);
		this.dateAtEnd= DateToUnix(dateAtEnd);
		this.readFile(path, ip, cpu, dateAtStart, dateAtEnd);	
		this.printResult(ip);
	
	}
	//readFile method returns the logs of a particular server's CPu usage which is queried
	private void readFile(String path,String ip,String cpu,String dateAtStart,String dateAtEnd) 
	{	
		String cpuUsage;
		try {
			// FileReader reads text files in the default encoding.	
			FileInputStream  fileReader = new FileInputStream (path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileReader));
			String line=null;
			int timesection;
			int start = Integer.parseInt(dateAtStart);   //Starting timestamp
			int end = Integer.parseInt(dateAtEnd);       //Ending timestamp
			line= bufferedReader.readLine();
			for(int i=0;i<result.length;i++)
			{	result[i]=0;
				timeStamp[i]=null;
			}
			int index=0;
			int ind=0;
			int ipCount = 0;
			int ipFalse =0;
			while((line= bufferedReader.readLine()) != null )   //Checks line by line for usage log and if found stores the usage and timestamp in an array.
			{	
				
				String str = line;
				String[] parts = str.split(" ");
				timesection= Integer.parseInt(parts[0]);
				cpuUsage = parts[6];
				ipCount++;
				if(line.contains(" "+ip+" ") && line.contains(" "+cpu+" ") && timesection >start && timesection < end) 
				{	
					result[index]=timesection;
					usage[ind]=cpuUsage;
					index++;
					ind++;
				}
				else
				{
					ipFalse++;   //Checks if ip found in log file and increments the counter if not found
				}
			}
			if(ipCount == ipFalse)
				System.out.println("Ip or timestamp not found in logs");
			
			bufferedReader.close();  
			fileReader.close();    //Closes the file reader
			int i=0;
			while(result[i] != 0)
			{
				timeStamp[i] = unixToDate(result[i]);   //Stores the timestamps found corresponding to query IP
				i++;
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	//Converts unix timestamp tp Date and Time in format yyyy-MM-dd H:mm
	private static String unixToDate(long unix_timestamp) throws ParseException {    
		long timestamp = unix_timestamp * 1000;
		Date date = new Date(timestamp);     
		Format sdf = new SimpleDateFormat("yyyy-MM-dd H:mm");
		String formatdate;
		formatdate= sdf.format(date);
		return formatdate;
	}
	//Converts Date and time in given format yyyy-MM-dd H:mm to unix Timestamp
	private static String DateToUnix(String date) throws ParseException {    
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:mm");
		Date d = dateFormat.parse(date );
		long unixTime = (long) d.getTime()/1000;
		return Long.toString(unixTime);
	}
	//Prints the query result on console   
	public  void printResult(String ip){	
		int i=0;
		if(timeStamp[i]==null)
			return;
		System.out.println("CPU usage on "+ip+":");
		while(timeStamp[i] != null)
		{
			System.out.print("("+timeStamp[i] +","+ usage[i]+"%),");  //Timestamp and usage corresponding to the server IP and cpu id
			i++;
		}
		System.out.println("\n");
	}
	public static void main(String[] args) throws ParseException{
		Scanner sc = new Scanner(System.in);
		String path = args[0];
		while (sc.hasNextLine())   //Scans for user input for query or EXIT
		{
		    String query = sc.nextLine();
		    
		    if(query.equals("EXIT"))   //If user gives EXIT then program is terminated
		    {
		    	System.exit(0);	
		    }  
		    else {  //If query is given it is parsed and passed to the constructor for class Query
		    try 
		    {
		    	String[] querySplit = query.split(" ");
		    	Query q = new Query(path,querySplit[1],querySplit[2],querySplit[3],querySplit[4],querySplit[5],querySplit[6]);
		    	
		    }
		    
		    catch(Exception e){
		    	System.out.println("Please input appropriate query");
		    }
		}		 
		}
	}

}


