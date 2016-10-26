import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class SeatBooking {
	public static Queue<Client> queue = new LinkedList<Client>();
	public static Semaphore numCustomers; 
	public static int filledSeats;
	public static int emptySeats;
	public static Random randomElement = new Random();
	Boolean [] seats; //the seats


class Clerk implements Runnable{
	public int chosen;
	public int identifier;
	
	Clerk(int n){
		identifier = n;
	}

	public void run(){
		while(emptySeats > 0){
			helpClient();
		}
		System.exit(0);
	}


	
	private void helpClient(){
		try {
			SeatBooking.numCustomers.acquire();
			confirm(this.identifier);
			emptySeats --;
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}
	
	public void begin(){
		(new Thread(new Clerk(identifier))).start();
	}
}

class Client implements Runnable{
	public int identifier;
	public int seatNum;
	
	
	public Client(int num){
		identifier = num;
		seatNum = -1;
	}
	
	public void choose(){
		try{
			Thread.sleep(randomElement.nextInt(6)*55);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		int number;
		Random random = new Random();
		do{
		number = random.nextInt(seats.length);}
		while (seats[number] == false);
		
		System.out.println("Client " + identifier + " wants to have seat: " + number);
		seatNum = number;
		
		if(seatNum == -1){
			System.out.println("error");
			}
	}




		

	public void run(){
		try{
			
			Thread.sleep(randomElement.nextInt(6)*15);
			choose();
			SeatBooking.queue.add(this);
			System.out.println("Client " + identifier + " joined the line, now queue = " + SeatBooking.queue.size());
			SeatBooking.numCustomers.release(); //releasing on the 
			
		}catch(InterruptedException e){
			e.printStackTrace();
			
		}
		
	}
	
	public void begin(){
		/*identifier = (filledSeats +1);*/
		System.out.println("Client number: " + (filledSeats+1) + " has arrived.");
		filledSeats++;
		(new Thread(new Client(identifier))).start();
	}
}


	
	public SeatBooking(int n){
		//System.out.println("here");
		//n is the amount of seats, m is the amount of remote terminals/clerks
		numCustomers = new Semaphore(0);
		filledSeats = 0;
		emptySeats = n;
		//Boolean [] seats;
		seats = new Boolean[n];
		//emptySeats = numberOfSeats;
		for(int i=0; i< n; i++){
			seats[i] = true; //all the seats are empty they can all be picked
		}
	}
		
	public synchronized void confirm(int i){
		
		try {
			Thread.sleep(randomElement.nextInt(4)*200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Client temp = SeatBooking.queue.remove();
		System.out.println("Client " + temp.identifier + " is being served by Clerk " + i);
		System.out.println("queue size = " + SeatBooking.queue.size());
		int y = temp.seatNum;
		Random rand = new Random();
		while (seats[y] == false){
			y= rand.nextInt(seats.length);
		
		}

		//REMOVE THIS
		seats[y] = false;
		System.out.println("Client " + temp.identifier + " recieved a ticket for seat " + y);
	}
		
	
	public static void main(String[] args) {
		int seatingCapacity = 10;
		int remoteTerminals = 3;

		SeatBooking Event = new SeatBooking(seatingCapacity);
		
		while(remoteTerminals > 0){
			Clerk t = Event.new Clerk(remoteTerminals);
			t.begin();
			remoteTerminals --;
		}


		// staggering the arrival of clients
		while (filledSeats < seatingCapacity) {
			int n = randomElement.nextInt(4);
			if (n == 2) {
				Client c = Event.new Client(filledSeats+1);
				c.begin();
			}
			Client c = Event.new Client(filledSeats+1);
			c.begin();
			try{
				Thread.sleep(randomElement.nextInt(14)*100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		
		
		

	}// while end bracket
}