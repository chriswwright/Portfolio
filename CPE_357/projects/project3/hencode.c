#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <math.h>
#include "functions.c"

int main(int argc,char* argv[]){
   char *c = malloc(4);
   int *size = malloc(4);
   int file = 0;
   struct LeafNode* current;
   *size = 0;
   create(0);
   if(argv[1] != NULL){
   reader(argv);
    current = head;
    if(current->name == 0){file = open(argv[2], O_TRUNC | O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);
      write(file, size, 4);
      return 0;}

   makeTree();
   finder(head, 1, c);
   printer(argv);}
   else{
      printf("No such file or directory\n");
      return 0;
   }
   table = NULL;
   head = NULL;
   return 0;
}

