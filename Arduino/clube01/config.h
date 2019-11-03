
#ifndef  CONFIG_MAIN

  extern char *nome;
  extern char *prenome16;
  extern   char *nome16;
  extern   char *sigla;
  extern char *hello;
  extern char *versao;
  
#else
  char *nome = "Monitor de Temperatura";
  char *prenome16 = "Monitor  Temperatura";
  char *nome16 = "Temperatura";
  char *sigla = "Maker";
  char *hello = "Bom dia!";
  char *versao = "2019.10.31";
#endif

#define NUM_MAX_SENSORES 8
#define PINO_BARRAMENTO_ONEWIRE 8   // barramento onewire no D4 ok 

#define UNKNOW_COMAND "Comando desconhecido."
