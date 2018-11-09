package main

import "fmt"
import "unicode/utf8"

func main() {
	str := "hello,世界"
	rstr := []rune(str)
	fmt.Printf("%s,%d,%d,%d\n", str, len(str), len(rstr), utf8.RuneCountInString(str))
	for _, r := range rstr {
		fmt.Println(r)
	}
}
