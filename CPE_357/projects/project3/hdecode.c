#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <sys/stat.h>
#include <math.h>
#include "functions.c"

int main(int argc,char* argv[]){
   int file;
   int i = 0;
   int wfile;
   char *c = malloc(4);
   char* binbuf = malloc(8192);
   struct LeafNode* temphead;
   create(0);

   if(argv[1] == '\0' || (argv[1][1] == '\0' && argv[1][0] == '-')){
      file = 0;
   }

   else{
         file = open(argv[1], O_RDONLY);
         if(argv[2] == NULL){
            wfile = 1;
         }
         else
               wfile = open(argv[2], O_TRUNC | O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);}
   decotree(file);
   makeTree();

   temphead = head;
   finder(temphead, 1, c);
   /*for(i = 0; i < 256; i++){printf("%c %s\n",table[i].name, table[i].location);}*/
   
   binbuf = decoder(file, argv);
   if(binbuf[0] == 0){binbuf[0]=10;}
   writer(binbuf, wfile);
   
   return 0;
}

