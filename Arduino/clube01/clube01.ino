#define CONFIG_MAIN
#include "config.h"
#include "comando.h"
#include "sensors-18B20.h"

void setup() {
        pinMode(LED_BUILTIN, OUTPUT);
        pinMode(9, OUTPUT);
        Serial.begin(115200);
        Serial.println(nome);
        Serial.println(hello);
        setupTemp18B20();
}

int interation =0;

void loop() {
    interation++;
     LE18B20();
     if(qfluxo())
          sendLine();
     String *com = getCommand();
     if(com != 0){
            //Serial.println(*com);
            interpreta(*com);
            } 
}
