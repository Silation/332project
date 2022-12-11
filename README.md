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

