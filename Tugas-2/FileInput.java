    import java.io.BufferedInputStream;  
    import java.io.DataInputStream;  
    import java.io.File;  
    import java.io.FileInputStream;  
    import java.io.FileNotFoundException;  
    import java.io.IOException;  
      
    /** 
     * Program ini membaca baris file teks per baris dan menampilkannya ke console. 
     * Digunakan FileOutputStream untuk membaca file. 
     *  
     */  
      
    public class FileInput {  
      
        public static void main(String[] args) {  
      
            File file = new File("guss.txt");  
            FileInputStream fis = null;  
            BufferedInputStream bis = null;  
            DataInputStream dis = null;  
      
            try {  
                fis = new FileInputStream(file);  
      
                // di sini BufferedInputStream ditambahkan untuk pembacaan secara cepat.  
                bis = new BufferedInputStream(fis);  
                dis = new DataInputStream(bis);  
      
                // dis.available() akan mengembalikan nilai 0 jika file sudah tidak punya baris lagi.  
                while (dis.available() != 0) {  
      
                    // statement ini membaca baris dari file dan menampilkannya ke console.  
                    System.out.println(dis.readLine());  
                }  
      
                // buang semua resources setelah menggunakannya.  
                fis.close();  
                bis.close();  
                dis.close();  
      
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  