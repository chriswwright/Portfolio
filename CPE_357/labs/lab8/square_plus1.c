#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/wait.h>
#include <unistd.h> 

int main(int argc, char* argv[]){
   int pid1; 
   int pid2 = 1;
   int fd[2];
   char* pstring= calloc(16, 1);
   int i = 0;
   char* p;
   int result;
   pipe(fd);

   if(argv[1] == NULL){
      fprintf(stderr, "Usage: ./a.out integer\n");
      return -1;
   }
   for(i = 0; argv[1][i] != 0; i++){
      if(argv[1][i] < 48 || argv[1][i]>57){
         fprintf(stderr, "Usage: ./a.out integer\n");
         return -1;
      }
   }
   
   pid1 = fork();
   if(pid1 == 0){
      result = strtol(argv[1], &p, 10);
      sprintf(pstring, "%d", result*result);
      write(fd[1], pstring, 16);
      exit(0);
   }

   else{
      wait(NULL);
      pid2 = fork();
      if(pid2 == 0){
         read(fd[0], pstring, 16);     
         result = strtol(pstring, &p, 10);
         sprintf(pstring, "%d", result + 1);
         write(fd[1], pstring, 16);
         close(fd[1]);
         exit(0);   }
 
   waitpid(pid2, NULL, 0);
   read(fd[0], pstring, 16);   
   printf("%s\n", pstring);
   return 0;}

}
