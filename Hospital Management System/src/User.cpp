#include "User.h"
#include <iostream>
#include <string>


using namespace std;

User::User(){};

User::User(string hospitalID, string password, string role) : hospitalID(hospitalID),password(password),role(role)
{
    //ctor
}

User::~User
{
    //dtor
}

bool User::verifyLogin(string hospitalID, string password){
    if(this.hospitalID == hospitalID && this.password == password){
        return true;
    }
    return false;
}

string User::getHospitalID() const{
    return hospitalID;
}

void User::setHospitalID(string newHospitalID){
    this.hospitalID = newHospitalID;
}

void User::setHospitalID(string newPassword){
    this.password = newPassword;
}

string User::getRole() const{
    return role;
}
