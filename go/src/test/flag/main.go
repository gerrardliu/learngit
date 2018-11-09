package main

import (
	"flag"
	"fmt"
	"os"
)

var (
	version bool
	conf    string
)

func init() {
	flag.BoolVar(&version, "v", false, "show version of this service")
	flag.StringVar(&conf, "c", "cccc", "config path")
}

func main() {
	fmt.Println(os.Args)
	if len(os.Args) < 2 {
		flag.Usage()
		os.Exit(0)
	}
	flag.Parse()
	if version {
		fmt.Println("VER=V0.0.1")
	}
	fmt.Printf("conf=%s\n", conf)
	fmt.Println("done")
}
