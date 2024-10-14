#include <iostream>
#include <string>
#include <User.h>


using namespace std;

int main()
{
    string userID = "";
    string password = password;
    bool logOut = 0;
    User user1;

    while(logOut!=1){
        bool loggedIn=0;
        if(loggedIn=0){
            cout << "Welcome to Hospital Management System (HMS)!" << endl;
            cout << "Please enter User ID: " << endl;
            cin >> userID;
            cout << "Please enter password: " << endl;
            cin >> password;
            if(user1.verifyLogin(userID,password)){
                cout << "Successful login";
                //Check if user logged in for the first time
                cout << "Do you want to change your password?";
            }
        }
    }


    return 0;
}
