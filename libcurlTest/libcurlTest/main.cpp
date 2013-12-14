//
//  main.cpp
//  libcurlTest
//
//  Created by Wing on 8/8/13.
//  Copyright (c) 2013 Wing. All rights reserved.
//

#include <iostream>
#include <string>
#include <curl/curl.h>
#include <curl/easy.h>
using namespace std;

/*
 CURLOPT_WRITEFUNCTION
     Function  pointer  that  should  match  the following prototype:
     size_t function( char *ptr,  size_t  size,  size_t  nmemb,  void
     *userdata);  This  function  gets  called  by libcurl as soon as
     there is data received that needs to be saved. The size  of  the
     data  pointed  to  by ptr is size multiplied with nmemb, it will
     not be zero terminated. Return  the  number  of  bytes  actually
     taken  care of. If that amount differs from the amount passed to
     your function, it'll signal an error to the library.  This  will
     abort the transfer and return CURLE_WRITE_ERROR.
 */

size_t write_data(char *ptr, size_t size, size_t nmemb, void *userdata){
    
    char** responseData = (char**)userdata;
    /* assuming the response is a string */
    *responseData = strndup(ptr, (size_t)(size *nmemb));
    printf("%s", *responseData);
    return strlen(*responseData);
}

int main(int argc, const char * argv[])
{
    
    CURL *curl = curl_easy_init();
    if(curl){
        curl_easy_setopt(curl, CURLOPT_URL, "http://www.google.com");
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, write_data);
        
        /* passing the pointer to the response as the callback parameter */
        char* response = NULL;
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        
#ifdef SKIP_PEER_VERIFICATION
        /*
         * If you want to connect to a site who isn't using a certificate that is
         * signed by one of the certs in the CA bundle you have, you can skip the
         * verification of the server's certificate. This makes the connection
         * A LOT LESS SECURE.
         *
         * If you have a CA cert for the server stored someplace else than in the
         * default bundle, then the CURLOPT_CAPATH option might come handy for
         * you.
         */
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
#endif
        
#ifdef SKIP_HOSTNAME_VERIFICATION
        /*
         * If the site you're connecting to uses a different host name that what
         * they have mentioned in their server certificate's commonName (or
         * subjectAltName) fields, libcurl will refuse to connect. You can skip
         * this check, but this will make the connection less secure.
         */
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);
#endif

        CURLcode res = curl_easy_perform(curl);
        if(res!=CURLE_OK){
            fprintf(stderr,"curl_easy_perform() failed: %s\n",
                    curl_easy_strerror(res));
        }
        curl_easy_cleanup(curl);
    }
    
    curl_global_cleanup();
    return 0;
}

