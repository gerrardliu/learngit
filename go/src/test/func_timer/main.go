package main

import "test/func_timer/util"

func main() {
	defer util.FuncTimer("main")()
}
