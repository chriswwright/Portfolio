#include <stdio.h>

#include "checkit.h"
#include "part6.h"

/* define testing functions */
void test_create_point1(void)
{
   struct point p = create_point(1.9, -2.7);

   checkit_double(p.x, 1.9);
   checkit_double(p.y, -2.7);
}

void test_create_point2(void)
{
   struct point p = create_point(0.2, 12.1);

   checkit_double(p.x, 0.2);
   checkit_double(p.y, 12.1);
}

void test_create_point(void)
{
   test_create_point1();
   test_create_point2();
}

void test_square_1(void)
{
   struct rectangle r = {create_point(0,0), create_point(1,1)};

   checkit_boolean(this_is_a_square(r), 1);
}

void test_square_2(void)
{
   struct rectangle r = {create_point(0,1), create_point(4,4)};

   checkit_boolean(this_is_a_square(r), 0);
}


void test_squares(void)
{
   test_square_1();
   test_square_2();

}

int main(int arg, char *argv[])
{
   /* call testing function(s) here */
   test_create_point();
   test_squares();
   return 0;
}
