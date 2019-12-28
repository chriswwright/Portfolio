#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char* argv[]){
    char* path = malloc(256);
    char* ptr;

    ptr = realpath(argv[1], path);
    
    printf("%s\n",ptr);
    return 0;
}
