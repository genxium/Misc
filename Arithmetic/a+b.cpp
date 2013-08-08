#include <iostream>
using namespace std;

int add(int a,int b){
	if(b==0) return a;
	int c=(a^b);
	int up=((a&b)<<1);
	return add(c,up);
}

int main(){
	int a,b;
	while(cin>>a>>b){
		int ans=add(a,b);
		cout<<ans<<endl;
	}
	return 0;
}

