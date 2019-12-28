#include <stdio.h>

#include "checkit.h"
#include "part4.h"

void test_lower_1()
{
   char str_1[] = "BaNaNa";
   checkit_string(str_lower(str_1, str_1), "banana");
}

void test_lower_2()
{
   char str_2[] = "";
   checkit_string(str_lower(str_2, str_2), "");
}

void test_lower_3()
{
   char str_3[] = "IM YELLING";
   checkit_string(str_lower(str_3, str_3), "im yelling");
}

void test_lower()
{
   test_lower_1();
   test_lower_2();
   test_lower_3();

}

int main(void)
{
   test_lower();

   return 0;
}
