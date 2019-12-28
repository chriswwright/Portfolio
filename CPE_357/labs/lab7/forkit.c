#include <stdio.h>
#include <string.h>
#include <sys/types.h> 
#include <unistd.h> 

int main(){
   pid_t pid;
   printf("Hello, world!\n");
   
   if(fork() == 0){
      pid = getpid();
      printf("This is the child, pid %d\n",pid);
      exit(0);}
   else{
   pid = getpid();
   printf("This is the parent, pid %d\n",pid);}
   wait();
   printf("This is the parent, pid %d, signing off.\n",pid);
return 0;
}
