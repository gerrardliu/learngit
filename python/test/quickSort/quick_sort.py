def quickSort(nums):
    if len(nums) < 2:
        return nums
    flag = nums[0]
    left = [i for i in nums[1:] if i <= flag]
    right = [i for i in nums[1:] if i > flag]
    return quickSort(left) + [flag] + quickSort(right)

arr=[10,47,98,7,82,34,64,2,109,65]
print(quickSort(arr))