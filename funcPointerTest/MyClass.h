//
//  MyClass.h
//  funcPointerTest
//
//  Created by Wing on 5/8/13.
//  Copyright (c) 2013 Wing. All rights reserved.
//

#ifndef funcPointerTest_MyClass_h
#define funcPointerTest_MyClass_h

#include <string>
using namespace std;

class MyClass{
public:
	MyClass();
	virtual ~MyClass();
public:
	bool init(void (*pFunc)(string messageToPass));
	void useFunc(string messageToPass);
    
private:
	void (*m_pFunc)(string messageToPass);
};

#endif