#include <string>
#include <chrono>
#include <iostream>

int main()
{
	using namespace std::chrono;
	high_resolution_clock::time_point t1 = high_resolution_clock::now();
	std::cout << "print 1000 starts" << std::endl;
	for(int i=0; i<1000; i++) {
		std::cout << "*";
	}
	std::cout << std::endl;
	high_resolution_clock::time_point t2 = high_resolution_clock::now();

	std::cout << "in second" << std::endl;
	duration<double,std::ratio<1,1>> duration_s(t2-t1);
	std::cout << duration_s.count() << std::endl;

	std::cout << "in mili-second" << std::endl;
	duration<double,std::ratio<1,1000>> duration_ms(t2-t1);
	std::cout << duration_ms.count() << std::endl;

	std::cout << "in micro-second" << std::endl;
	duration<double,std::ratio<1,1000000>> duration_mis(t2-t1);
	std::cout << duration_mis.count() << std::endl;

	return 0;
}
