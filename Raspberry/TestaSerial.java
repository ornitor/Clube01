/*
 * TESTA A PORTA SERIAL 
 * ONDE DEVE ESTAR UM ARDUINO 
 * QUE RTESPONDE AO COMANDO T
 * (Vide codigo do arduino)
 * 
 */
import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import java.io.*;

public class TestaSerial
{
    
    static boolean sempre = true;
   
    static public void main(String[] args)
    {
        TestaSerial node = new TestaSerial ();
        byte q2[] = {'T','\n'};
        SerialPort comPort = node.inicializaSerial();
        try{
            while(sempre){
                comPort.writeBytes(q2,2);
                System.out.println("Enviei: T" ); 
                Thread.sleep(500);
                String buf = getLine(comPort);
                System.out.println("Recebi: " + buf);
                Thread.sleep(1500);
            }
        } catch( Exception err){}
        comPort.closePort();
    }

 
    static public String getLine(SerialPort comPort)
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


}
