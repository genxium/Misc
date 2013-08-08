#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <pthread.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#define BUFFSIZE 1024

char send_buff[BUFFSIZE];
char recv_buff[BUFFSIZE];
int sockfd, portno;
struct sockaddr_in serv_addr;
struct hostent *server;
pthread_t send_th, recv_th;  
int iret_send, iret_recv;

void error(const char *msg)
{
    perror(msg);
    exit(0);
}

void* send_thread(void* prt){
	while(fgets(send_buff,BUFFSIZE,stdin)){
		int n = write(sockfd,send_buff,strlen(send_buff));
    		if (n < 0) {error("ERROR writing to socket");}
		bzero(send_buff, BUFFSIZE);
	}
}

void* recv_thread(void* ptr){
	while(1){
		int n = read(sockfd,recv_buff,BUFFSIZE);
    		if (n < 0) 
         		error("ERROR reading from socket");
		if(strlen(recv_buff)>0)
    			printf("%s\n",recv_buff);
		bzero(recv_buff, BUFFSIZE);
	}
}

int main(int argc, char *argv[])
{
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
    portno = atoi(argv[2]);
    // socket file descriptor is independent of addresses
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) error("ERROR opening socket");
    server = gethostbyname(argv[1]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");
    printf("Please enter the message: ");
    
    iret_send=pthread_create(&send_th, NULL, send_thread, NULL);
    iret_recv=pthread_create(&recv_th, NULL, recv_thread, NULL);

    pthread_join(send_th, NULL);
    pthread_join(recv_th, NULL);

    close(sockfd);

    return 0;
}
