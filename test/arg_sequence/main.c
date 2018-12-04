#include <stdio.h>

//http://www.cnblogs.com/easonliu/p/4224120.html

int main()
{
	int a = 10;
	//printf("%d %d %d\n", a++, ++a, a);
	printf("%d %d %d %d\n", a++, ++a, a, a++);
	return 0;
}
