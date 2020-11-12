package main

import "fmt"
import "github.com/flosch/pongo2/v4"

func main() {
	fmt.Println("vim-go")
	tpl, err := pongo2.FromString("Hello {{ name|capfirst }}!")
	if err != nil {
		panic(err)
	}

	out, err := tpl.Execute(pongo2.Context{"name": "gerr"})
	if err != nil {
		panic(err)
	}

	fmt.Println(out)
}
