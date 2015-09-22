import java.io.*;
import java.net.Socket;

public class ClientLCG {
	private static Socket guss_CS = null;
	public static void main (String [] args) {
	
		// The default host.
		String guss_SHost = "127.0.0.1";
		// String guss_SHost = "172.24.202.151";
		// The default port.
		int guss_SPort = 1234;

		if (args.length == 2) {
			guss_SHost = args [0];
			guss_SPort = Integer.valueOf (args [1]).intValue ();
		}
		
		System.out.println ("");
		System.out.println ("( (( ((( Let's Ghat - Guss ))) )) )");
		System.out.println ("");
		System.out.println ("Side in		: Client");
		System.out.println ("Host IP		: " + guss_SHost);
		System.out.println ("Host Port	: " + guss_SPort);
		System.out.println ("Status		: Sending request for a connection");
		System.out.println ("");
		System.out.println ("To connect to host from another IP and Port	:");
		System.out.println ("		  Type 'java ClientLCG IP Port'");
		System.out.println ("		  (e.g., java ClientLCG 172.24.202.1 1111)");
		System.out.println ("");
		
		try {
			guss_CS = new Socket (guss_SHost, guss_SPort);
			
			Guss_GetMsgFromServerThd guss_getIt = new Guss_GetMsgFromServerThd (guss_CS);
			Thread guss_Thd1 = new Thread (guss_getIt);
			guss_Thd1.start ();
			
			Guss_SendMsgToServerThd guss_sendIt = new Guss_SendMsgToServerThd (guss_CS);
			Thread guss_Thd2 = new Thread (guss_sendIt);
			guss_Thd2.start ();
		}
		
		catch (Exception guss_Ex) {
			System.out.println (guss_Ex.getMessage ());
		}
	}
}

class Guss_GetMsgFromServerThd implements Runnable {
	Socket guss_CS_Req = null;
	BufferedReader guss_BR = null;

	public Guss_GetMsgFromServerThd (Socket guss_CS_Req) {
		this.guss_CS_Req = guss_CS_Req;
	}//end constructor
	
	public void run() {
	
		try {
			guss_BR = new BufferedReader (new InputStreamReader (this.guss_CS_Req.getInputStream ()));//get inputstream
			
			String guss_SMsg = null;
			
			while ((guss_SMsg = guss_BR.readLine ()) != null) {
				if (guss_SMsg.equals ("EXIT"))
					break;
					
				System.out.println ("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b Server		: " + guss_SMsg);
				System.out.print ("You		: ");
			}
			
			this.guss_CS_Req.close ();
			System.exit (0);
		}
		
		catch (Exception guss_Ex) {
			System.out.println (guss_Ex.getMessage ());
		}
	}//end run
}//end class recievethread

class Guss_SendMsgToServerThd implements Runnable {
	Socket guss_CS_Req = null;
	PrintWriter guss_PW = null;
	BufferedReader guss_BR = null;

	public Guss_SendMsgToServerThd (Socket guss_CS_Req) {
		this.guss_CS_Req = guss_CS_Req;
	}//end constructor
 
	public void run () {
		try {
			if (this.guss_CS_Req.isConnected ()) {
				System.out.println ("Status		: Connected to " + this.guss_CS_Req.getInetAddress () + " on port " + guss_CS_Req.getPort ());
				System.out.println ("To leave	: Type 'exit'");
				System.out.println ("");
				System.out.println ("Type your message below, and press enter to send.");
				System.out.println ("");
				this.guss_PW = new PrintWriter (this.guss_CS_Req.getOutputStream (), true);
				
				while (true) {
					System.out.print ("You		: ");
					
					guss_BR = new BufferedReader (new InputStreamReader (System.in));
					String guss_CMsg = null;
					
					guss_CMsg = guss_BR.readLine ();
					this.guss_PW.println (guss_CMsg);
					this.guss_PW.flush ();

					if(guss_CMsg.equals("exit"))
						break;
				}//end while
				
				this.guss_CS_Req.close ();
			}
		}
		
		catch (Exception guss_Ex) {
			System.out.println (guss_Ex.getMessage ());
		}
	}//end run method
}//end class