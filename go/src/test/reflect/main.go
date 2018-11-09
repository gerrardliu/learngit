package main

import (
	"fmt"
	"reflect"
)

type Bird struct {
	Name string
	Age  int
}

func (bird *Bird) Fly() {
	fmt.Println("flying..")
}

func main() {
	sparrow := &Bird{"sparrow", 3}
	s := reflect.ValueOf(sparrow).Elem()
	t := s.Type()
	for i := 0; i < s.NumField(); i++ {
		f := s.Field(i)
		fmt.Printf("%d %s %s=%v\n", i, t.Field(i).Name, f.Type(), f.Interface())
	}
}
