package util

import (
	"fmt"
	"time"
)

func FuncTimer(name string) func() {
	t := time.Now()
	return func() {
		fmt.Printf("%s finished (elasped %s)\n", name, time.Since(t))
	}
}
