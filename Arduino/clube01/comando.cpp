#include <Arduino.h>
#include "config.h"
#include <string.h>

String str = "";
String commandbuf[10];

void split(char * str, char *delimitador);
void toogleFluxo();
void sendLine();

bool fluxo = false;


String *getCommand() // run over and over
{
   while(Serial.available()){
   char c = Serial.read();
   if (c != '\n')
     str = str + c;
   else{
    //Serial.println("chegou: " + str);
    return &str;
   }
  }
  return 0;
}


void interpreta(String  c)
{
        split(c.c_str(),", ");
        if(commandbuf[0].compareTo("G")==0){
          
        } else if(commandbuf[0].compareTo("Q")==0) {  Serial.print(sigla);  Serial.print(" ");  Serial.println(versao); // identifica o Agente arduino
        } else if(commandbuf[0].compareTo("T")==0) {  sendLine();  // Q envia line sob demanda
        } else if(commandbuf[0].compareTo("C")==0){  toogleFluxo();  // C ATIVA/DESATIVA FLUXO VIA USB
        }  else 
            Serial.println(UNKNOW_COMAND);        
        str = "";
}


void split(char * str, char *delimitador)
{
    char *str1, *token;
    char *saveptr1;
    int j=0;
    commandbuf[0] = strtok_r(str, delimitador, &saveptr1);
    for (j = 1;j<10 ; j++) {
        commandbuf[j] = strtok_r(NULL, delimitador, &saveptr1);
        if (commandbuf[j] == NULL)
            break;
    }
}

void toogleFluxo()
{
      if(fluxo){
        fluxo = false;      
        Serial.println("Fluxo desativado"); 
      } else {
        fluxo = true;      
        Serial.println("Fluxo ativado"); 
      }
}

bool qfluxo()
{
  return fluxo;
}

void sendMillis()
{
  Serial.print("Tempo (s):\t");
  Serial.println(millis()/1000.);
 
}
