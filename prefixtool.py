import os
import re
import sys

def add_mark():
    pre = input("请输入需要添加的前缀:")
    mark = "%s_"%pre
    old_names= os.listdir()
    for old_name in old_names:
        if old_name != sys.argv[0]:
            os.rename(old_name, mark+old_name)

def remove_mark():
    old_names= os.listdir()
    for old_name in old_names:
        try:
            result = re.match(r"(^\[.*\])(.*)", old_name).group(2)
            rm = old_name

            if result:
                os.rename(old_name, result)
            print("已为%s移除前缀"%rm)
        except Exception as e:
            pass

def main():
    while True:
        option = int(input("请选择功能数值:\n1.添加前缀\n2.删除前缀\n3.退出程序\n"))
        if option == 1:
            add_mark()
        elif option == 2:
            remove_mark()
        else:
            exit()

if __name__ == "__main__":
    main()
