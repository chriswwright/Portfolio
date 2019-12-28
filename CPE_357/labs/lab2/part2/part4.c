#include <stdio.h>
#include <ctype.h>
#include "part4.h"


char* str_lower(char x[], char y[]){

   char* yptr = NULL;
   for (yptr = y; *yptr != '\0'; yptr++) {
      
      *yptr = tolower(*yptr);
      
   }

   return y;
}



