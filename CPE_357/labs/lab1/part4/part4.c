#include <stdio.h>
#include <ctype.h>
#include "part4.h"



char* str_lower(char x[], char y[]){

   int i = 0;
   for (i = 0; x[i] != '\0'; i++) {
      
      char lower_char = tolower(x[i]);
      y[i] = lower_char;
   }

   return y;
}



