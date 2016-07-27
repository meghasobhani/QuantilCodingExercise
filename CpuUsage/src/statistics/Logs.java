package statistics;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logs {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final long CURRENT_TIME = System.currentTimeMillis();
	private static final long MILLISECONDS_PER_DAY = 86400000L;
	private static final Date START_DATE = new Date(CURRENT_TIME - MILLISECONDS_PER_DAY);
	private static final Date END_DATE =  new Date(CURRENT_TIME);
	
	private String filepath;
	public Logs(String filepath){
		this.filepath = filepath;
		this.generate(filepath,START_DATE,END_DATE);
	}
	public void generate(String filepath,Date START_DATE,Date END_DATE ){
		Date startDate = START_DATE;
		Date endDate = END_DATE;
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		
	}
	
	public static void main(String[] args){
		if(args.length != 1)
		{
			System.out.println("Please provide a path for output file to store the logs"); 
			System.exit(1);
		}
		
		Logs logs = new Logs(args[0]);
	}
}
