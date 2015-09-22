import java.io.*;
import java.net.*;
import java.lang.*;

public class ServerLCG {
	public static void main (String [] args) throws IOException {
		final int guss_SPort = 1234;
		ServerSocket guss_SS = new ServerSocket (guss_SPort);
		
		System.out.println ("");
		System.out.println ("( (( ((( Let's Ghat - Guss ))) )) )");
		System.out.println ("");
		System.out.println ("Side in		: Server");
		// System.out.println ("Host/IP	: " + guss_SS.getLocalAddress ());
		System.out.println ("Port		: " + guss_SPort);
		System.out.println ("Status		: Waiting for a connection");

		Socket guss_CS = guss_SS.accept ();
		System.out.println ("");
		System.out.println ("Status		: Receiving a connection");
		System.out.println ("Guest IP	: " + guss_CS.getInetAddress ());
		System.out.println ("Guest Port	: " + guss_CS.getPort ());
		System.out.println ("To leave	: Type 'exit'");
		System.out.println ("");
		System.out.println ("Type your message below, and press enter to send.");
		System.out.println ("");
		System.out.print ("You (Server)	: ");
		
		Guss_GetMsgFromClientThd guss_getIt = new Guss_GetMsgFromClientThd (guss_CS);
		Thread guss_Thd1 = new Thread (guss_getIt);
		guss_Thd1.start ();
		
		Guss_SendMsgToClientThd guss_sendIt = new Guss_SendMsgToClientThd (guss_CS);
		Thread guss_Thd2 = new Thread (guss_sendIt);
		guss_Thd2.start ();
	}
}

class Guss_GetMsgFromClientThd implements Runnable {
	Socket guss_CS_Ret = null;
	BufferedReader guss_BR = null;

	public Guss_GetMsgFromClientThd (Socket guss_CS_Ret) {
		this.guss_CS_Ret = guss_CS_Ret;
	}
	
	public void run () {
		
		try {
			guss_BR = new BufferedReader (new InputStreamReader (this.guss_CS_Ret.getInputStream ()));

			String guss_CMsg = null;
			while (true) {
				while ((guss_CMsg = guss_BR.readLine ()) != null) {
					if (guss_CMsg.equals ("EXIT"))
						break;
					
					System.out.println ("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b Client		: " + guss_CMsg);
					System.out.print ("You (Server)	: ");
				}
				this.guss_CS_Ret.close ();
				System.exit (0);
			}

		}
		
		catch (Exception guss_Ex) {
			System.out.println (guss_Ex.getMessage ());
		}
	}
}

class Guss_SendMsgToClientThd implements Runnable {
	Socket guss_CS_Ret = null;
	PrintWriter guss_PW = null;

	public Guss_SendMsgToClientThd (Socket guss_CS_Ret) {
		this.guss_CS_Ret = guss_CS_Ret;
	}
	
	public void run () {
		try {
			guss_PW = new PrintWriter (new OutputStreamWriter (this.guss_CS_Ret.getOutputStream ()));

			while (true) {
				BufferedReader guss_BR = new BufferedReader (new InputStreamReader (System.in));

				String guss_SMsg = null;
				guss_SMsg = guss_BR.readLine ();

				guss_PW.println (guss_SMsg);
				guss_PW.flush ();
				System.out.print ("You (Server)	: ");

				if (guss_SMsg.equals ("exit"))
					break;
			}
			
			this.guss_CS_Ret.close ();
		}
		
		catch (Exception guss_Ex) {
			System.out.println (guss_Ex.getMessage ());
		}
	}
}