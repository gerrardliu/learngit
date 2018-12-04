package main

import "fmt"
import "encoding/json"

type Department struct {
	DepartmentName     string `json:"departmentName"`
	DepartmentId       string `json:"departmentId"`
	ParentDepartmentId string `json:"parentDepartmentId"`
	DepartmentType     int32  `json:"departmentType"`
	DepartmentUserType int32  `json:"departmentUserType"`
}

type CompanyUserInfo struct {
	ChineseName    string        `json:"chineseName"`
	EnglishName    string        `json:"englishName"`
	PhotoUrl       string        `json:"photoUrl"`
	PhoneNumber    string        `json:"phoneNumber"`
	Email          string        `json:"email"`
	CompanyName    string        `json:"companyName"`
	CreateType     int32         `json:"createType"`
	UserType       int32         `json:"userType"`
	DepartmentList []*Department `json:"departmentList"`
}

type Data struct {
	CompanyUserList []*CompanyUserInfo `json:"companyUserList"`
}

type CompanyUserInfoListResp struct {
	Status  int32  `json:"status"`
	Message string `json:"message"`
	Data    Data   `json:"data"`
}

func main() {
	jsonstr := `{
    "status": 0,
    "message": "success",
    "data": {
        "companyUserList":[{
            "chineseName":"尉迟恭",
            "englishName":"ChiGong Yu",
            "photoUrl":"xxxx",
            "phoneNumber":"xxxx",
            "email":"xxxx",
            "companyName":"xxxx",
            "createType":1,
            "userType":1,
            "departmentList":[{
                "departmentName":"xxxx",
                "departmentId":"xxxx",
                "parentDepartmentId":"xxxx",
                "departmentType":1,
                "departmentUserType":1
            }]
        }]
    }
}`
	fmt.Println(jsonstr)
	resp := &CompanyUserInfoListResp{}
	err := json.Unmarshal([]byte(jsonstr), resp)
	if err != nil {
		fmt.Printf("err=%s\n", err.Error())
		return
	}
	fmt.Println(resp)
	fmt.Printf("status=%d\n", resp.Status)
	fmt.Printf("message=%s\n", resp.Message)
	fmt.Printf("data=%v\n", resp.Data)
	for i, info := range resp.Data.CompanyUserList {
		fmt.Printf("no.%d\n", i)
		fmt.Printf("info.ChineseName=%s\n", info.ChineseName)
		fmt.Printf("info.EnglishName=%s\n", info.EnglishName)
		for _, dept := range info.DepartmentList {
			fmt.Printf("deptName=%s\n", dept.DepartmentName)
		}
	}
}
