package main

import "fmt"

func MyPrint(args ...interface{}) {
	for _, arg := range args {
		switch arg.(type) {
		case int:
			fmt.Println(arg, "is int")
		case string:
			fmt.Println(arg, "is string")
		case int64:
			fmt.Println(arg, "is int64")
		default:
			fmt.Println(arg, "not known")
		}
	}
}

func main() {
	var v1 int = 1
	var v2 int64 = 234
	var v3 string = "hello"
	var v4 float32 = 1.234

	MyPrint(v1, v2, v3, v4)
}
