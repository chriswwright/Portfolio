CC = gcc
CFLAGS = -Wall -ansi -pedantic
MAIN = mytr
OBJS = mytr.o
all : $(MAIN)

$(MAIN) : $(OBJS)
	$(CC) $(CFLAGS) -o $(MAIN) $(OBJS)

example.o : mytr.c
	$(CC) $(CFLAGS) -c mytr.c

clean :
	rm *.o $(MAIN) core*
