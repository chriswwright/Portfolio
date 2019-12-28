#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

struct LeafNode {
   char name;
   struct LeafNode* left;
   struct LeafNode* right;
   struct LeafNode* next;
   int frequency;
};




struct ItemNode {
   char name;
   int frequency;
   char* location;

};

   struct ItemNode* table;
   struct LeafNode* head;

void create(int size){
   int i = 0;
   size = 128;
   table = (struct ItemNode*) malloc(sizeof(struct ItemNode)*size);
   for(i = 0; i!=size; i++){
      struct ItemNode aNode;
      aNode.name = i;
      aNode.frequency = 0;
      table[i] = aNode;
      }
   }

void reader(char* argv[]){
   struct LeafNode* current;
   struct LeafNode* previous;
   char buf[1];
   int num = 1;
   int i = 0;
   int t = 0;
   FILE *file;
   head = malloc(sizeof(struct LeafNode*));
   head->next = NULL;
   head->name = 255;
   head->left = NULL;
   head->right = NULL;
   head->frequency = 0;
   file = fopen(argv[1], "r");
   if(file != NULL){
   while(num == 1){
      num = fread(buf, 1, 1, file);
      table[(int)buf[0]].frequency++;
   }
   for(i = 0; i != 128; i++){
      if(table[i].frequency != 0){
         t = 1;
         current = head;
         previous = NULL;
         while(t == 1){
            if(current->next == NULL){
               struct LeafNode* new;
               new = malloc(sizeof(struct LeafNode));
               new->next = NULL;
               new->frequency = 0;
               new->left = NULL;
               new->right = NULL;
               current->name = table[i].name;
               current->frequency = table[i].frequency;
               current->next = new;
               t = 0;   
            }
            else if((table[i].frequency < current->frequency)||(table[i].frequency == current->frequency && table[i].name < current->name)){
               struct LeafNode* new;
               new = malloc(sizeof(struct LeafNode));
               new->next = current;
               new->left = NULL;
               new->right = NULL;
               new->name = table[i].name;
               new->frequency = table[i].frequency;
               if(current != head){
                  previous->next = new;
               }
               else{
                  head = new;
               }

               t = 0;
            }
            else{
               previous = current;
               current = current->next;
            }

        }

    }

   }
   current = head;
   while(current->next != NULL){
      if (current->next->name == 0){
      current->next = NULL;
      break; }
      current = current->next;
}

}
}
void makeTree(){
   struct LeafNode* current = head;
   struct LeafNode* previous = NULL;
   struct LeafNode* a;
   struct LeafNode* b;
   int set = 1;
   int boolean = 0;
   while (set == 1){
     boolean = 0;
     a = head;
     b = head->next;
     struct LeafNode* new = (struct LeafNode*) malloc(sizeof(struct LeafNode));
     new->left = a;
     new->right = b;
     new->next = NULL;
     new->frequency = a->frequency + b->frequency;
     new->name = 0;
     current = head;
     /*printf("test\n");
     while(current != NULL){
      printf("%d  %d\n", current->name, current->frequency);
      current = current->next;
   }*/

     if (head->next->next != NULL){
        head = head->next->next;
        current = head;
        previous = NULL;
        while(boolean == 0){
          if(new->frequency <= current->frequency){
             new->next = current;
             if (previous != NULL){
                previous->next = new;
             }
             else{
                head = new;
             }
             
             boolean = 1;
          }
          else if (current->next == NULL){
             current->next = new;
             boolean = 1;
          }
          else{
             previous = current;
             current = current->next;
          }
        }
     }
     else if(head->next->next == NULL){
        head = new;
        set = 0;
     }
     else{set = 0;}}

      current = head;
      while(current != NULL){
      current = current->next;
   }

}
void finder(struct LeafNode* x, int i, char* c){
    char* d = malloc(i +1);
    strcpy(d,c);
    if (x->left == NULL && x->right == NULL){
       table[x->name].location = d;
    }
    if (x->left != NULL){
       d[i-1]='0';
       finder(x->left, i+1, d);}
    if (x->right != NULL){
       d[i-1]='1';
       finder(x->right, i+1, d);
    }

}
   

void printer(char* argv[]){
   int i = 0;
   FILE *file;
   int useless = 0;
   int path = 0;
   char *c = malloc(1);
   finder(head, 1, c);
   if(argv[2] != NULL){
   file = fopen(argv[2], "w");
   for(i = 0; i != 128; i++){
      if(table[i].frequency != 0){
         fprintf(file, "0x%d%x:  ",(table[i].name/16),table[i].name % 16);
         fprintf(file, "%s\n",table[i].location);
     }}}
   else{
   for(i = 0; i != 128; i++){
      if(table[i].frequency != 0){
         printf("0x%d%x:  ",(table[i].name/16),table[i].name % 16);
         printf("%s\n",table[i].location);
   }
   }   
   }
}


