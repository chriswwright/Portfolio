#include <stdio.h>
#include <string.h>
#include <sys/types.h> 
#include <unistd.h>

int main(int argc, char* argv[]){
   pid_t pid;
   int status;
   FILE *file;
   if(argv[1] == '\0'){
      printf("usage: tryit command\n");
   }
   else{
   pid = fork();
   if(pid == 0){
      pid = getpid();
      file = fopen(argv[1], "r");
      if(file == NULL){
         printf("%s: No such file or directory.\n", argv[1]);
         exit(-1);
      }
      execl(argv[1],argv[1],NULL);
      exit(-1);}
   pid = wait(&status);
   if(status != 0){
      printf("Process %d exited with an error value.\n",pid);
   }
   else{
      printf("Process %d succeeded.\n",pid, status);}
      return 0;
   }}

