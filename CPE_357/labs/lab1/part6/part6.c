#include "part6.h"

struct point create_point(double x, double y)
{
   struct point p = {x, y};
   return p;
}

int this_is_a_square(struct rectangle rect){

    double length = rect.b.x - rect.a.x;
    double width = rect.b.y - rect.a.y;
    return length == width;

}

