#include <stdio.h>

#include "checkit.h"
#include "part1.h"

void test_swap_1()
{
   int a = 20;
   int b = 10;
   swap(&a, &b);
   checkit_int(a, 10);
   checkit_int(b, 20);
}

void test_swap_2()
{

   int a = 0;
   int b = 0;
   swap(&a, &b);
   checkit_int(a, 0);
   checkit_int(b, 0);
}

void test_swap_3()
{

   int a = 01;
   int b = 02;
   swap(&a, &b);
   checkit_int(a, 2);
   checkit_int(b, 1);
}
void test_swap()
{
   test_swap_1();
   test_swap_2();
   test_swap_3();

}

int main(void)
{
   test_swap();

   return 0;
}
