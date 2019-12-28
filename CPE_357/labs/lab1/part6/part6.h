#ifndef PART6_H
#define PART6_H

struct point
{
   double x;
   double y;
};

struct rectangle
{
   struct point a;
   struct point b;
};

int this_is_a_square(struct rectangle);

struct point create_point(double x, double y);


#endif
