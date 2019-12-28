CC = gcc
CFLAGS = -Wall -ansi -pedantic 
PRA = mytar
OBJS = mytar.o
all: mytar


mytar : mytar.o
	$(CC) $(CFLAGS) -o $(PRA) mytar.o 

mytar.o : mytar.c functions.o
	$(CC) $(CFLAGS) -c mytar.c

functions.o : functions.c
	$(CC) $(CFLAGS) -c functions.c

clean :
	rm mytar.o functions.o
