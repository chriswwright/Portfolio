#include <stdio.h>
#include <math.h>
#include "part3.h"



int sum(int x[], int size){
   int sum = 0;
   int i = 0;
   for (i = 0; i < size; i++) {

      sum += x[i];

}
   return sum;
}
