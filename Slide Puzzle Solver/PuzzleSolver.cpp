#include <iostream>
#include <string>
#include <map>
#include <set>
#include <queue>
#include <algorithm>
using namespace std;

string reverseStr(string str)
{
//    cout<<"String to reverse is: "<<str<<endl;
    int middle=(str.size()>>1);
//    cout<<"middle = "<<middle<<endl;
    for(int i=0;i<middle;++i){
//        cout<<"i = "<<i<<endl;
        swap(str[i],str[str.size()-1-i]);
    }
    return str;
}

string solvePuzzle(string input, int rows, int cols)
{
    // Algorithm: levelly traversing
    
    map<string,string> cache[2];
    queue<string> q[2];
    
    string instructions[2][4]={{"u","d","l","r"},{"d","u","r","l"}};
   
    int lv=1; // root is in level 1
    string target = input; sort(target.begin(), target.end());
    
    q[0].push(input); cache[0][input]=""; // no instruction is needed for the first status
    q[1].push(target); cache[1][target]=""; // note that the instructions in backwardCache should be reversed before returning
    
//    cout<<"input: "<<input<<endl;
//    cout<<"target: "<<target<<endl;
    
    string prefix="";
    
    while(!q[0].empty()&&!q[1].empty()){
        int me,it;
//        cout<<"forward cache size "<< cache[0].size() <<endl;
//        cout<<"backward cache size "<< cache[1].size() <<endl;
        me=q[0].size()<q[1].size()?0:1; // shorter queue
        it=1-me; // longer queue
        
        size_t count=q[me].size();
        
        while(count--){
            
            string now = q[me].front(); q[me].pop();
//            if(me==0){
//                cout<<prefix<<"Get "<<now<<" from forward queue"<<endl;
//            }
//            else{
//                cout<<prefix<<"Get "<<now<<" from backward queue"<<endl;
//            }
            string nowInstructions = cache[me][now];
            // find the position of X, actually this can be reduced by storing the position of X into queue element
            int posX=-1;
            for(posX=0;posX<now.size();++posX){
                if(now[posX]=='x') break;
            }
            int tRow = posX/cols, tCol=posX%cols;
            
            vector<int> directions;
            if(tRow-1>=0) directions.push_back(posX-cols); // instruction is 'd'
            else directions.push_back(-1);
            if(tRow+1<rows) directions.push_back(posX+cols); // instruction is 'u'
            else directions.push_back(-1);
            if(tCol-1>=0) directions.push_back(posX-1); // instruction is 'r'
            else directions.push_back(-1);
            if(tCol+1<cols) directions.push_back(posX+1); // instruction is 'l'
            else directions.push_back(-1);
            
            for(int i=0;i<directions.size();++i){
                if(directions[i]==-1) continue;
                string candidate=now;
                swap(candidate[directions[i]],candidate[posX]);
                
                if(cache[it].find(candidate)!=cache[it].end()){
                    // target found
                    string foo = cache[me][now]+instructions[me][i];
                    string loo = cache[it][candidate];
                    
                    if(me==0){
//                        cout<<"Step "<<lv<<":Visiting forward cache, "<<candidate<<" is found in the backward cache"<<endl;
//                        cout<<"Forward instructions: "<<foo<<endl;
//                        cout<<"Reversed backward instructions: "<<loo<<endl;
                        return foo+reverseStr(loo);
                    }
                    else{
//                        cout<<"Step "<<lv<<":Visiting backward cache, "<<candidate<<" is found in the forward cache"<<endl;
//                        cout<<"Forward instructions: "<<loo<<endl;
//                        cout<<"Reversed backward instructions: "<<foo<<endl;
                        return loo+reverseStr(foo);
                    }
                }
                else if(cache[me].find(candidate)==cache[me].end()){
                    // candidate has not been cached yet
                    cache[me][candidate]=nowInstructions+instructions[me][i];
                    q[me].push(candidate);
                }
                else;
            }
        }
        ++lv;
        prefix+="   ";
    }
    return "unsolvable";
}

int main()
{
//    string inputPuzzle = "1234567x8";
//    string inputPuzzle = "23415x768";
    string inputPuzzle;
    int rows=3, cols=3;
    for(int i=0;i<9;++i){ char c; cin>>c; inputPuzzle.push_back(c);}
    
    string steps=solvePuzzle(inputPuzzle, rows, cols);
    
    cout<<steps<<endl;
    return 0;
}

