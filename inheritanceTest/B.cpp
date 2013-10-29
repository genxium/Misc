#include "B.h"
#include "stdio.h"

B::B(){
    printf("B::B()\n");    
    sb=5;
}

B::~B(){
    
}

void B::foo(){
    sb=10;
}
