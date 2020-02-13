package main

import (
	"bufio"
	"fmt"
	"net"
)

func handle(conn net.Conn){
	defer conn.Close()
	input := bufio.NewScanner(conn)
	for input.Scan(){
		fmt.Fprintln(conn, "Hello " + input.Text())
	}
}
func main(){
	listen, err := net.Listen("tcp", "localhost:8888")
	if err != nil{
		panic(err)
	}
	defer listen.Close()

	for {
		conn, err := listen.Accept()
		if err != nil{
			panic(err)
		}
		go handle(conn)
	}
}

