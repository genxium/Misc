//
//  main.cpp
//  funcPointerTest
//
//  Created by Wing on 5/8/13.
//  Copyright (c) 2013 Wing. All rights reserved.
//

#include <iostream>
#include <string>
#include "MyClass.h"
using namespace std;

void printHello(string messageFromCaller){
	cout<<messageFromCaller<<endl;
}

int main(){
	MyClass *instance = new MyClass();
	instance->init(&printHello);
	instance->useFunc("Hello my caller!");
	return 0;
}

