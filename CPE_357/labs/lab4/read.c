#include <stdio.h>
#include <time.h>
#include <fcntl.h>

#define THEFILE "/usr/lib/locale/locale-archive"
#define ARRAY 2048


int main(void){
   char buf[ARRAY];
   int num = ARRAY;
   FILE *file;
   file = fopen(THEFILE, "r");
   while(num == ARRAY){
   num = fread(buf, 1, ARRAY, file);
   }
   return 0;
}
