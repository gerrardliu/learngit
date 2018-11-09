package main

import "fmt"

func sum(arr []int, resChan chan int) {
	n := 0
	for _, v := range arr {
		n += v
	}
	resChan <- n
}

func main() {
	arr := []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
	resChan := make(chan int, 2)
	go sum(arr[:len(arr)/2], resChan)
	go sum(arr[len(arr)/2:], resChan)
	res1, res2 := <-resChan, <-resChan
	fmt.Printf("%d+%d=%d\n", res1, res2, res1+res2)
}
