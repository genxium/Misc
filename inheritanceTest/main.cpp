#include "B.h"
#include "stdio.h"

int main(){
    B* bInstance=new B();
    printf("bInstance.sa=%d, bInstance.sb=%d\n", bInstance->sa, bInstance->sb);
    bInstance->foo();
    printf("bInstance.sa=%d, bInstance.sb=%d\n", bInstance->sa, bInstance->sb);
    delete bInstance;
    return 0;
}
