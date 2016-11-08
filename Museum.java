import java.util.Random;
import java.util.concurrent.Semaphore;



public class Museum {
	public int capacity;
	public int status; //1 is open, 2 is close
	Random randomElement;
	Semaphore people;
	Counter guestCounter;
	
	class Director implements Runnable{
		public Director(){
			System.out.println("The Director is created");
		}
		
		public void begin(){
			(new Thread(new Director())).start();
		}

		@Override
		public void run(){
			open();
			try{
				Thread.sleep(9000);
				
				
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			close();
			
		}
		
		public void open(){
			status = 1;
			EastDoor e = new EastDoor();
			WestDoor w = new WestDoor();
			e.begin();
			w.begin();

		}
		
		public void close(){
			status = 0;
		}
		
		
		
	}
	
	
	class EastDoor implements Runnable{
		String name = "East Door";
		
		EastDoor(){
			
		}

		public void letPeopleIn() {
			if (guestCounter.guestCount < capacity) {
				try {
					Thread.sleep(randomElement.nextInt(4) * 450);
					guestCounter.increase();
					people.release();
					System.out.println("Guest Entered, Guests = " + guestCounter.guestCount);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			

		}
		
		
		public void begin(){
			(new Thread(new EastDoor())).start();
		}

		@Override
		public void run() {
			System.out.println("Opening the East Door");
			
			while(status == 1){ //while the museum is open, let people in
				letPeopleIn();
			}
			
			System.out.println("Closing the East Door");
		}


		
	}
	
	class WestDoor implements Runnable{
		String name = "West Door";
		
		WestDoor(){
			
		}
		
		public void letPeopleLeave(){
				try {
					Thread.sleep(randomElement.nextInt(8) * 450);
					people.acquire();
					guestCounter.decrease();
					System.out.println("Guest Left :(, Guests = "+guestCounter.guestCount);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		
		
		public void begin(){
			(new Thread(new WestDoor())).start();
		}

		@Override
		public void run() {
			System.out.println("Opening the West Door");
			while(status == 1){
				letPeopleLeave();
			}
			while(status == 0){
				if(guestCounter.guestCount > 0 ){
					letPeopleLeave();
				}
				
				else{
					System.out.println("Closing the West Door");
					System.out.println("Museum Closed.");
					System.exit(0);
				}


			}
			
		}//end of run
		
		
	}
	
	
	class Counter {
	    private int guestCount;
	    
	    Counter(){
	    	 guestCount = 0;
	     }


	    public synchronized void increase(){
	        guestCount++;
	    }

	    public synchronized void decrease(){
	        guestCount--;
	    }
	    
	    public int getCapacity(){	//to get the guestCount but not be able to access it without the synchronized functions
	    	return guestCount;
	    	
	    }

	}
	
	
	
	
	
	
	
	
	
	
	public Museum(int n){
		randomElement = new Random();
		capacity = n;
		people = new Semaphore(0);
		guestCounter = new Counter();
		Director d = new Director();
		d.begin();
	}

	public static void main(String[] args) {
		Museum ROM = new Museum(25);

	}

}
