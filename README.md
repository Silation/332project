Welcome to Purple team’s 332 project

# How to build

```
git clone [https://github.com/Silation/332project.git](https://github.com/Silation/332project.git)
cd 332project/server
sbt compile
```

# How to run

## Master

```
sbt "run master <# of workers>"
ex) sbt "run master 3"
```

## Worker

존재하는 output directory만 실행 가능합니다.

input, ouput file path는 file 이름을 포함해서 실행해 주세요.

```
sbt "run slave 2.2.2.111:50052 -I <input data relative path> <input data relative path> ... <input data relative path> -O <output data relative path>"
ex) sbt "run slave 2.2.2.111:50052 -I ../data/data1/test_input.txt ../data/data2/test_input.txt -O /data/output/test_ouput.txt"
```

# Result

master(vm11. 2.2.2.111:50052)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f8fb7163-a2f5-4523-a276-72f8f1418be1/Untitled.png)

worker

vm09. 2.2.2.109:22

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f8e52b48-eee6-4a41-a594-df3ed55581be/Untitled.png)

vm01. 2.2.2.101:22

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d9c63f57-361f-4288-8d70-53fec3f4676c/Untitled.png)

vm10. 2.2.2.110:22

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2b2d4c85-4476-466c-9924-dae80b9b7ba2/Untitled.png)
