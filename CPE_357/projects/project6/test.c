#include <stdio.h>
#include <dirent.h>
#include <stdlib.h>
#include <signal.h>
#include <ctype.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <pwd.h>
#include <grp.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h> 
#include <unistd.h>

int main(int argc, char* argv[]){
	int i = 0;
	char* input = malloc(512);
		gets(input);
		printf("%s\n", input);
    return 0;	
}