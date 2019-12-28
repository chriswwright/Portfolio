#include <stdio.h>
#include <unistd.h>
#include <limits.h>




int main(void){
   printf("%d\n", sysconf(_SC_OPEN_MAX));
}
