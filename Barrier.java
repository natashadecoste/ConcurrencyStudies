import java.util.Random;

class myThread implements Runnable{
	public Random rand;	//used to slow down some parts of the thread execution so we can see actions on the console
	public Barrier x;
	public String name;	//for console purposes to track which threads are executing
	
	public myThread(String n, Barrier s){	//constructor class
		name = n;
		x = s;
		rand = new Random();
		
		
	}
	
	public void begin() {	//start running the thread
		System.out.println("Thread " + name + " is running." );
		(new Thread(new myThread(name,x))).start();
		
	}
	
	public void statement(int i){	//running arbitrary instructions 
		try {
			
			System.out.println("Thread "+ name + " performed task No." + i);	
			Thread.sleep(rand.nextInt(4)*100);	//simulating a random amount of time to execute the statement 
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void finalStatement(){	//after sync, each thread has a final statement to prove it waits till sync of all the threads to move on
		System.out.println("Thread " + name + " performed its final instruction");
	}
	
	@Override
	public void run() {
									//decide how many statements to run before sync
		int j = rand.nextInt(6);
		for(int i = 0; i<j; i++){	//giving each thread a different amount of instructions before sync 
			statement(i);
		}
		try {
			System.out.println("Thread " + name + " has run the SYNC function");
			x.sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						//runs sync after it's set of instructions
		
		finalStatement(); 	//can go on to final statements once all have synced
		
	}
	
}
public class Barrier {
	

	public int amountOfThreads;	//the amount of threads running
	public int count;	//count will increment to make sure all the threads have gone through the sync methods

	
	
	
	public Barrier(int n){
		amountOfThreads = n;
		count = 0;
		
		for(int i=0;i<n;i++){		//creating N amount of threads
			myThread a = new myThread(Integer.toString(i),this);
			a.begin();
		}
	}
	
	public synchronized void sync() throws InterruptedException{	//only lets one thread in at a time
		count ++;	//counter to make sure all threads execute this

		
		if (count < amountOfThreads){
			wait();
		}
		else {
			notifyAll();
		}

	}
	

	public static void main(String[] args) {
		Barrier c = new Barrier(3);

	}

}
