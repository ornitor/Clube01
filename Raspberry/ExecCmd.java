import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public abstract class ExecCmd {
        
     public abstract void interpreta(String cmdline) ;
   
      
     public void go(String cmdline) 
     {
               fala("...executando: " + cmdline);
               try {
                     String line;
                     Process p = Runtime.getRuntime().exec(cmdline);
                     
                     BufferedReader input = 
                       new BufferedReader
                         (new InputStreamReader(p.getInputStream()));
                     while ((line = input.readLine()) != null) {
                         interpreta(line);
                       }
                     input.close();
               } catch (Exception err) {
                   err.printStackTrace();
               }
     }
          
     static public void Go(String cmdline) 
     {
    	           fala("...executando: " + cmdline);
    		   try {
    			     String line;
    			     Process p = Runtime.getRuntime().exec(cmdline);
    			     
    			     BufferedReader input = 
    			       new BufferedReader
    			         (new InputStreamReader(p.getInputStream()));
    			     while ((line = input.readLine()) != null) {
    			    	 fala(line);
    			       }
    			     input.close();
    		   } catch (Exception err) {
    			   err.printStackTrace();
    		   }
     }
	  

     public static void Save(String nome, String content) 
     {
            File aFile = new File(nome);
            fala("Salvando "+aFile.getName()+"...");        
            try {
              PrintStream p = new PrintStream(aFile);
              p.println(content);
              p.close();

            } catch (Exception e) {
              e.printStackTrace();
              System.err.println(aFile);
            }
      }
      
     static public String getTemporalName(String nome) 
     {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            Date date = new Date();
            return nome+dateFormat.format(date);
     }
    
     static void fala(String s){
           System.out.println(s);

     }
        
     public static void CreateFolderIfNotExist(String path)
     {
          File diretorio = new File(path); // ajfilho é uma pasta!
          if (!diretorio.exists()) {
             diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
             System.out.println("Diretório novo criado");
          } 
     }
  }







