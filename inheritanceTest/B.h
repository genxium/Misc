#include "A.h"

class B:virtual public A{
public:
    B();
    virtual ~B();
public:
    int sb;
    void foo();
};
