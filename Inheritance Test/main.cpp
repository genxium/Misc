#include "employee.h"
#include <iostream>
using namespace std;


int main(){
	Employee obj("genxium",100.0);
	string name=obj.getName();
	float wage=obj.getPayRate();
	cout<<name<<"\t"<<wage<<endl;
	return 0;
}