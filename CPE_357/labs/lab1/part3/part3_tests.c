#include <stdio.h>

#include "checkit.h"
#include "part3.h"

void test_sum_1()
{
   int arr_1[] = {1,2,3};
   checkit_int(sum(arr_1,3), 6);
}

void test_sum_2()
{
   int arr_2[] = {10,10,10,10};
   checkit_int(sum(arr_2,4), 40);
}

void test_sum_3()
{
   int arr_3[] = {0,0,0,0};
   checkit_int(sum(arr_3,4), 0);
}

void test_sum()
{
   test_sum_1();
   test_sum_2();
   test_sum_3();

}

int main(void)
{
   test_sum();

   return 0;
}
