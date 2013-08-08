#include "employee.h"

Employee::Employee(string theName, float thePayRate)
{
  name = theName;
  payRate = thePayRate;
}

string Employee::getName() const
{
  return name;
}

float Employee::getPayRate() const
{
  return payRate;
}

float Employee::pay(float hoursWorked) const
{
  return hoursWorked * payRate;
}