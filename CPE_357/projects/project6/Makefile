CC = gcc
CFLAGS = -pedantic
MAIN = mush
OBJS = mush.o
all : $(MAIN)

$(MAIN) : $(OBJS)
	$(CC) $(CFLAGS) -o $(MAIN) $(OBJS)

mush.o : mush.c parseline.o runner.o
	$(CC) $(CFLAGS) -c mush.c

runner.o : runner.c
	$(CC) $(CFLAGS) -c runner.c	

parseline.o : parseline.c
	$(CC) $(CFLAGS) -c parseline.c

clean :
	rm parseline.o mush.o runner.o
