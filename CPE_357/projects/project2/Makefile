CC = gcc
CFLAGS = -Wall -ansi -pedantic
MAIN = fw
OBJS = fw.o
all : $(MAIN)


$(MAIN) : $(OBJS)
	$(CC) $(CFLAGS) -o $(MAIN) $(OBJS)

fw.o : fw.c hashtable.o queue.o
	$(CC) $(CFLAGS) -c fw.c 

hashtable.o : hashtable.c
	$(CC) $(CFLAGS) -c hashtable.c 
queue.o : queue.c
	$(CC) $(CFLAGS) -c queue.c
clean :
	rm *.o $(MAIN) core*
