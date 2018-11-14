package main

import "fmt"
import "test/random/util"

func main() {
	arr := util.GenRandIntArray(5, 10)
	fmt.Println(arr)
}
