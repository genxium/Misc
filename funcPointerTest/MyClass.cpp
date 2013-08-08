//
//  MyClass.cpp
//  funcPointerTest
//
//  Created by Wing on 5/8/13.
//  Copyright (c) 2013 Wing. All rights reserved.
//

#include "MyClass.h"

MyClass::MyClass(){
    m_pFunc = NULL;
}

MyClass::~MyClass(){
    
}

bool MyClass::init(void (*pFunc)(string message)){
    bool bRet = false;
    do{
        m_pFunc = pFunc;
        bRet = true;
    }while(false);
    return bRet;
}

void MyClass::useFunc(string messageToPass){
    (*m_pFunc)(messageToPass);
}