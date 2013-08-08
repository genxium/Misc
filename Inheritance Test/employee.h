#ifndef EMPLOYEE_H
#define EMPLOYEE_H

#include <string>
using namespace std;

class Employee {
public:
  Employee(string theName, float thePayRate);

  string getName() const;
  float getPayRate() const;

  float pay(float hoursWorked) const;

protected:
  string name;
  float payRate;
};

#endif