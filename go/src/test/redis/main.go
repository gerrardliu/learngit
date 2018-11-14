package main

import (
	"fmt"
	"github.com/go-redis/redis"
	"strconv"
)

func GetInboxCacheKey(companyId, uid string) string {
	return fmt.Sprintf("feed:inbox:%s:%s", companyId, uid)
}

func main() {
	client := redis.NewUniversalClient(&redis.UniversalOptions{
		Addrs: []string{"10.30.60.40:6379"},
	})
	defer client.Close()
	if _, err := client.Ping().Result(); err != nil {
		fmt.Printf("redis failed:%s\n", err.Error())
		return
	}
	cacheKey := GetInboxCacheKey("1001", "33824731634602436")
	list, err := client.LRange(cacheKey, 0, 10).Result()
	if err != nil {
		fmt.Printf("client.LRange failed:%s", err.Error())
		return
	}
	fmt.Printf("%v\n", list)
	arr := []uint64{}
	for _, v := range list {
		v1, _ := strconv.ParseUint(v, 10, 64)
		arr = append(arr, v1)
	}
}
