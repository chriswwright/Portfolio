#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <dirent.h>

int main(int argc, char* argv[]){
    char* ptr = malloc(100);
    DIR* dirp= opendir(argv[1]);
    struct dirent *dp;
    while(dp = readdir(dirp)){
       ptr = dp->d_name;
       printf("%s\n", ptr);
}
    
    return 0;
}
