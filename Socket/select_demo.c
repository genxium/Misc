#include <stdio.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>

#define STDIN 0 // file descriptor for standard input

int main(){
	struct timeval tv;
	fd_set readfds;
	tv.tv_sec=2;
	tv.tv_usec=500000;

	FD_ZERO(&readfds); // flush readfds
	FD_SET(STDIN, &readfds); // add file descriptor STDIN to be snifferd

	// wait for 2.5 seconds and check for updates of fd_sets 
	select(STDIN+1,&readfds,NULL,NULL,&tv);
	
	// one has to press "Enter" to trigger the "key pressed" event
	if(FD_ISSET(STDIN,&readfds))
		printf("A key was pressed!\n");
	else
		printf("Timed out.\n");

	return 0;
}


