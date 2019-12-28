#include <stdio.h>
#include <dirent.h>
#include <stdlib.h>
#include <signal.h>
#include <ctype.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h> 
#include "parseline.c"
#include "runner.c"
int set = 0;
int ni = 0;
void catch_function(int signo) {
  if(signo == 14){
     exit(0);
  }
}

void par_catch(int signo){
  ni = 1;
  if(signo == 14){
     exit(0);
  }
}


int main(int argc, char* argv[]){
   int kpid;
   char * buffer = malloc(512);
   int i = 0;
   FILE *file;
   char* input = malloc(512);
   int pid;
   int ppid = getpid();
   if(argv[1] != 0){
	   set = 1;
	   file = fopen(argv[1], "r");
   }
   else{
	   set = 0;
   }

   
   while(1){
	  int fid[2];
	  int fd[2];
      int out_fd = dup(1);
	  int num;
	  int sset = 0;
      int in_fd = dup(fileno(stdin));
      FILE *readfile;
	  pipe(fid);
	  /*int save_fd = dup(fid[1]);*/
	  pipe(fd);
	  if(set == 1){
	  buffer = fgets(input, 512, file);  
	  if(buffer == 0){return 0;}
	  readfile = fopen("temp_rd_file", "w+");
	  fwrite(buffer, 1, strlen(buffer), readfile);
      fseek(readfile, 0 , SEEK_SET);	  
	  }
	  else{
          readfile = stdin; 
	  }
      kpid = fork();
	  pid = getpid();
	  if(pid != ppid){
		  
	     struct sigaction sa;
         sa.sa_handler = par_catch;
         sigemptyset(&sa.sa_mask);
         sa.sa_flags = 0;
   
		  
         if(sigaction(SIGALRM, &sa, NULL) == -1){
           fprintf(stderr, "ERROR!!!!\n");
           return 0;}
		 
         parseline(argc, argv, readfile);	 
		 write(fd[1], toklist[0].com[0], 512);
		 if(strncmp(toklist[0].com[0], "cd ", 3) != 0){
		 for(i = 0; toklist[i].com != 0; i++){}
		 num = i;
		 for(i = 0; toklist[i].com != 0; i++){
            runner(toklist[i].com, fid, sset);
		 }
		 }
		 
		 exit(10);
		 }
		 
	     else{
		 close(fid[0]);
		 close(fid[1]);
		 char *command = malloc(512);
		 struct sigaction sa;
		 int status;
		 pid = getpid();

         sa.sa_handler = catch_function;
         sigemptyset(&sa.sa_mask);
         sa.sa_flags = 0;
   
   
         if(sigaction(SIGINT, &sa, NULL) == -1){
           fprintf(stderr, "ERROR!!!!\n");
           return 0; }
		   
         if(sigaction(SIGALRM, &sa, NULL) == -1){
           fprintf(stderr, "ERROR!!!!\n");
           return 0;}		   

		   wait(&status);
		   remove("temp_rd_file");
		 if(status == 2560){
		     read(fd[0], command, 512);
			 
		 if(strncmp(command, "cd ", 3) == 0){
			 int x = 0;
			 x = chdir(command + 3);
			 if(x != 0){
				 printf("%s: No such file or direcotry\n", command + 3);
			 }
		 }}
		 
	    dup2(in_fd, 0);
		close(in_fd);
		
		dup2(out_fd, 1);
		close(out_fd);
		 }
   }
   return 0;
}







