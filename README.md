# Parallel Genetic Algorithm with scala

Create for personal use (train my bots on codingame.com and another competitions)

my goal was to create **easy to use** and **performance** solution 

(as evaluating function can be complex, so it's important to make less
evaluation of fitness function as possible)
current solution linearly scale with CPU cores 

with small data and simple fitness function performance usual don't have any matter
also performance for parallel algorithm can be even worse

but than data is growing and fitness function is become really complex (for example fitness function relaying on game simulation result)
there parallel working is huge, also less ammount of fitness evaluation is important

## using

to make use GeneticAlgoritm just use 

```scala
     GeneticAlgorithm(geneBase, geneSize, fitness, mutationRate)
```
- `geneBase - baze whitch is used to generate Genes`
- `geneSize - numbers element of resulting Gene`
- `fitness - your evaluating function from input DNA to Double`
- `mutationRate - percent of random mutation (from 1 to 100) [optional]`

[see sample in AlgoTest](/src/test/scala/AlgoTest.scala)

## correctness testing

use `sbt testOnly Algotest`

usual string permutation and similarity evaluation to check correctness

## performance evaluating

I increase working time of fitness function with dummy operation like `sleep`
(only in tests)
in order to optimise solution to real workflow

use `sbt testOnly Perfomance`
