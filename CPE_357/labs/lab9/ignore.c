#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 
#include <signal.h>

void catch_function(int signo) {
}



int main(int argc, char* argv[]){
   char* buff = malloc(512);
   while(1){
      if(signal(SIGINT, catch_function) == SIG_ERR){
         fprintf(stderr, "oops\n");
         return 0;
      }
      read(0, buff, 512);
      if(strncmp(buff, "quit", 4) == 0){
         return 0;
      }

   }



}
