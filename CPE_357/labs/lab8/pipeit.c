#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/stat.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <unistd.h> 

int main(int argc, char* argv[]){
   int pid1, pid2, i, ii;
   int fd[2];
   int set = 0;
   int arg_cut;
   char *parta = calloc(100,1);
   char **args1;
   char **args2;
   pipe(fd);

   if(argc < 3){
      fprintf(stderr, "Usage: pipeit program1 [args]\\; program2 [args]\n");
      return -1;
   }

   for(i = 1; argv[i] != 0; i++){
   for(ii = 0; argv[i][ii] != 0; ii++){

      if(argv[i][ii] == ';'){
         arg_cut = i;
         set = 1;
      }
   }
}


   if(set == 0){
      fprintf(stderr, "Usage: pipeit program1 [args]\\; program2 [args]\n");
      return -1;
   }

   args1 = calloc(10 , 256);
   args2 = calloc(10, 256);
   set = 0;

   for(i = 1; argv[i] != 0; i++){
     if(i == arg_cut){set = 1;}
     if(set == 0 && i >= 1){
        args1[i-1] = argv[i+1];
     }
     if(set == 1){
        args2[i-arg_cut-1] = argv[i+1];
     }


   }


   pid1 = fork();

   if(pid1 == 0){
      dup2(fd[1], STDOUT_FILENO);
      close(fd[1]);
      close(fd[0]);
      set = execv(argv[1], args1);
      if(set == -1){
         fprintf(stderr, "ERRRRRRRRORRRR\n");
      }
      exit(0);
   }
   else{
   pid2 = fork();
   if(pid2 == 0){
      dup2(fd[0], STDIN_FILENO);
      close(fd[1]);
      close(fd[0]);
      set = execv(argv[arg_cut + 1], args2);
      if(set == -1){
         fprintf(stderr, "ERRRRRRRRORRRR\n");
      }
     exit(0);
   }
   else{
    close(fd[0]);
    close(fd[1]);
    wait(0);
    wait(0);
   
    return 0;
}}

}
