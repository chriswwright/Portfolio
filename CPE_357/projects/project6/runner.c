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

int runner(char** com, int fd[], int flag){
	int pid;
	int set = 0;
	int n = 0;
	int num = 0;
	pid = fork();
	if(pid == 0){
		if(strncmp(com[0], "cd " , 3) == 0){
		   return(10);
		}
	    if(strncmp(com[1], "pipe", 4) == 0){
		    dup2(fd[0], 0);
		}
	    else if(strcmp(com[1], "original stdin") != 0){
		    int in_file = open(com[1], 0666);
			dup2(in_file, 0);
			close(in_file);
		}
	    if(strncmp(com[2], "pipe", 4) == 0){
		    dup2(fd[1], 1);
		}
		else if(strcmp(com[2], "original stdout") != 0){
		    int out_file = open(com[2], O_TRUNC | O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
			dup2(out_file, 1);
			close(out_file);		
		}
		close(fd[0]);
		close(fd[1]);
		num = execvp(com[4], com+4);
		printf("%s: No such file or directory\n", com[4]);
		exit(-1);
	}
		
	
	else{
		int status;
		close(fd[1]);
		wait(&status);
		if(status != 0){
			exit(-1);
		}
		
	}
	return 0;
}