#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
long
hash(char *str)
{
    long hash = 33;
    int c;

    while (c = *str++)
        hash = ((hash << 5) + hash) + c;

    return abs(hash);
}

int SIZE = 0;
struct ItemNode {
   char *name;
   int key;
   int frequency;

};
   struct ItemNode* hashTable;
   struct ItemNode aNode;

void create(int size){
   int i = 0;
   aNode.key = -1;
   aNode.name = "";
   aNode.frequency = -1;
   SIZE = size;
   hashTable = (struct ItemNode*) malloc(sizeof(struct ItemNode)*size);
   for(i = 0; i!=size; i++){
      hashTable[i] = aNode;

   }
   
}

int dehash(long key){
   return abs(key % SIZE);
}

struct ItemNode find(int key){
   int index = dehash(key); 
   while(hashTable[index].key!= -1){
      if(hashTable[index].key == key){
        return hashTable[index];}

      ++index;
      index %= SIZE;
   }
   
   printf("Item not found\n");
   return aNode;
}

int insert(long key, char *name){
   int count = 0;
   int set = 0;
   int empty = 1;
   int index = dehash(key);
   while(set == 0){
      if(hashTable[(index+1)%SIZE].key == -1){
         set = 1;
      }
      else if(hashTable[(index+1)%SIZE].key == key){
         set = 1;
         empty = 0;
      }
      index++;
      index %= SIZE;
      }
   if (empty == 1){
      hashTable[index].name = name;
      hashTable[index].key = key;
      hashTable[index].frequency = 1;      
   }
   else{
      hashTable[index].frequency += 1;
   }

while (count != SIZE){

count++;}
return 0;
}

void displayAll() {

}


