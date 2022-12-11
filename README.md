# Welcome to Purple team’s 332 project

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
![MapReduce](Weekly%20Progress%206b31ccb2ff7c415b87200bb912974191/1.png)
worker
![MapReduce](Weekly%20Progress%206b31ccb2ff7c415b87200bb912974191/2.png)

vm09. 2.2.2.109:22
![MapReduce](Weekly%20Progress%206b31ccb2ff7c415b87200bb912974191/3.png)


vm01. 2.2.2.101:22
![MapReduce](Weekly%20Progress%206b31ccb2ff7c415b87200bb912974191/4.png)

vm10. 2.2.2.110:22
