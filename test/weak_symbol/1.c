#include <stdio.h>
int a;
int a;
int main()
{
a = 1;
printf("a=%d\n",a);
int* p = &a;
printf("a1=%d a2=%d\n", *(p-1), *(p+1));
return 0;
}
