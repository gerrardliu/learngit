#include <array>
#include <iostream>
#include "../random/gen_random.h"

const int n = 100;

void PrintArray(const std::array<int,n>& arr) 
{
	for(auto i=0; i<(int)arr.size(); i++) {
		std::cout << arr[i] << " ";
	}
	std::cout << std::endl;
}

void QSort(std::array<int,n>& arr, int left, int right) 
{
	if( left >= right ) {
		return;
	}

	int i = left;
	int j = right;

	int k = arr[i];
	while(i < j) {
		while(i<j && arr[j]>=k)	j--;
		arr[i] = arr[j];
		while(i<j && arr[i]<=k) i++;
		arr[j] = arr[i];
	}
	arr[i] = k;

	QSort(arr, left, i-1);
	QSort(arr, i+1, right);
}

void QuickSort(std::array<int,n>& arr)
{
	if(arr.size() > 1) {
		QSort(arr, 0, arr.size()-1);
	}
}

int main()
{
	std::array<int,n> arr;
	for(int i=0; i<n; i++) {
		arr[i] = GenRandomNum(0,100);
	}
	PrintArray(arr);
	QuickSort(arr);
	PrintArray(arr);
	return 0;
}
