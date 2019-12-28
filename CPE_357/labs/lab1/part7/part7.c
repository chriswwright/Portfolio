#include <stdio.h>
#include "part7.h"


void outputter(char *args[])
{
   int i = 0;
   
   for (i = 0; args[i] != '\0'; i++){
   
      if (args[i][0] == '-') {
         printf("%s\n", args[i]);
      }
   }
}


int main(int argc, char *argv[])
{
   outputter(argv);
   return 0;
}
