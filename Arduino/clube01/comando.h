#include <Arduino.h>

String *getCommand();

void interpreta(String  c);

void sendTempsConsole();

void split(char * str, char *delimitador);

bool qfluxo();

#define commandOK() Serial.println("OK");
