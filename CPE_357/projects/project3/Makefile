CC = gcc
CFLAGS = -Wall -ansi -pedantic 
PRA = hencode
PRB = hdecode
OBJS = hencode.o hdecode.o
all: hencode hdecode


hencode : hencode.o
	$(CC) $(CFLAGS) -o $(PRA) hencode.o -lm

hdecode : hdecode.o
	$(CC) $(CFLAGS) -o $(PRB) hdecode.o -lm

hencode.o : hencode.c functions.o
	$(CC) $(CFLAGS) -c hencode.c

hdecode.o : hdecode.c functions.o
	$(CC) $(CFLAGS) -c hdecode.c

functions.o : functions.c
	$(CC) $(CFLAGS) -c functions.c

clean :
	rm *.o $(MAIN) core*
