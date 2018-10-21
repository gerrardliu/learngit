package main

import (
	"fmt"
	"io/ioutil"
	"math/rand"
	"time"
)

func qsort(a []int, left, right int) {
	i, j := left, right
	if i >= j {
		return
	}
	k := a[i]
	for i < j {
		for j > i && a[j] >= k {
			j--
		}
		a[i] = a[j]
		for i < j && a[i] <= k {
			i++
		}
		a[j] = a[i]
	}
	a[i] = k
	qsort(a, left, i-1)
	qsort(a, i+1, right)
}

func quickSort(a []int) {
	if len(a) > 1 {
		qsort(a, 0, len(a)-1)
	}
}

func bubbleSort(a []int) {
	for i := 0; i < len(a)-1; i++ {
		for j := 0; j < len(a)-i-1; j++ {
			if a[j] > a[j+1] {
				a[j], a[j+1] = a[j+1], a[j]
			}
		}
	}
}

func printArray(a []int) {
	for _, n := range a {
		fmt.Printf("%d ", n)
	}
	fmt.Println()
}

func GenRandArray(num int) ([]int, []int) {
	a := []int{}
	b := []int{}
	if num > 0 {
		r := rand.New(rand.NewSource(time.Now().UnixNano()))
		for i := 0; i < num; i++ {
			n := r.Intn(100000)
			a = append(a, n)
			b = append(b, n)
		}
	}
	return a, b
}

func SortBySomething(a []int, f func(arr []int)) {
	//printArray(a)
	t := time.Now()
	f(a)
	fmt.Println("elasped ", time.Since(t))
	//printArray(a)
}

func main() {
	//nums := []int{49, 38, 65, 97, 76, 13, 27}
	//SortBySomething(nums, quickSort)
	nums, nums2 := GenRandArray(100000)
	SortBySomething(nums, bubbleSort)
	SortBySomething(nums2, quickSort)
	ioutil.WriteFile("nums.txt", []byte(fmt.Sprintf("%v", nums)), 0644)
	ioutil.WriteFile("nums2.txt", []byte(fmt.Sprintf("%v", nums2)), 0644)
}
