#include <iostream>
#include "gen_random.h"

int main()
{
	for(int i=0; i<100; i++) {
		std::cout << GenRandomNum(0,100) << std::endl;
	}
	return 0;
}
