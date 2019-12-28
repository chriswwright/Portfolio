#include <stdio.h>
#include <dirent.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <pwd.h>
#include <time.h>
#include <grp.h>
#include <sys/types.h>
#include <sys/stat.h> 
#include <unistd.h>
#include "functions.c"

int main(int argc, char* argv[]){
   int i = 0;
   int times = argc;


   int wfile;

   char* args = (char*) malloc(7);  
   strcat(args, "000000");
   args[6] = 0;
   if(argc < 2){
      fprintf(stderr, "usage: mytar [ctxvS]f tarfile [ path [ ...  ]  ])\n");
      return 0;
   }
   else if(argv[1][0] == '-'){
      i = 1;
   }
   for(i=i; argv[1][i] != 0; i++){
      if(argv[1][i] == 'c'){args[0] = '1';}
      else if(argv[1][i] == 't'){args[1] = '1';}
      else if(argv[1][i] == 'x'){args[2] = '1';}
      else if(argv[1][i] == 'v'){args[3] = '1';}
      else if(argv[1][i] == 'S'){args[4] = '1';}
      else if(argv[1][i] == 'f'){args[5] = '1';}
      else{
      fprintf(stderr, "usage: mytar [ctxvS]f tarfile [ path [ ...  ]  ])\n");
      return 0;
      }
   }
   if((args[0] == '1' && args[1] == '1') || (args[1] == '1' && args[2] == '1') || (args[0] == '1' && args[2] == '1') || (args[0] == '0' && args[1] == '0' && args[2] == '0')){
   fprintf(stderr, "usage: mytar [ctxvS]f tarfile [ path [ ...  ]  ])\n");
   return 0;
   }
   else if(args[0] == '1'){
       
 
       wfile = open(argv[2], O_TRUNC | O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
       for(i = 3; i != times; i++){
          FILE *fp = fopen(argv[i], "r");
          if(fp == 0){
             fprintf(stderr, "%s: Nonexistant file.\n", argv[i]);
          }
          else{
          create(argv[i], wfile, args[3]);
          }
   
   }
       eoa(wfile);          
   }


   else if(args[1] == '1'){
       if(argc <= 2){
          fprintf(stderr, "mytar [ctxvS]f tarfile [ path [ ...  ]  ])\n");
          return 0;
       }
       else if(argc != 3){

       printtoc(argv[2], args[3], argv);
       
       }
       else{
          printtoc(argv[2], args[3], argv);
       }
   }
   else{
       if(argc <= 2){
          fprintf(stderr, "mytar [ctxvS]f tarfile [ path [ ...  ]  ])\n");
          return 0;
       }
       else if(argc != 3){

       extract(argv[2], args[3], argv);
       
       }
       else{
          extract(argv[2], args[3], argv);
       }
   }
return 0;
}
