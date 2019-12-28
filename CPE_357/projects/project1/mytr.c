#include <stdio.h>
#include <string.h>
#include "mytr.h"
#include <stdlib.h>

char* input_decoder(char *input, int len){
   char a = 0;
   char *ret_s;
   int index = 0;
   int i = 0;
   ret_s = (char*)malloc(len);
   for(i = 0; input[i] != '\0'; i++){
      a = input[i];
      if(a == 92 && input[i+1] != '\0'){
         if(input[i+1] == 92){
            ret_s[index] = '\\';
            i++;
         }

         else if(input[i+1] == 'n'){
            ret_s[index] = '\n';
            i++;
         }

         else if(input[i+1] == 't'){
            ret_s[index] = '\t';
            i++;
         }
         else{
            ret_s[index] = input[i+1];
            i++;
         }
     }
     else{
        ret_s[index] = input[i];
     }
     index++;

   }
   return ret_s;
}

int counter(char *args[], int index){
   int i_two = 0;
   for(i_two = 0; args[index][i_two] != '\0'; i_two++){
   }
   return i_two;
}


int del_handler(char *args[])
{
   int set = 0;
   int i_one = 0;
   char x;
   while(scanf("%c", &x) != EOF){
   set = 0;
   for(i_one = 0; args[2][i_one] != '\0'; i_one++){
      if(x == args[2][i_one]){
         set = 1;
}
}
   if(set == 0){
   printf("%c", x);}
}
   return 0;
}



int in_handler(int two_len, char *args[])
{
   int set = 0;
   int index = 0;
   int i_one = 0;
   int in = 0;
   char x;
   while(scanf("%c", &x) != EOF){
   set = 0;
   index = -1;
   for(i_one = 0; args[1][i_one] != '\0'; i_one++){
      if(x == args[1][i_one] && set != 1){
         index = i_one;
         in = i_one;}
         }
      if (index != -1){
      set = 1;
      if(in >= two_len){
         x = args[2][two_len - 1];}
      else{
         x = args[2][in];
   }
   
   
   }
   printf("%c", x);
   }
   return 0;
      
   
}






int main(int argc, char *argv[])
{
   int len_one = 0; 
   int len_two = 0;
   if(argv[2] == NULL){
      printf("Usage mytr [-d] 'set1' 'set2'\n");
      return 0;       
   }
   else if(argv[1][0] == 45 && strcmp(argv[1], "-d") != 0){
      printf("Usage mytr [-d] 'set1' 'set2'\n");
      return 0;
   }
   len_one = counter(argv, 1);
   len_two = counter(argv, 2);

   argv[2] = input_decoder(argv[2], len_two);
   if(!strcmp(argv[1], "-d")){
   del_handler(argv);
   }
   else{
   argv[1] = input_decoder(argv[1], len_one);
   in_handler(len_two, argv);
   }
   return 0;
}

