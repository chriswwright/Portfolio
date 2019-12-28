#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int set = 0;
char* readlongline(){
   int size = 0;
   char* longline = "";
   char character;
   longline = (char*) malloc(size + 1);
   for(size = 0; character != '\n'; size++){
      longline = (char*) realloc(longline, size + 1);
      character = getchar();
      if (character == -1){
         set = 1;
         return longline;
      }
      else{
      longline[size] = character;
      }
      

   }
   return longline;
}

char* uniq(char *new, char *last){

   if (strcmp(new, last)){
      printf("%s",new);
   }

   return new;

}

int main(void){
   char* p = readlongline();
   char* prev = p;
   char* to_free = prev;
   prev = uniq(p, "");
   while (set == 0){
      to_free = prev;
      p = readlongline();
      prev = uniq(p, prev);
      free(to_free);
      
   }
   return 0;

}
