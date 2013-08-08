/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <pthread.h>
#define BUFFSIZE 1024

char send_buff[BUFFSIZE];
char recv_buff[BUFFSIZE];
int sockfd, newsockfd, portno;
struct sockaddr_in serv_addr;
struct hostent *server;
pthread_t send_th, recv_th;  
int iret_send, iret_recv;

void error(const char *msg)
{
    perror(msg);
    exit(1);
}

void* recv_thread(void* ptr){
	while(1){
		int n = read(newsockfd,recv_buff,BUFFSIZE);
    		if (n < 0) 
         		error("ERROR reading from socket");
		if(strlen(recv_buff)>0)
    			printf("%s\n",recv_buff);
		bzero(recv_buff, BUFFSIZE);
		n = write(newsockfd,"I got your message",18);
     		if (n < 0) error("ERROR writing to socket");
	}
}

//command line: ./server port_number

int main(int argc, char *argv[])
{
     socklen_t clilen;
     char buffer[256];
     struct sockaddr_in serv_addr, cli_addr;
     int n;
     if (argc < 2) {
         fprintf(stderr,"ERROR, no port provided\n");
         exit(1);
     }
     sockfd = socket(AF_INET, SOCK_STREAM, 0);
     if (sockfd < 0) error("ERROR opening socket");
     bzero((char *) &serv_addr, sizeof(serv_addr));
     portno = atoi(argv[1]);
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons(portno);
     if (bind(sockfd, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0) 
              error("ERROR on binding");
     listen(sockfd,5);
     clilen = sizeof(cli_addr);
     newsockfd = accept(sockfd, 
                 (struct sockaddr *) &cli_addr, 
                 &clilen);
     if (newsockfd < 0) 
          error("ERROR on accept");

     iret_recv=pthread_create(&recv_th, NULL, recv_thread, NULL);

     pthread_join(recv_th, NULL);
     close(newsockfd);
     close(sockfd);
     return 0; 
}
