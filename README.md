# 332project -  Weekly Progress
(11/16): Progress slides deadline  
(12/09): Project deadline

<br/>  


## **Week 1**  
---

**Progress of Week 1**  

- 첫 팀 미팅을 가져 앞으로의 미팅 계획, 협업 등을 논의했다. Git Repo를 개설했다.
- 주차별로 대략적인 계획을 세웠다.
- 프로젝트를 시작하기에 앞서 배경지식으로 숙지가 필요한 여러 분야들을 선정했고, 공부해 온 후 공유/ 프로젝트 계획을 구체화 하기로 하였다.

| week | Due | To Do. |
| --- | --- | --- |
| week1 | 10/21 | (Planning) team building (미팅 시간 잡기, 계획 논의) |
| week2 | 10/28 | (Planning) 개념 스터디 및 공유 |
| week3 | 11/4 | (Planning) 구체적 코딩 계획 확정 |
| week4 | 11/11 | (Coding) 기본 sorting / Challenge 1 |
| week5 | 11/18
*(11/16)중간 발표 | (Coding) Challenge 2 |
| week6 | 11/25 | (Component test) Debug + Performance Enhancing 1 |
| week7 | 12/2 | (Program test) Debug + Performance Enhancing 2 |
| week8 | 12/9 | Performance Enhancing3 + 발표 준비 |
|  | 12/13, 12/15 | 최종 발표 |

<br/>  

**Goal of Week 1**

- 다음 미팅 때 프로젝트의 전반적 뼈대와 계획을 논의할 수 있을 만큼, 그 전까지 필요한 배경 지식들, 자료들을 수집하고 학습한다.

<br/>  

**Goal of the week for individual**

- 조은국, 김은채, 이창우 (공통):
    
    프로젝트에 필요한 자료들을 공유, 학습해오고, 다음 미팅때 각자 공부한 부분 설명하기
    
    ex) Parallel Computing with Scala, gRPC, Gensort …
    

<br/>

## **Week 2**

---

**Progress of Week 2**

- 수행할 과제에서 Sampling, Sort, Partition, Shuffle, Merge의 개념을 이해하고 Hadoop Map reduce 시스템의 Splitting, Mapping, Shuffling, Reducing과 비교하였다.
    - MapReduce는 Map, Reduce의 phase로 구성
    - Map과 Reduce 사이에는 shuffle과 sort라는 스테이지 존재
    - 각 Map task는 전체 데이터 셋에 대해서 별개의 부분에 대한 작업을 수행(기본적으로 하나의 HDFS block 대상)
    - 모든 Map 태스크가 종료되면, intermediate 데이터를 Reduce phase를 수행할 노드로 분산하여 전송
    - Distributed File System에서 수행되는 MapReduce 작업이 끝나면 HDFS에 파일이 써짐
    - MapReduce 작업이 시작할때는 HDFS로 부터 파일을 가져오는 작업이 수행됨

![MapReduce](Weekly%20Progress%206b31ccb2ff7c415b87200bb912974191/Untitled.png)

<br/>  

**Goal of Week 2**

- 시험 일정으로 인해 정해진 시간에 만나진 못했는데, 미팅 시간을 조율하여 학습한 내용들을 토대로 문제를 분석한다. 또한 프로젝트의 단계를 분할하여, 각 단계별 구체적 계획을 논의한다.

<br/>  

**Goal of the week for individual**

- 조은국, 김은채, 이창우 (공통):
    - 학습한 내용을 바탕으로 코딩 계획을 계략적으로 각자 구상해온다.
    - 미팅 이후 파트를 나눠 각자 담당한 파트에 대한 코딩 계획을 구체화하고 공유한다. (Input+partitioning / Sorting / Shuffling+Merging)


<br/>
  
## **Week 3**

---

**Progress of Week 3**

- 프로그램의 전반적 flow, (Master-Worker connection, Sampling&Pivoting, sorting)를 학습하였다.
- Scala의 GRPC와 Gensort의 동작을 학습하였다.


<br/>  

**Goal of Week 3**

- 개발환경 세팅(VM)
    - 한 컴퓨터에서 port만 다르게 해서 구현
- [gRPC] master-slave 연결
    - master ip와 port 출력
    - master 주소를 매개로 slave-master 연결
    - master가 slave의 ip 주소 출력하도록 구현
- 전반적 진행이 더디다 판단되어, 11/10 (목) 22:30까지 해당 tasks를 수행하고 미팅을 갖는다. 이후 추가적으로 goals update 예정.


<br/>  

**Goal of the week for individual ( ~ 11/10(목) )**

- 조은국, 김은채, 이창우(공통):
    - VM 세팅 및 방법 공유 ( ~ 11/08(화), 카카오톡 메신저 활용 )
    - 이후 아래 과정 수행

- [TBD 1]: master ip, port 출력 구현
- [TBD 2]: master 주소를 매개로 slave-master 연결
- [TBD 3]: master의 slave ip 주소 출력 구현
