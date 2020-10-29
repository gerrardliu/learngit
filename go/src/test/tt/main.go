package main

import "fmt"
import "os"
import "strconv"
import "time"

func main() {
	fmt.Println("timestamp to datetime")
	if len(os.Args) != 2 {
		fmt.Println("usage: ./tt timestamp")
		os.Exit(0)
	}
	t, _ := strconv.ParseInt(os.Args[1], 10, 64)
	fmt.Println(t)
	fmt.Println(time.Unix(t, 0))
	fmt.Println(time.Unix(t, 0).UTC())
}
