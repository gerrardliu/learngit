package main

import (
	"fmt"
	"regexp"
)

func main() {
	//text := "Hello 世界! 123 Go."
	//text := "世界公园"
	text := "秦叔宝"
	//reg := regexp.MustCompile("[a-z]+")
	reg := regexp.MustCompile(`^[\p{Han}]{1,3}$`)
	fmt.Printf("%q\n", reg.FindAllString(text, -1))
	fmt.Println(reg.MatchString(text))
}
