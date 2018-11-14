package util

import (
	"math/rand"
	"time"
)

func GenRandIntArray(num, max int) []int {
	a := []int{}
	if num > 0 {
		r := rand.New(rand.NewSource(time.Now().UnixNano()))
		for i := 0; i < num; i++ {
			n := r.Intn(max)
			a = append(a, n)
		}
	}
	return a
}

func GenRandUInt64Array(num int, max uint64) []uint64 {
	a := []uint64{}
	if num > 0 {
		r := rand.New(rand.NewSource(time.Now().UnixNano()))
		for i := 0; i < num; i++ {
			n := r.Uint64() % max
			a = append(a, n)
		}
	}
	return a
}
