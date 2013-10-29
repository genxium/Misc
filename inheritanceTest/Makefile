CC = g++
CFLAGS = -Wall -g -c
OBJS = main.o B.o A.o

test: $(OBJS)
	$(CC) $(OBJS) -o test
main.o: main.cpp B.h
	$(CC) $(CFLAGS) main.cpp -o main.o
B.o: B.cpp B.h A.h
	$(CC) $(CFLAGS) B.cpp -o B.o
A.o: A.cpp A.h
	$(CC) $(CFLAGS) A.cpp -o A.o
