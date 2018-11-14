package main

import (
	"fmt"
	"sort"
	"test/random/util"
)

type PostIdArr []uint64

func (m PostIdArr) Swap(i, j int) {
	m[i], m[j] = m[j], m[i]
}

func (m PostIdArr) Len() int {
	return len(m)
}

func (m PostIdArr) Less(i, j int) bool {
	return m[i] > m[j]
}

func (m PostIdArr) Uniq() (a PostIdArr) {
	for i := 0; i < len(m); i++ {
		if len(a) > 0 {
			if m[i] == a[len(a)-1] {
				continue
			}
		}
		a = append(a, m[i])
	}
	return a
}

func main() {
	//arr := PostIdArr{0, 2, 4, 6, 8}
	arr := PostIdArr(util.GenRandUInt64Array(5, 10))
	fmt.Println(arr)
	fmt.Println(sort.IsSorted(arr))
	sort.Sort(arr)
	arr = arr.Uniq()
	fmt.Println(arr)
	fmt.Println(sort.IsSorted(arr))
	pos := sort.Search(len(arr), func(i int) bool {
		return arr[i] <= 5
	})
	fmt.Println(pos)
}
