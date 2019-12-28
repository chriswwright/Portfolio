#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "bst.c"

int main(int argc,char* argv[]){
   int i = 0;
   struct LeafNode* current;
   struct LeafNode* tree;
   if(argv[1] != NULL){
   create(0);
   reader(argv);
/*   for(i = 0; i != 128; i++){
      printf("%d  %d\n",table[i].name, table[i].frequency);
   }*/
    current = head;
    if(current->name == 0){return 0;}

/*    while(current->next != NULL){
      printf("%d  %d\n", current->name, current->frequency);
      current = current->next;
   }*/
   makeTree();
   printer(argv);}
   return 0;
}

