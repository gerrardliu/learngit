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

func heapAdjust(a []int, s int, l int) {
	rc := a[s]
	for j := 2*s + 1; j < l; j = 2*s + 1 {
		if j+1 < l && a[j] < a[j+1] {
			j++
		}
		if rc >= a[j] {
			break
		}
		a[s] = a[j]
		s = j
	}
	a[s] = rc
}

func heapSort(a []int) {
	for i := len(a)/2 - 1; i >= 0; i-- {
		heapAdjust(a, i, len(a))
	}
	for i := len(a) - 1; i > 0; i-- {
		a[0], a[i] = a[i], a[0]
		heapAdjust(a, 0, i)
	}
}

func printArray(a []int) {
	for _, n := range a {
		fmt.Printf("%d ", n)
	}
	fmt.Println()
}

func GenRandArray(num int) ([]int, []int, []int) {
	a := []int{}
	b := []int{}
	c := []int{}
	if num > 0 {
		r := rand.New(rand.NewSource(time.Now().UnixNano()))
		for i := 0; i < num; i++ {
			n := r.Intn(100000)
			a = append(a, n)
			b = append(b, n)
			c = append(c, n)
		}
	}
	return a, b, c
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
	//SortBySomething(nums, heapSort)
	nums, nums2, nums3 := GenRandArray(100000)
	SortBySomething(nums, bubbleSort)
	SortBySomething(nums2, heapSort)
	SortBySomething(nums3, quickSort)
	ioutil.WriteFile("nums.txt", []byte(fmt.Sprintf("%v", nums)), 0644)
	ioutil.WriteFile("nums2.txt", []byte(fmt.Sprintf("%v", nums2)), 0644)
	ioutil.WriteFile("nums3.txt", []byte(fmt.Sprintf("%v", nums3)), 0644)
}
