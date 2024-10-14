#ifndef USER_H
#define USER_H
#include <iostream>
#include <string>


using namespace std;

class User
{
    private:
        string hospitalID;
        string password;
        string role;
        bool initialLogin;

    public:
        User();
        User(string hospitalID, string password, string role);
        virtual ~User();
        bool verifyLogin(string hospitalID, string password);
        string getHospitalID() const;
        void setHospitalID(string newHospitalID);
        void setPassword(string newPassword);

    protected:
        string getPassword() const;


};

#endif // USER_H
