#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <limits.h>

int main(int argc,char* argv[]){
   int i = 1;
   int m = 0;
   while(argv[i] != '\0'){
   
   struct stat fileStat;
   if(lstat(argv[i],&fileStat)<0){
     return 1;}

   printf("Information for '%s'\n",argv[i]);
   m = fileStat.st_mode;
   if(S_ISREG(m) == 1){
      printf("Type: Regular File\n");
   }
   else if(S_ISCHR(m) == 1){
      printf("Type: Character Device\n");  
   }
   else if(S_ISBLK(m) == 1){
      printf("Type: Block Device\n");  
   }
   else if(S_ISFIFO(m) == 1){
      printf("Type: FIFO\n"); 
   }
   else if(S_ISLNK(m) == 1){
      printf("Type: Symbolic Link\n");  
   }
   else if(S_ISSOCK(m) == 1){
      printf("Type: Socket\n");
   }
   else if(S_ISDIR(m) == 1){
      printf("Type: Directory\n");
   }
   printf("Size: %d\n", fileStat.st_size);
   printf("Inode: %d\n", fileStat.st_ino);
   printf("Links: %d\n", fileStat.st_nlink);
   m = fileStat.st_mode;
   if(S_ISREG(m) == 1){
   printf("Access: -");
   }
   else if(S_ISCHR(m) == 1){
   printf("Access: c");
   }
   else if(S_ISBLK(m) == 1){
   printf("Access: b");
   }
   else if(S_ISFIFO(m) == 1){
   printf("Access: p");
   }
   else if(S_ISLNK(m) == 1){
   printf("Access: l");
   }
   else if(S_ISSOCK(m) == 1){
   printf("Access: -");
   }
   else if(S_ISDIR(m) == 1){
   printf("Access: d");
   }

   printf( (fileStat.st_mode & S_IRUSR) ? "r" : "-");
   printf( (fileStat.st_mode & S_IWUSR) ? "w" : "-");
   printf( (fileStat.st_mode & S_IXUSR) ? "x" : "-");
   printf( (fileStat.st_mode & S_IRGRP) ? "r" : "-");
   printf( (fileStat.st_mode & S_IWGRP) ? "w" : "-");
   printf( (fileStat.st_mode & S_IXGRP) ? "x" : "-");
   printf( (fileStat.st_mode & S_IROTH) ? "r" : "-");
   printf( (fileStat.st_mode & S_IWOTH) ? "w" : "-");
   printf( (fileStat.st_mode & S_IXOTH) ? "x\n" : "-\n");
   i++;
}
}
