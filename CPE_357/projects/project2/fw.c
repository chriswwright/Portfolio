#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "hashtable.c"
#include "queue.c"

int filereader(int n, int argc, char* argv[]){
   char num = 0;
   int set = 0;
   int i = n;
   int index = 0;
   int size = 0;
   char* word;
   FILE *file;
   head = (struct Node*)malloc(sizeof(struct Node));
   word = (char*)malloc(1);
   for(i; i != argc; i++){
      index = 0;
      file = fopen(argv[i], "r");
      num = 0;
      if(file != NULL){
      while(num != -1){
         num = fgetc(file);
         if(isalpha(num)!= 0){
            word = realloc(word, index+1);
            word[index] = tolower(num);
            index++;
         }
         else{
            if (index != 0){
               word[index] = '\0'; 
               if (set == 0){
                  set = 1;
                  head->name = malloc(strlen(word)+1);
                  strcpy(head->name, word);
               }
               else{
               struct Node *new;
               new = malloc(sizeof(struct Node));
               new->name = malloc(strlen(word)+1);
               strcpy(new->name, word);
               new->prev = head;
               head = new;
               } 
               word = NULL;
               free(word);
               word = (char*)malloc(1);             
               index = 0;
               size++;
            }
         }}
         
   }
   }
   return 2*size;
}
int main(int argc,char* argv[]){
   int i= 0;
   int ic = 0;
   int oc = 0;
   int max_size  = 10;
   int count = 0;
   int boolean = 0;
   char* p;
   struct Node *current;
   struct LNode *previous;
   struct LNode *lcurrent;
   struct LNode *lhead = malloc(sizeof(struct LNode));
   if((argv[1] != NULL) && (strncmp(argv[1], "-", 1) == 0)){
      if(strncmp(argv[1], "-n", 1024) == 0){
         for(i = 0; argv[2][i]!='\0';i++){
            if(isdigit(argv[2][i]) != 0){
            }
            else{
               printf("Usage: [-n] file1.txt file2.txt ... fileN.txt\n");
               return 0;}}
            max_size = (int)strtol(argv[2], &p, 10);
            SIZE = filereader(3, argc, argv);
         
      }
      else{
         printf("Usage: [-n] file1.txt file2.txt ... fileN.txt\n");
         return 0;
      }
   }
   else if((argv[1] != NULL) && (strncmp(argv[1], "-", 1) != 0)){
      SIZE = filereader(1, argc, argv);
   }

   if(SIZE != 0){
   create(SIZE);
}
   else{   printf("The top %d words (out of 0) are:\n",max_size);
   return 0;}
   current = head;
   while(current != NULL){
      insert(hash(current->name),current->name);
      current = current->prev;
   }
   head = NULL;
   free(head);

   lhead->name = NULL;
   lhead->freq = 0;
   lhead->next = NULL;
   for(i =0; i != SIZE; i++){
      /*printf("%s  %d  %d\n", hashTable[i].name, hashTable[i].frequency, hashTable[i].key);*/
      if (hashTable[i].frequency != -1){
         count++;
         lcurrent = lhead;
         boolean = 0;
         ic = 0;
         while (boolean == 0){
           if (lcurrent == NULL){
               struct LNode *new;
               new = malloc(sizeof(struct LNode));
               new->name = hashTable[i].name;
               new->freq = hashTable[i].frequency;
               previous->next = new;
               boolean = 1;           
              }
           else if (lcurrent->name == NULL){
             lcurrent->name = hashTable[i].name;
             lcurrent->freq = hashTable[i].frequency;
             previous = lcurrent;
             boolean = 1;
             }
           else if(hashTable[i].frequency > lcurrent->freq){
               struct LNode *new;
               new = malloc(sizeof(struct LNode));
               new->name = hashTable[i].name;
               new->freq = hashTable[i].frequency;
               new->next = lcurrent;
               if(lcurrent == lhead){
                 lhead = new;               
               }
               else{
               previous->next = new;}
               boolean = 1;     
               }

           else if(hashTable[i].frequency == lcurrent->freq){
               if(strcmp(hashTable[i].name,lcurrent->name) > 0){
                  struct LNode *new;
                  new = malloc(sizeof(struct LNode));
                  new->name = hashTable[i].name;
                  new->freq = hashTable[i].frequency;
                  new->next = lcurrent;
                  if(lcurrent == lhead){
                    lhead = new;            
                  }
                  else{
                  previous->next = new;}
                  boolean = 1;                         
               }
               else if(ic == max_size + 1){
                  boolean = 1;}
               else{
                  ic++;
                  previous = lcurrent;
                  lcurrent=lcurrent->next;
                  }
                  }
           else if(ic == max_size + 1){
               boolean = 1;}
           else{
               ic++;
               previous = lcurrent;
               lcurrent=lcurrent->next;
               }
         }
      }
   }
   hashTable = NULL;
   free(hashTable);
   lcurrent = lhead;
   oc = 0;
   i = 0;
   printf("The top %d words (out of %d) are:\n",max_size, count);
   if (count != 0){
   while (lcurrent != NULL && oc != max_size){
      if (strlen(lcurrent->name) > 500) {printf("%*d %s\n",9,2, lcurrent->name);}
      else{
      printf("%*d %s\n",9,lcurrent->freq, lcurrent->name);}
      i++;
      oc++;
      lcurrent = lcurrent->next;
}}
   lhead = NULL;
   free(lhead);
   return 0;
}
