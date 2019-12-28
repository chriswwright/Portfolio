#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char* argv[]){
   char* parta = calloc(1,1);
   int num;
   num = 1;
   while(num != 0){
   num = read(0, parta, 1);
   if(num != 0){
   printf("%s\0",parta);}}
return 0;}
