import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import java.io.*;

public class ArduinoSensors
{

    
    static boolean sempre = true;
   
    static public void main(String[] args)
    {
        ArduinoSensors node = new ArduinoSensors ();
        byte q2[] = {'T','\n'};
        SerialPort comPort = node.inicializaSerial();
        try{
            while(sempre){
                comPort.writeBytes(q2,2);
                node.atualiza(comPort, 6000);
                Thread.sleep(20000);
            }
        } catch( Exception err){}
        comPort.closePort();
    }

    public double temp[] = new double[8];
    public double temp_1[] = new double[8];
   

    int iteratio = 0;

    public String atualiza(SerialPort comPort, int delay)
    {
        String buf = getLine(comPort);
        String s[] = buf.split("[\t]");
        for(int i = 0; i < s.length-1; i++){
                temp[i] = Double.parseDouble(s[i]);
            };
        System.out.println(buf);
        iteratio++;
        try{
            for(int i = 0; i < s.length-1; i++){
                pub("temp"+i,s[0]); 
                temp_1[i] = temp[i]; 
                Thread.sleep(delay/6);
            }
        } catch( Exception err){}
        return buf;
    }

    public String getLine(SerialPort comPort)
    {
        String buf = "";
        try {
            for(int i=0; (comPort.bytesAvailable() == 0) && i<50;i++)
                Thread.sleep(20);

            byte[] readBuffer = new byte[comPort.bytesAvailable()];
            int numRead = comPort.readBytes(readBuffer, readBuffer.length);
            buf = new String (readBuffer);
        } catch (Exception e) { e.printStackTrace(); }
        return buf;

    }

    public SerialPort inicializaSerial()
    {
        byte q1[] = {'Q','1','\n'};
        SerialPort comPorts[] = SerialPort.getCommPorts();
        SerialPort comPort=null;
        for(int i=0;i<comPorts.length;i++){
            comPort = comPorts[i]; // SerialPort.getCommPort("/dev/ttyACM0");
            comPort.setComPortParameters(115200, 8, 1, 0); // default connection settings for Arduino
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); 
            comPort.openPort();
            comPort.writeBytes(q1,3);
            try{
                Thread.sleep(1000);
            } catch (Exception e) { e.printStackTrace(); }
            String buf = getLine(comPort);
            String s[] = buf.split("[-]");
            if (s[0].equals("TH") && s[1].equals("pH")){
                System.out.println(comPort + ": ");
                return comPort;
            }
        }
        return comPort;
    }

    public void pub(String topic, String msg)
    {
        String str = "mosquitto_pub -h " + Broker.url ;
        str = str + " -u " + Broker.user;
        str = str + " -P " + Broker.pass;
        str = str + " -p " + Broker.port;
        str = str + " -t " + topic;
        str = str + " -m " + msg;
        ExecCmd.Go(str);    
    }

    public static void save(String nome, String content) 
    {
        File aFile = new File(nome);
        System.out.println("Salvando "+aFile.getName()+"...");      
        try {
            PrintStream p = new PrintStream(aFile);
            p.println(content);
            p.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(aFile);
        }
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
