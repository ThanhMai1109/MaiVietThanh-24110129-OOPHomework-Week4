#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Vehicle // Class me 
{
protected:
    string route;
    int capacity;
    bool status;
    int bookseat;

public:
    Vehicle(string r, int c) : route(r), capacity(c), status(true), bookseat(0) {}
    virtual double calculateTravelTime(double distance)
    {
        return distance / 40.0; // Default speed 40 km/h
    }
    string getRoute() { return route; }
    int getCapacity() { return capacity; }
    bool getStatus() { return status; }
    int getBookedSeats() { return bookseat; }
    bool bookSeat(int seats=1)
    {
        if (bookseat + seats <= capacity) {
            bookseat += seats;
            return true;
        } else {
            cout << "Vehicle " << route << " is full!\n";
            return false;
        }
    }
    void cancelSeat(int seats = 1)
    {
        if (bookseat - seats >= 0) bookseat -= seats;
    }
};

class ExpressBus : public Vehicle // Class con ke thua Vehicle
{
private:
    double speed;
public:
    ExpressBus(string r, int c, double s) : Vehicle(r, c), speed(s) {}
    double calculateTravelTime(double distance) override { 
        return (distance / speed)*0.8; // Express bus chay nhanh hon 20%
     }
};

class Station // Class quan ly tram
{
protected:
    string name;
    string location;
    vector<Vehicle*> vehicles;
public:
    Station(string n, string loc) : name(n), location(loc) {}
    void addVehicle(Vehicle* v) { vehicles.push_back(v); }
    void displayVehicles() {
        cout << "Vehicles at " << name << ":\n";
        for (const auto& v : vehicles) {
            cout << "- Route: " << v->getRoute() << ", Capacity: " << v->getCapacity() << "\n";
        }
    }

};

class BusStation : public Station  // Class con ke thua Station
{
private:
    vector<ExpressBus*> expressBuses; 
public:
    BusStation(string n, string loc) : Station(n, loc) {}
    void addExpressBus(ExpressBus* eb) { expressBuses.push_back(eb); addVehicle(eb); } 
    void displayExpressBuses() {
        cout << "Express Buses at " << name << ":\n";
        for (const auto& eb : expressBuses) {
            cout << "- Route: " << eb->getRoute() << ", Capacity: " << eb->getCapacity() << "\n";
        }
    }
};

class Passenger
{
private:
    string name;
    int id;
    vector<Vehicle*> bookedVehicles;
public:
    Passenger(string n, int i) : name(n), id(i) {}
    void bookVehicle(Vehicle* v) {
        if (v->bookSeat()) {
            bookedVehicles.push_back(v);
            cout << name << " booked a seat on " << v->getRoute() << endl;
        }
    }
    void cancelBooking(Vehicle* v) {
        for(auto it = bookedVehicles.begin(); it != bookedVehicles.end(); ++it) {
            if (*it == v) {
                v->cancelSeat();
                bookedVehicles.erase(it);
                cout << name << " canceled booking on " << v->getRoute() << endl;
                return;
            }
        }
        cout << name << " not booking on this " << v->getRoute() << " before "<< endl;
    }
};

class Schedule // Lich trinh
{   
private:
    string time;
    string type;
    Vehicle* vehicle;
public:
    Schedule(string t, string ty, Vehicle* v) : time(t), type(ty), vehicle(v) {}
    void displaySchedule() {
        cout << type << " at " << time << "\n Vehicle: " <<  vehicle->getRoute() << "\n Booked: " << vehicle->getBookedSeats() << "/" << vehicle->getCapacity() << "\n";

    }
};

int main() // ChatGPT tao main cac loai testcase
{
    // Tạo 1 trạm xe buýt
    BusStation bs("Central Bus Station", "Downtown");

    // Tạo phương tiện
    Vehicle v1("Bus 101", 3);
    ExpressBus eb1("Express 201", 2, 60.0);

    // Thêm phương tiện vào trạm
    bs.addVehicle(&v1);
    bs.addExpressBus(&eb1);

    // Hiển thị danh sách xe
    bs.displayVehicles();
    bs.displayExpressBuses();

    // Tạo hành khách
    Passenger p1("Alice", 1);
    Passenger p2("Bob", 2);

    // Đặt vé
    p1.bookVehicle(&v1);
    p2.bookVehicle(&v1);
    p1.bookVehicle(&v1);
    p2.bookVehicle(&v1); // sẽ báo full

    // Hủy vé
    p1.cancelBooking(&v1);

    // Lịch trình
    Schedule s1("08:00", "Departure", &v1);
    Schedule s2("09:30", "Arrival", &eb1);
    s1.displaySchedule();
    s2.displaySchedule();

    // Test travel time
    cout << "⏱ Travel time Bus101 (100km): " << v1.calculateTravelTime(100) << "h\n";
    cout << "⏱ Travel time Express201 (100km): " << eb1.calculateTravelTime(100) << "h\n";

     cout << "\n=== TEST CASES ===\n";

    // Testcase 1: Đặt full chỗ Express Bus
    cout << "\n[TC1] Book Express Bus until full:\n";
    Passenger p3("Charlie", 3);
    Passenger p4("Daisy", 4);
    p3.bookVehicle(&eb1);
    p4.bookVehicle(&eb1);
    p3.bookVehicle(&eb1); // sẽ báo full

    // Testcase 2: Hủy vé không tồn tại
    cout << "\n[TC2] Cancel booking that doesn't exist:\n";
    p4.cancelBooking(&v1); // Daisy chưa từng đặt Bus 101

    // Testcase 3: Một hành khách đặt vé trên nhiều phương tiện
    cout << "\n[TC3] Passenger books multiple vehicles:\n";
    p1.bookVehicle(&eb1);
    p1.bookVehicle(&v1);

    // Testcase 4: Nhiều lịch trình cho cùng một xe
    cout << "\n[TC4] Multiple schedules for same vehicle:\n";
    Schedule s3("11:00", "Departure", &eb1);
    Schedule s4("12:30", "Arrival", &eb1);
    s3.displaySchedule();
    s4.displaySchedule();

    // Testcase 5: Thử travel time với nhiều khoảng cách
    cout << "\n[TC5] Travel time with different distances:\n";
    cout << "Bus101 (50km): " << v1.calculateTravelTime(50) << "h\n";
    cout << "Bus101 (200km): " << v1.calculateTravelTime(200) << "h\n";
    cout << "Express201 (50km): " << eb1.calculateTravelTime(50) << "h\n";
    cout << "Express201 (200km): " << eb1.calculateTravelTime(200) << "h\n";

    return 0;
}