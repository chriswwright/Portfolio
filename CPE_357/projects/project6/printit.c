#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char* argv[]){
   char* boi;
   int i = 0;
   for(i = 0; argv[i] != 0; i ++){
     sprintf(boi, "%s\n",argv[i]);
     write(1, boi, strlen(boi));
     }


    return 0;
}

