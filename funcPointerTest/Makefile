CC = g++
CFLAGS = -Wall -g -c
LFLAGS = -Wall -g
NODEBUGFLAGS = -Wall -c 
OBJS = main.o MyClass.o 

main.out: $(OBJS)
	$(CC) $(OBJS) -o main.out
main.o: main.cpp MyClass.h
	$(CC) $(CFLAGS) main.cpp -o main.o
MyClass.o: MyClass.cpp MyClass.h
	$(CC) $(CFLAGS) MyClass.cpp -o MyClass.o
	
