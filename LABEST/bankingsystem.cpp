#include <bits/stdc++.h>
using namespace std;

//generate baking system using single responsiblity principle
class Bank{
public:
    string name;
    string accountnumber;
    long long balance;
 
    Bank() = default;
    Bank(string name, string accountnumber, long long balance){
        this->name = name;
        this->accountnumber = accountnumber;
        this->balance = balance;
    }
};
vector<Bank> banks;
unordered_map<string, Bank> bankMap;
class adduser{
    public:
    void addUser(string name, string accountnumber, long long balance){
        Bank bank=Bank(name, accountnumber, balance);
        banks.push_back(bank);
        bankMap[accountnumber] = bank;
    }
};
class Addmoney{

    public:
    void addMoney(string accountnumber, long long amount){
        if(bankMap.find(accountnumber) != bankMap.end()){
            for(auto &it : banks){
                if(it.accountnumber == accountnumber){
                    it.balance += amount;
                    bankMap[accountnumber].balance += amount;
                    cout << "Money added successfully" << endl;
                    return;
                }
            }
        }
        cout << "Account not found" << endl;
    }

 
    
};
class Withdrawmoney{
    public:
    void withdrawMoney(string accountnumber, long long amount){
        if(bankMap.find(accountnumber) != bankMap.end()){
            for(auto &bank : banks){
                if(bank.accountnumber == accountnumber){
                    if(bank.balance >= amount){
                        bank.balance -= amount;
                        bankMap[accountnumber].balance -= amount;
                        cout << "Money withdrawn successfully" << endl;
                        return;
                    }
                    else{
                        cout << "Insufficient balance" << endl;
                        return;
                    }
                }
            }
        }
        cout << "Account not found" << endl;
    }
};
class Viewbalance{
    public:
    void viewBalance(string accountnumber){
        if(bankMap.find(accountnumber) != bankMap.end()){
            cout << "Balance: " << bankMap[accountnumber].balance << endl;
        } else {
            cout << "Account not found" << endl;
        }
    }
};
int main(){
    adduser adduser;
    Addmoney addmoney;
    Withdrawmoney withdrawmoney;
    Viewbalance viewbalance;

    adduser.addUser("John Doe", "1234567890", 1000);
    addmoney.addMoney("1234567890", 500);
    viewbalance.viewBalance("1234567890");
}

