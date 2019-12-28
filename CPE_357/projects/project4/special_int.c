#include <arpa/inet.h>
#include <string.h>

unit32_t extract_special_int(char *where, int len){
   int32_t val= -1;
   if((len >= sizeof(val)) && (where[0] & 0x80)){
      val = *(int32_t *)(where+len-sizeof(val));
      val = ntohl(val);



   }
}

