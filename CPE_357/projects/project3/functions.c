#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>
#include <math.h>

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
   size = 256;
   table = (struct ItemNode*) malloc(sizeof(struct ItemNode)*size);
   for(i = 0; i!=size; i++){
      struct ItemNode aNode;
      aNode.name = i;
      aNode.frequency = 0;
      aNode.location = NULL;
      table[i] = aNode;
      }
   }

void decotree(int file){
   struct LeafNode* current;
   struct LeafNode* previous;
   int *size = malloc(4);
   int ssize = 0;
   int hnum = 0;
   int hi = 0;
   int i = 0;
   int num = 1;
   int t = 0;
   head = malloc(sizeof(struct LeafNode));
   head->next = NULL;
   head->name = 0;
   head->left = NULL;
   head->right = NULL;
   head->frequency = 0;
   num = read(file, size, 4);
   ssize = *size;
   while(ssize != 0){
      num = read(file, size, 1);
      ssize--;
      hi = *size;
      num = read(file, size, 4);      
      hnum = *size;
      table[hi].frequency = hnum;
      }
      for(i = 0; i != 256; i++){
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

   
  
void reader(char* argv[]){
   struct LeafNode* current;
   struct LeafNode* previous;
   char buf[1];
   int num = 1;
   int i = 0;
   int t = 0;
   int file;
   head = malloc(sizeof(struct LeafNode));
   head->next = NULL;
   head->name = 0;
   head->left = NULL;
   head->right = NULL;
   head->frequency = 0;
   file = open(argv[1], O_RDONLY);
   if(file >= 0){
   while(num == 1){
      num = read(file, buf, 1);
      if(num != 0){
      /*printf("%c",buf[0]);*/
      table[(int)buf[0]].frequency++;}
   }
   for(i = 0; i != 256; i++){
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

void convert(char* x, int file){

   int out = 0;
   char* hex = malloc(1);
   out = (8*(x[0] - '0')+4*(x[1]-'0')+2*(x[2]-'0')+1*(x[3]-'0'));
   sprintf(hex, "%x", out%16);
   write(file, hex, 1);


}

void makeTree(){
   struct LeafNode* current = head;
   struct LeafNode* previous = NULL;
   struct LeafNode* a;
   struct LeafNode* b;
   int set = 1;
   int boolean = 0;
   if(head->next != NULL){
   while (set == 1){
     struct LeafNode* new = (struct LeafNode*) malloc(sizeof(struct LeafNode));
     boolean = 0;
     a = head;

     b = head->next;
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
   }}

}
void finder(struct LeafNode* x, int i, char* c){
    char* d = malloc(i +1);
    strcpy(d,c);
    if (x->left == NULL && x->right == NULL){
       table[(int)x->name].location = d;
    }
    if (x->left != NULL){
       d[i-1]='0';
       finder(x->left, i+1, d);}
    if (x->right != NULL){
       d[i-1]='1';
       finder(x->right, i+1, d);
    }

}

char* decoder(int file, char* argv[]){
   char* buf = malloc (8);
   char* ss = malloc(1);
   char* binbuf = malloc(8192);
   int* size = malloc(8);
   int num = 1;
   int i = 0;
   char* p;
   int hex = 0;
   while(num != 0){
      num = read(file, size, 1);
      if (num != 0){
     hex = *size;
     sprintf(buf, "%d%d%d%d%d%d%d%d",(hex/128)%2,(hex/64)%2,(hex/32%2),(hex/16)%2,(hex/8)%2,(hex/4)%2,(hex/2)%2,hex%2);
     strcat(binbuf, buf);}
   }
   return binbuf;
}

void writer(char* x, int file){
   struct LeafNode* b = head;
   char* buf = malloc(1);
   int set = 0;
   int i = 0;  
   while(x[i] != '\0'){
      set = 0;
      b = head;
      while(set == 0){
         if(b->left == NULL && b->right == NULL){
            if(table[(int)b->name].frequency != 0){
            buf[0] = (b->name);
            write(file, buf, 1);
            table[(int)b->name].frequency--;}
            else{i++; break;};
            set = 1;}
         else if(b->left != NULL && x[i] == '0'){
            b = b->left;
            i++;
            }
         else if(b->right != NULL && x[i] == '1'){
            b = b->right;
            i++;
         }
        else{break;}
      }
   }
   
}

void printer(char* argv[]){
   int i = 0;
   int ii = 0;
   char *hex;
   char *big = malloc(8192);
   char *temp = malloc(8192);
   int i_file = 0;
   int* size = malloc(4);
   int file;
   char* buf = malloc(1);
   int num = 1;
   int power = 0;
   int ssize = 0;
   
   for(i = 0; i != 256; i++){
      
      if(table[i].frequency != 0){(ssize)++;}
   }
   size = &ssize;
   if(argv[2] != NULL){
   file = open(argv[2], O_TRUNC | O_CREAT | O_RDWR, S_IRUSR | S_IWUSR);}
   else{ file = 1;}
   write(file, size, 4);
   for(i = 0; i != 256; i++){
      if(table[i].frequency != 0){
         size = &table[i].name;
         write(file, size, 1);
         size = &table[i].frequency;
         write(file, size, 4);
     }
   }
   i_file = open(argv[1], O_RDONLY);
   lseek(i_file, 0 ,SEEK_SET);
   while(num == 1){
      num = read(i_file, buf, 1);
      if(num != 0){
      sprintf(temp, "%s",table[(int)buf[0]].location);
      strcat(big, temp);}
   }
   hex = malloc(8);
   for(i = 0; i < strlen(big) ; i++){
      ssize = 0;
      if(i%8 == 0 && i!=0){
         for(ii = 8; ii != 0; ii--){
            power = pow(2,ii-1);
            ssize += (hex[8-ii]-'0')*power;
         }
         size = &ssize;
         write(file, size, 1);
         hex[i%8]=big[i];
      }
      else{
         hex[i%8]=big[i];
      }}
   if (i%8 != 0){
   for(i = i; i%8 != 0; i++){
         hex[i%8]='0'; 
    }
         /*printf("hex: %s\n",hex);*/
         for(ii = 8; ii != 0; ii--){
            power = pow(2,ii-1);  
            ssize += (hex[8-ii]-'0')*power;

         }
         size = &ssize;
      
      write(file, size, 1);
   }

}


