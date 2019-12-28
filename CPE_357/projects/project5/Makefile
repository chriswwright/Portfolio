CC = gcc
CFLAGS = -Wall -ansi -pedantic
MAIN = parseline
OBJS = parseline.o
all : $(MAIN)

$(MAIN) : $(OBJS)
	$(CC) $(CFLAGS) -o $(MAIN) $(OBJS)

clean :
	rm parseline.o
