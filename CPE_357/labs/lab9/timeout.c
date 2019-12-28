#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 
#include <signal.h>

void catch_function(int signo) {
	  printf("Child timeout\n");
  exit(-1);
}



int main(int argc, char* argv[]){
   int status;
   int flag = 0;
   int pid;
   int time;
   char* p;

   struct sigaction sa;
   sa.sa_handler = catch_function;
   sigemptyset(&sa.sa_mask);
   sa.sa_flags = 0;

   if(argc < 3){
      fprintf(stderr, "ERROR\n");
      return 0;
   }

   if(sigaction(SIGALRM, &sa, NULL) == -1){
      fprintf(stderr, "ERROR!!!!\n");
      return 0;
   }
   time = strtol(argv[1], &p, 10);
   alarm(time);
   pid = fork();
   if(pid == 0){
      execv(argv[2], argv+3);
      exit(0);
   }
   else{
      pid = wait(&status);
      if(status != 0){
         printf("Child timeout\n");
         exit(-1);
      }
      printf("Child finsihed succesfully\n");
      return 0;
   }
     
}
