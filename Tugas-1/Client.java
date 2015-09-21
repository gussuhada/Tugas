import java.io.*;
import java.net.Socket;
public class Client {
  private static Socket sock = null;
 public static void main(String[] args)
 {
    // The default port.
     int portNumber = 1113;
     // The default host.
     String host = "172.24.202.151";

     if (args.length < 2) {
    System.out.println("***********************************************************************");
       System.out.println("*            Jika Melakukan Koneksi Dari Komputer Lain                *");
       System.out.println("*  Gunakan Perintah: java Client \n   *");
    System.out.println("***********************************************************************");

     } else {
       host = args[0];
       portNumber = Integer.valueOf(args[1]).intValue();
    }
  try {
       sock = new Socket(host, portNumber);
   SendThread sendThread = new SendThread(sock);
   Thread thread = new Thread(sendThread);thread.start();
   RecieveThread recieveThread = new RecieveThread(sock);
   Thread thread2 =new Thread(recieveThread);thread2.start();
  } catch (Exception e) {System.out.println(e.getMessage());}
 }
}
class RecieveThread implements Runnable
{
 Socket sock=null;
 BufferedReader recieve=null;

 public RecieveThread(Socket sock) {
  this.sock = sock;
 }//end constructor
 public void run() {
  try{
  recieve = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));//get inputstream
  String msgRecieved = null;
  while((msgRecieved = recieve.readLine())!= null)
  {
   System.out.println("Dari Server: " + msgRecieved);
   System.out.println("Kirim Pesan Anda:");
  }
  }catch(Exception e){System.out.println(e.getMessage());}
 }//end run
}//end class recievethread
class SendThread implements Runnable
{
 Socket sock=null;
 PrintWriter print=null;
 BufferedReader brinput=null;

 public SendThread(Socket sock)
 {
  this.sock = sock;
 }//end constructor
 public void run(){
  try{
  if(sock.isConnected())
  {
   System.out.println("***********************************************************************");
   System.out.println("         Client connected to "+sock.getInetAddress() + " on port "+sock.getPort());
   System.out.println("***********************************************************************");
   System.out.println("*           Ketikkan Pesan dengan kata 'exit' untuk keluar            *");
   System.out.println("***********************************************************************");
   this.print = new PrintWriter(sock.getOutputStream(), true);
  while(true){
   System.out.println("Kirim Pesan Anda:");
   brinput = new BufferedReader(new InputStreamReader(System.in));
   String msgtoServerString=null;
   msgtoServerString = brinput.readLine();
   this.print.println(msgtoServerString);
   this.print.flush();

   if(msgtoServerString.equals("exit"))
   break;
   }//end while
  sock.close();}}catch(Exception e){System.out.println(e.getMessage());}
 }//end run method
}//end class