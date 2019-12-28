CC = gcc
CFLAGS = -Wall -ansi -pedantic 
PRA = pipeit
PRB = printit
PRC = yeet
OBJS = pipeit.o printit.o yeet.o
all: pipeit printit yeet


pipeit : pipeit.o
	$(CC) $(CFLAGS) -o $(PRA) pipeit.o

printit : printit.o
	$(CC) $(CFLAGS) -o $(PRB) printit.o

yeet : yeet.o
	$(CC) $(CFLAGS) -o $(PRC) yeet.o

pipeit.o : pipeit.c
	$(CC) $(CFLAGS) -c pipeit.c

printit.o : printit.c 
	$(CC) $(CFLAGS) -c printit.c

yeet.o : yeet.c
	$(CC) $(CFLAGS) -c yeet.c

clean :
	rm yeet.o pipeit.o printit.o

