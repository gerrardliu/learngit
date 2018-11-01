#include <random>
#include "gen_random.h"

int GenRandomNum(int min, int max)
{
	std::random_device rd;
	std::uniform_int_distribution<int> dist(min, max);
	return dist(rd);
}
